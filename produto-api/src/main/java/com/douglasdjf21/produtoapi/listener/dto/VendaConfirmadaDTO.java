package com.douglasdjf21.produtoapi.listener.dto;


import com.douglasdjf21.produtoapi.listener.enums.VendasStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendaConfirmadaDTO {

    private String vendaId;
    private VendasStatus status;
    private String transactionId;
}
