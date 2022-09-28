package com.ada.springboot.repository;

import com.ada.springboot.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

     List<Cliente>findByNome(String nome);

    //Encontrar todos os clientes que tenham parte do nome ou data de nascimento
     List<Cliente> findByNomeContainingOrDataNascimentoOrderByNomeAsc(String nome, LocalDate dataNascimento);

     List<Cliente> findByDataNascimentoBetween(LocalDate dataInicial, LocalDate dataFinal);

    Optional<Cliente> findByCpf(String cpf);

    void deleteByCpf(String cpf);

    @Query("select c from Cliente c where c.cpf = ?1") // o ?1 representa Primeiro Parametro recebido, caso tenha mais utilizar ?2, ?3,etc.
    Optional<Cliente> buscarClientePorCpfParametroIndexado(String cpf);

    @Query("select c from Cliente c where c.cpf = :cpf")
    Optional<Cliente> buscarClientePorCpfParametroNominal(@Param("cpf") String cpf);

    @Query("select cliente from Cliente cliente where cliente.dataNascimento between :dataInicial and :dataFinal")
    List<Cliente> listarClientesPorPeriodo(@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal")LocalDate dataFinal);

}
//2. Crie uma query que traga os clientes que nasceram em um determinado periodo. Tem-se como período data inicial e data final. Utilize os conceitos de  Defined Query Methods.
