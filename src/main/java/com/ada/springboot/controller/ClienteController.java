package com.ada.springboot.controller;

import com.ada.springboot.controller.vo.ClienteVO;
import com.ada.springboot.model.Cliente;
import com.ada.springboot.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cliente")
    public String criarCliente(@RequestBody ClienteVO clienteVO){
        Cliente cliente = new Cliente();
        cliente.setNome(clienteVO.getNome());
        cliente.setCpf(clienteVO.getCpf());
        cliente.setIdade(clienteVO.getIdade());
        clienteService.criarCliente(cliente);
        return "<h1> Cliente Criado </h1>";
        }
}
