package com.douglasdjf21.produtoapi.listener.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEstoqueDTO {

    private String vendaId;
    private List<ProdutoQuantidadeDTO> produtos;
    private String transactionId;
}
