package br.com.sicredi.desafio.assembleia.votacao.associados.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sessoes")
@Getter
@Setter
@NoArgsConstructor
public class Sessao extends EntidadeBase {

    @ManyToOne(targetEntity = Pauta.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "idPauta", insertable = false, updatable = false)
    private Pauta pauta;

    @NotNull
    private Long idPauta;

    @NotNull
    private LocalDateTime horarioInicio;

    @NotNull
    private LocalDateTime horarioTermino;

    @JsonIgnore
    @OneToMany(mappedBy = "sessao")
    private List<VotoAssociado> votos;

    public Sessao(Long idPauta, LocalDateTime horarioInicio, LocalDateTime horarioTermino) {
        this.setIdPauta(idPauta);
        this.setHorarioInicio(horarioInicio);
        this.setHorarioTermino(horarioTermino);
    }
}