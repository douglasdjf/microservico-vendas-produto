package com.douglasdjf21.produtoapi.domain.service;


import com.douglasdjf21.produtoapi.domain.entity.Fornecedor;
import com.douglasdjf21.produtoapi.domain.entity.Produto;
import com.douglasdjf21.produtoapi.domain.respository.ProdutoRepository;
import com.douglasdjf21.produtoapi.dto.FornecedorDTO;
import com.douglasdjf21.produtoapi.dto.ProdutoDTO;
import com.douglasdjf21.produtoapi.dto.ProdutoUpdateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTO salvar(ProdutoDTO produtoDTO){
        Produto produto = modelMapper.map(produtoDTO,Produto.class);
        return  modelMapper.map(repository.save(produto), ProdutoDTO.class);
    }

    public List<ProdutoDTO> buscar(){
        return   repository.findAll().stream().map(c -> modelMapper.map(c,ProdutoDTO.class)).collect(Collectors.toList());
    }

    public ProdutoDTO atualizar(Long id, ProdutoUpdateDTO produtoDTO){

        Optional<Produto> optionalProduto = repository.findById(id);
        if(!optionalProduto.isPresent())
            throw new RuntimeException("Id inválido");

        Produto produtoNovo = modelMapper.map(produtoDTO,Produto.class);
        Produto produtoAntes = optionalProduto.get();
        Produto produtoAlterado =  Produto.builder().build();

        BeanUtils.copyProperties(produtoAntes,produtoAlterado);
        BeanUtils.copyProperties(produtoNovo,produtoAlterado,"id","dataCriacao","fornecedor","categoria");
        Produto fornecedorDepois = repository.save(produtoAlterado);

        return modelMapper.map(fornecedorDepois,ProdutoDTO.class);
    }

    public void delete(Long id) {
        Optional<Produto> optionalProduto = repository.findById(id);
        if(!optionalProduto.isPresent())
            throw new RuntimeException("Id inválido");

        repository.deleteById(id);
    }
}
