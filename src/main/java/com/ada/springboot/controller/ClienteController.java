package com.ada.springboot.controller;

import com.ada.springboot.controller.dto.ClienteDTO;
import com.ada.springboot.controller.vo.ClienteVO;
import com.ada.springboot.model.Cliente;
import com.ada.springboot.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cliente")
    public String criarCliente(@RequestBody ClienteVO clienteVO){
        Cliente cliente = new Cliente();
        cliente.setNome(clienteVO.getNome());
        cliente.setCpf(clienteVO.getCpf());
        cliente.setDataNascimento(clienteVO.getDataNascimento());
        clienteService.criarCliente(cliente);
        return "<h1> Cliente Criado </h1>";
        }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable("id") Long id){ //vai consumir da API, classe intermediária
        Optional<Cliente> optionalCliente = this.clienteService.buscarClientePorId(id);
        if(optionalCliente.isPresent()){
            Cliente cliente = optionalCliente.get();
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setNome(cliente.getNome());
            clienteDTO.setCpf(cliente.getCpf());
            clienteDTO.setDataNascimento(cliente.getDataNascimento());
            return ResponseEntity.ok(clienteDTO); //Lembrando que o DTO é para retornar os dados formatados
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/cliente/{id}")
    public ResponseEntity<ClienteDTO>atualizarCliente(@PathVariable("id")Long id, @RequestBody ClienteVO clienteVO){
       Cliente cliente = this.clienteService.atualizarClientePorId(id,clienteVO);
        if(Objects.nonNull(cliente)){
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setNome(cliente.getNome());
            clienteDTO.setCpf(cliente.getCpf());
            clienteDTO.setDataNascimento(cliente.getDataNascimento());
            return ResponseEntity.ok().body(clienteDTO);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("cliente/{id}")
    public ResponseEntity<String>removerCliente(@PathVariable("id")Long id){
        Optional<Cliente> optionalCliente = this.clienteService.buscarClientePorId(id);
        if(optionalCliente.isPresent()){
            Cliente cliente = optionalCliente.get();
            this.clienteService.removerClientePorId(id);
            return ResponseEntity.ok("Cliente removido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente não existe!");
    }

}
