package com.ada.springboot.controller.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class ClienteDTO {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    //DTO - Transportar dos dados do VO

}
