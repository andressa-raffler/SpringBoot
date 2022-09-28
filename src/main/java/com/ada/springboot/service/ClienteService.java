package com.ada.springboot.service;

import com.ada.springboot.controller.vo.ClienteVO;
import com.ada.springboot.model.Cliente;
import com.ada.springboot.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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

    public Cliente atualizarCliente(ClienteVO dadosAtualizadosCliente){
        Optional<Cliente> optionalCliente = this.clienteRepository.findByCpf(dadosAtualizadosCliente.getCpf());
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

    public List<Cliente> listarTodosClientes(){
        return this.clienteRepository.findAll();
    }

    public List<Cliente> listarClientePorNome(String nome){
        return this.clienteRepository.findByNome(nome);
    }
    public List<Cliente> listarClientePorNomeOuDataNascimentoOrdenadoPorNome(String nome, LocalDate dataNascimento){
        return this.clienteRepository.findByNomeContainingOrDataNascimentoOrderByNomeAsc(nome, dataNascimento);
    }

    public List<Cliente> listarClientesPorPeriodo(LocalDate dataInicial, LocalDate dataFinal){
        //return this.clienteRepository.findByDataNascimentoBetween(dataInicial, dataFinal); //utilizando metodos
        return this.clienteRepository.listarClientesPorPeriodo(dataInicial, dataFinal); //UTILIZANDO QUERYS

    }

    public Optional<Cliente> buscarClientePorCpf(String cpf){
        return clienteRepository.findByCpf(cpf);
    }

    public void removerClientePorCpf(String cpf) {
        this.clienteRepository.deleteByCpf(cpf);
    }

}

