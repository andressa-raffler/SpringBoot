package com.ada.springboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "conta")
public class Conta {

    @Id
    //@EmbeddedId para chave primária composta
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long numero;

    @Column(nullable = false, name = "data_criacao", columnDefinition = "TIMESTAMP")
    private LocalDate dataCriacao;

    @Column(precision = 16, scale = 2)
    private BigDecimal saldo;

    @ManyToOne //mapeamento bidirecional
    private Cliente cliente;







}
