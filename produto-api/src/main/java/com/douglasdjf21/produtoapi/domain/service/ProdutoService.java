package com.douglasdjf21.produtoapi.domain.service;


import com.douglasdjf21.produtoapi.domain.entity.Fornecedor;
import com.douglasdjf21.produtoapi.domain.entity.Produto;
import com.douglasdjf21.produtoapi.domain.respository.ProdutoRepository;
import com.douglasdjf21.produtoapi.dto.FornecedorDTO;
import com.douglasdjf21.produtoapi.dto.ProdutoDTO;
import com.douglasdjf21.produtoapi.dto.ProdutoUpdateDTO;
import com.douglasdjf21.produtoapi.listener.dto.ProdutoEstoqueDTO;
import com.douglasdjf21.produtoapi.listener.dto.ProdutoQuantidadeDTO;
import com.douglasdjf21.produtoapi.listener.dto.VendaConfirmadaDTO;
import com.douglasdjf21.produtoapi.listener.enums.VendasStatus;
import com.douglasdjf21.produtoapi.publisher.VendaConfirmacaoPublisher;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;


@Slf4j
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private VendaConfirmacaoPublisher vendaConfirmacaoPublisher;

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTO salvar(ProdutoDTO produtoDTO){
        Produto produto = modelMapper.map(produtoDTO,Produto.class);
        return  modelMapper.map(produtoRepository.save(produto), ProdutoDTO.class);
    }

    public List<ProdutoDTO> buscar(){
        return   produtoRepository.findAll().stream().map(c -> modelMapper.map(c,ProdutoDTO.class)).collect(Collectors.toList());
    }

    public ProdutoDTO atualizar(Long id, ProdutoUpdateDTO produtoDTO){

        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if(!optionalProduto.isPresent())
            throw new RuntimeException("Id inválido");

        Produto produtoNovo = modelMapper.map(produtoDTO,Produto.class);
        Produto produtoAntes = optionalProduto.get();
        Produto produtoAlterado =  Produto.builder().build();

        BeanUtils.copyProperties(produtoAntes,produtoAlterado);
        BeanUtils.copyProperties(produtoNovo,produtoAlterado,"id","dataCriacao","fornecedor","categoria");
        Produto fornecedorDepois = produtoRepository.save(produtoAlterado);

        return modelMapper.map(fornecedorDepois,ProdutoDTO.class);
    }

    public void delete(Long id) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if(!optionalProduto.isPresent())
            throw new RuntimeException("Id inválido");

        produtoRepository.deleteById(id);
    }

    public Produto findById(Long id) {
        return produtoRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There's no product for the given ID."));
    }


    /**
     * Atualiza e valida o estoque
     * @param produtoEstoqueDTO
     */
    public void atualizaProdutoEstoque(ProdutoEstoqueDTO produtoEstoqueDTO) {
        try {
            validaEstoqueAtualizaDados(produtoEstoqueDTO);
            atualizaEstoque(produtoEstoqueDTO);
        } catch (Exception ex) {
            log.error("Error while trying to update stock for message with error: {}", ex.getMessage(), ex);
            var rejectedMessage = VendaConfirmadaDTO.builder()
                                      .vendaId(produtoEstoqueDTO.getVendaId())
                                      .transactionId(produtoEstoqueDTO.getTransactionId())
                                      .status(VendasStatus.REJEITADO)
                                      .build();
            vendaConfirmacaoPublisher.sendSalesConfirmationMessage(rejectedMessage);
        }
    }

    /**
     *
     * Valida o estoque
     * @param produtoEstoqueDTO
     */
    private void validaEstoqueAtualizaDados(ProdutoEstoqueDTO produtoEstoqueDTO) {
        if (isEmpty(produtoEstoqueDTO)
                || isEmpty(produtoEstoqueDTO.getVendaId())) {
            throw new ValidationException("The product data and the sales ID must be informed.");
        }
        if (isEmpty(produtoEstoqueDTO.getProdutos())) {
            throw new ValidationException("The sales' products must be informed.");
        }
        produtoEstoqueDTO
                .getProdutos()
                .forEach(vendasProduto -> {
                    if (isEmpty(vendasProduto.getQuantidade())
                            || isEmpty(vendasProduto.getProdutoId())) {
                        throw new ValidationException("The productID and the quantity must be informed.");
                    }
                });
    }

    /**
     * Atualiza estoque
     * @param produtoEstoqueDTO
     */
    @Transactional
    private void atualizaEstoque(ProdutoEstoqueDTO produtoEstoqueDTO) {
        var produtosParaAtualizar = new ArrayList<Produto>();
        produtoEstoqueDTO
                .getProdutos()
                .forEach(vendasProduto -> {
                    var produtoExistente = findById(vendasProduto.getProdutoId());
                    validaQuantidadeEmEstoque(vendasProduto, produtoExistente);
                    produtoExistente.atualizaQuantidade(vendasProduto.getQuantidade());
                    produtosParaAtualizar.add(produtoExistente);
                });
        if (!isEmpty(produtosParaAtualizar)) {
            produtoRepository.saveAll(produtosParaAtualizar);

            var approvedMessage = VendaConfirmadaDTO.builder()
                    .vendaId(produtoEstoqueDTO.getVendaId())
                    .transactionId(produtoEstoqueDTO.getTransactionId())
                    .status(VendasStatus.APROVADO)
                    .build();
            vendaConfirmacaoPublisher.sendSalesConfirmationMessage(approvedMessage);
        }
    }

    /**
     * Valida quantidade no estoque
     *
     * @param produtoQuantidadeDTO
     * @param existingProduct
     */
    private void validaQuantidadeEmEstoque(ProdutoQuantidadeDTO produtoQuantidadeDTO,
                                           Produto existingProduct) {
        if (produtoQuantidadeDTO.getQuantidade() > existingProduct.getQuantidade()) {
            throw new ValidationException(
                    String.format("The product %s is out of stock.", existingProduct.getId()));
        }
    }

}
