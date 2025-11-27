package br.com.ifba.infrastructure.entity;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter // gera o getter do id.
@Setter // gera o setter do id.
@MappedSuperclass // anotação do JPA: indica que a classe não será diretamente mapeada para uma tabela
// porém suas propriedades serão herdadas por outras entidades.

public abstract class PersistenceEntity { 
    @Id // marca o campo como chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // o banco de dados gera o id automaticamente.
    private Long id;
}
