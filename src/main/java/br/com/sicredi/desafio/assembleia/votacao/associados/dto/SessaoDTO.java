package br.com.sicredi.desafio.assembleia.votacao.associados.dto;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Sessao;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SessaoDTO(
        @NotNull Long idPauta,
        Integer segundosAbertura
) {

    public Sessao converteParaSessao() {
        LocalDateTime horarioAbertura = LocalDateTime.now();
        int tempoAbertura = segundosAbertura == null ? 60 : segundosAbertura;
        return new Sessao(this.idPauta, horarioAbertura, horarioAbertura.plusSeconds(tempoAbertura));
    }
}