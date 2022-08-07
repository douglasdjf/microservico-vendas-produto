package com.douglasdjf21.produtoapi.exception.model;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ErroException extends RuntimeException {

    private LocalDateTime timestamp;
    private Integer status;
    private String mensagem;
    private String error;
    private String path;
}
