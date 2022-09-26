package com.ada.springboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

    @Getter
    @Setter
    @Entity
    @Table(name = "cliente")
    public class Cliente {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String nome;

        @Column(nullable = false)
        private String cpf;

        @Column(nullable = false, name = "data_nascimento", columnDefinition = "DATE")
        private LocalDate dataNascimento;


    }
