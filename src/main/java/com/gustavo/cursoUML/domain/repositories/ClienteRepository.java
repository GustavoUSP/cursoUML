package com.gustavo.cursoUML.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gustavo.cursoUML.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
// o objeto categoriaRepository eh capaz de realizar operacoes de acesso a dados
// referentes ao objeto Categoria, que por sua vez esta mapeada atraves da tabela Categoria 
// no banco de dados