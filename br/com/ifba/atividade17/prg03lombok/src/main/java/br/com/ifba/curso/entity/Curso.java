package br.com.ifba.curso.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "curso")
@Getter // cria getter pra todos os atributos declarados.
@Setter // cria setter pra todos os atributos declarados.
@NoArgsConstructor // serve pra criar um construtor vazio.
@AllArgsConstructor // serve pra criar o construtor com todos os atributos.
public class Curso extends PersistenceEntity{
    
    private String nome;
    private String codigo;

}
