package br.com.sicredi.desafio.assembleia.votacao.associados.entity;

import br.com.sicredi.desafio.assembleia.votacao.associados.util.StatusPauta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "pautas")
@Getter
@Setter
@NoArgsConstructor
public class Pauta extends EntidadeBase {

    @NotBlank
    String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    StatusPauta status;

    @NotNull
    int totalVotos;

    @NotNull
    int votosFavoraveis;

    @NotNull
    int votosContra;

    @JsonIgnore
    @OneToMany(mappedBy = "pauta")
    private List<Sessao> sessoes;

    public Pauta(Long id, String descricao, StatusPauta status, int totalVotos, int votosFavoraveis, int votosContra) {
        this.setId(id);
        this.setDescricao(descricao);
        this.setStatus(status);
        this.setTotalVotos(totalVotos);
        this.setVotosFavoraveis(votosFavoraveis);
        this.setVotosContra(votosContra);
    }

    public Pauta(String descricao, StatusPauta status, int totalVotos, int votosFavoraveis, int votosContra) {
        this.setDescricao(descricao);
        this.setStatus(status);
        this.setTotalVotos(totalVotos);
        this.setVotosFavoraveis(votosFavoraveis);
        this.setVotosContra(votosContra);
    }
}