package com.douglasdjf21.produtoapi.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/status")
public class StatusResource {

    @GetMapping
    public ResponseEntity<HashMap<String,Object>> getStatus(){
        var response = new HashMap<String,Object>();
        response.put("service","produto-api");
        response.put("status","up");
        response.put("httStatus", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}
