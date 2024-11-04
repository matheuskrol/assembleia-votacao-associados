package br.com.sicredi.desafio.assembleia.votacao.associados.entity;

import br.com.sicredi.desafio.assembleia.votacao.associados.util.Voto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "votos")
@Getter
@Setter
@NoArgsConstructor
public class VotoAssociado extends EntidadeBase {

    @ManyToOne(targetEntity = Associado.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "idAssociado", insertable = false, updatable = false)
    private Associado associado;

    @NotNull
    private Long idAssociado;

    @ManyToOne(targetEntity = Sessao.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "idSessao", insertable = false, updatable = false)
    private Sessao sessao;

    @NotNull
    private Long idSessao;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Voto voto;

    @NotNull
    private LocalDateTime horarioVoto;

    public VotoAssociado(Long id, Long idAssociado, Long idSessao, Voto voto, LocalDateTime horarioVoto) {
        this.setId(id);
        this.setIdAssociado(idAssociado);
        this.setIdSessao(idSessao);
        this.setVoto(voto);
        this.setHorarioVoto(horarioVoto);
    }

    public VotoAssociado(Long idAssociado, Long idSessao, Voto voto, LocalDateTime horarioVoto) {
        this.setIdAssociado(idAssociado);
        this.setIdSessao(idSessao);
        this.setVoto(voto);
        this.setHorarioVoto(horarioVoto);
    }
}