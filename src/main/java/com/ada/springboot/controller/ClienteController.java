package com.ada.springboot.controller;

import com.ada.springboot.controller.dto.ClienteDTO;
import com.ada.springboot.controller.vo.ClienteVO;
import com.ada.springboot.model.Cliente;
import com.ada.springboot.model.Conta;
import com.ada.springboot.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/gravar-cliente")
    public String criarCliente(@Valid @RequestBody ClienteVO clienteVO){
        Cliente cliente = new Cliente();
        cliente.setNome(clienteVO.getNome());
        cliente.setCpf(clienteVO.getCpf());
        cliente.setDataNascimento(clienteVO.getDataNascimento());
        List<Conta> contas = new ArrayList<>();
        if(!clienteVO.getContas().isEmpty()){
            contas = clienteVO.getContas().stream().map(contaVO -> {
                Conta conta = new Conta();
                conta.setNumero(contaVO.getNumero());
                conta.setDataCriacao(contaVO.getDataCriacao());
                conta.setSaldo(contaVO.getSaldo());
                return conta;
            }).collect(Collectors.toList());
        }
        cliente.setContas(contas);
        clienteService.criarCliente(cliente);
        return "<h1> Cliente Criado </h1>";
        }

    @GetMapping("/buscar-cliente-por-id/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable("id") Long id){ //vai consumir da API, classe intermediária
        Optional<Cliente> optionalCliente = this.clienteService.buscarClientePorId(id);
        return getClienteDTOResponseEntity(optionalCliente);
    }

    @GetMapping("/buscar-cliente-por-cpf/{cpf}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable("cpf") String cpf){ //vai consumir da API, classe intermediária
        Optional<Cliente> optionalCliente = this.clienteService.buscarClientePorCpf(cpf);
        return getClienteDTOResponseEntity(optionalCliente);
    }

    @PutMapping("/atualizar-cliente-por-id/{id}")
    public ResponseEntity<ClienteDTO>atualizarClientePorId(@PathVariable("id")Long id, @RequestBody ClienteVO clienteVO){
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

    @PutMapping("/atualizar-cliente-por-cpf/{cpf}")
    public ResponseEntity<ClienteDTO>atualizarClientePorCpf(@RequestBody ClienteVO clienteVO){
        Cliente cliente = this.clienteService.atualizarCliente(clienteVO);
        if(Objects.nonNull(cliente)){
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setNome(cliente.getNome());
            clienteDTO.setCpf(cliente.getCpf());
            clienteDTO.setDataNascimento(cliente.getDataNascimento());
            return ResponseEntity.ok().body(clienteDTO);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deletar-cliente-por-id/{id}")
    public ResponseEntity<String>removerCliente(@PathVariable("id")Long id){
        return getStringResponseEntity(id);
    }
    @DeleteMapping("/deletar-cliente-por-cpf/{cpf}")
    public ResponseEntity<String>removerClientePorId(@PathVariable("cpf")String cpf){
        Optional<Cliente> optionalCliente = this.clienteService.buscarClientePorCpf(cpf);
        if(optionalCliente.isPresent()){
            Cliente cliente = optionalCliente.get();
            this.clienteService.removerClientePorCpf(cpf);
            return ResponseEntity.ok("Cliente removido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente não existe!");
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteDTO>> listarTodosClientes(){
        List<Cliente> clienteList = this.clienteService.listarTodosClientes();
        return converterClienteEmClienteDTO(clienteList);
    }

    @GetMapping("/clientes-por-nome/{nome}")
    public ResponseEntity<List<ClienteDTO>> listarClientesPorNome(@PathVariable ("nome")String nome){
        List<Cliente> clienteList = this.clienteService.listarClientePorNome(nome);
        return converterClienteEmClienteDTO(clienteList);
    }

    @GetMapping("/clientes-por-nome/{nome}/ou-data-nascimento/{data-nascimento}/")
    public ResponseEntity<List<ClienteDTO>> listarClientePorNomeOuDataNascimento(@PathVariable ("nome")String nome,
                                                                                 @PathVariable("data-nascimento")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataNascimento){
        List<Cliente> clienteList = this.clienteService.listarClientePorNomeOuDataNascimentoOrdenadoPorNome(nome, dataNascimento);
        return converterClienteEmClienteDTO(clienteList);
    }

    @GetMapping("/clientes-nascidos-entre/{data-inicial}/e/{data-final}/")
    public ResponseEntity<List<ClienteDTO>> listarClientesPorData( @PathVariable("data-inicial")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicial,
                                                                                 @PathVariable("data-final")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFinal){
        List<Cliente> clienteList = this.clienteService.listarClientesPorPeriodo(dataInicial, dataFinal);
        return converterClienteEmClienteDTO(clienteList);
    }
    private ResponseEntity<List<ClienteDTO>> converterClienteEmClienteDTO(List<Cliente> clienteList) {
        if(clienteList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<ClienteDTO> clienteDTOS = clienteList.stream()
                .map(cliente -> {
                    ClienteDTO clienteDTO = new ClienteDTO();
                    clienteDTO.setNome(cliente.getNome());
                    clienteDTO.setCpf(cliente.getCpf());
                    clienteDTO.setDataNascimento(cliente.getDataNascimento());
                    return clienteDTO;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(clienteDTOS);
    }

    private ResponseEntity<ClienteDTO> getClienteDTOResponseEntity(Optional<Cliente> optionalCliente) {
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setNome(cliente.getNome());
            clienteDTO.setCpf(cliente.getCpf());
            clienteDTO.setDataNascimento(cliente.getDataNascimento());
            return ResponseEntity.ok(clienteDTO); //Lembrando que o DTO é para retornar os dados formatados
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ResponseEntity<String> getStringResponseEntity(@PathVariable("id") Long id) {
        Optional<Cliente> optionalCliente = this.clienteService.buscarClientePorId(id);
        if(optionalCliente.isPresent()){
            Cliente cliente = optionalCliente.get();
            this.clienteService.removerClientePorId(id);
            return ResponseEntity.ok("Cliente removido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente não existe!");
    }

}
