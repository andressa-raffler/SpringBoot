package com.ada.springboot.controller.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteVO {
    //VO -> Trabalhar com dados recebidos

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;

}
