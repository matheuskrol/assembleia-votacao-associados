package br.com.sicredi.desafio.assembleia.votacao.associados.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "associados", uniqueConstraints = {@UniqueConstraint(columnNames = {"cpf"})})
@Getter
@Setter
@NoArgsConstructor
public class Associado extends EntidadeBase {

    @NotBlank
    private String nome;

    @NotNull
    @Length(min = 11, max = 11)
    @Pattern(regexp = "\\d{11}")
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "associado")
    private List<VotoAssociado> votos;

    public Associado(Long id, String nome, String cpf) {
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
    }

    public Associado(String nome, String cpf) {
        this.setNome(nome);
        this.setCpf(cpf);
    }
}