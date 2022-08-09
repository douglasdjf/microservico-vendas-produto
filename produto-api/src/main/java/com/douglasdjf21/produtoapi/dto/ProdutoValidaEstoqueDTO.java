package com.douglasdjf21.produtoapi.dto;


import com.douglasdjf21.produtoapi.listener.dto.ProdutoQuantidadeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoValidaEstoqueDTO {
    private List<ProdutoQuantidadeDTO> produtos;
}
