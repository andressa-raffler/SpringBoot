package com.ada.springboot.controller.dto;


import com.ada.springboot.model.Conta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class ClienteDTO {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private List<ContaDTO> contas;
    //DTO - Transportar dos dados do VO

}
