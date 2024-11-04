package br.com.sicredi.desafio.assembleia.votacao.associados.dto;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.VotoAssociado;
import br.com.sicredi.desafio.assembleia.votacao.associados.exception.BadRequestException;
import br.com.sicredi.desafio.assembleia.votacao.associados.util.StringUtils;
import br.com.sicredi.desafio.assembleia.votacao.associados.util.Voto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record VotoAssociadoDTO(
        @NotNull Long idAssociado,
        @NotNull Long idSessao,
        @NotNull String voto
) {

    public VotoAssociado converteParaVotoAssociado() {
        try {
            Voto voto = Voto.valueOf(StringUtils.removerAcentos(this.voto.toUpperCase()));
            LocalDateTime horarioVoto = LocalDateTime.now();
            return new VotoAssociado(this.idAssociado, this.idSessao, voto, horarioVoto);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Voto inválido! São permitidos somente votos SIM ou NAO");
        }
    }
}