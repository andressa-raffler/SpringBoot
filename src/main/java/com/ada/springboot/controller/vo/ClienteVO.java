package com.ada.springboot.controller.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteVO {
    //VO -> Trabalhar com dados recebidos

    private String nome;
    //@Pattern(regexp="\\d{11}", message = "ATENÇÃO: Deve ser informado 11 dígitos!")
    private String cpf;
    private LocalDate dataNascimento;
    private List<ContaVO> contas;

}
