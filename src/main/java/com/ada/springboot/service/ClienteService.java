package com.ada.springboot.service;

import com.ada.springboot.controller.vo.ClienteVO;
import com.ada.springboot.model.Cliente;
import com.ada.springboot.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente criarCliente(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }


    public Optional<Cliente> buscarClientePorId(Long id) {
        return this.clienteRepository.findById(id);
    }

    public Cliente atualizarClientePorId(Long id, ClienteVO dadosAtualizadosCliente){
        Optional<Cliente> optionalCliente = this.clienteRepository.findById(id);
        if(optionalCliente.isPresent()){
            Cliente cliente = optionalCliente.get();
            Cliente clienteAtualizado = new Cliente();
            clienteAtualizado.setId(cliente.getId());
            clienteAtualizado.setNome(dadosAtualizadosCliente.getNome());
            clienteAtualizado.setCpf(dadosAtualizadosCliente.getCpf());
            clienteAtualizado.setDataNascimento(dadosAtualizadosCliente.getDataNascimento());
            return this.clienteRepository.save(clienteAtualizado); //devolve a própria entidade atualizada
        }
    //throw new RuntimeException("Cliente nao encontrado");
    return null; // MELHORAR ESSE CARA
    }

    public void removerClientePorId(Long id){
        this.clienteRepository.deleteById(id);
    }
}