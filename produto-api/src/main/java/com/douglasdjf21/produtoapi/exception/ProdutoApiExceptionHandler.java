package com.douglasdjf21.produtoapi.exception;

import com.douglasdjf21.produtoapi.exception.model.ErroException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
@ControllerAdvice
public class ProdutoApiExceptionHandler  {



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroException> handleValidationException(MethodArgumentNotValidException ex,HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErroException.builder()
                                    .timestamp(LocalDateTime.now())
                                    .error("Erro de validação")
                                    .mensagem(ex.getMessage())
                                    .status(HttpStatus.BAD_REQUEST.value())
                                    .path(request.getRequestURI())
                                    .build());
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroException> handleFormatMessageException(HttpMessageNotReadableException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErroException.builder()
                .timestamp(LocalDateTime.now())
                .error("Formato de mensagem inválido")
                .mensagem(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroException> handleValidationDataException(DataIntegrityViolationException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErroException.builder()
                .timestamp(LocalDateTime.now())
                .error("Erro persistência de dados")
                .mensagem(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request.getRequestURI())
                .build());
    }
}
