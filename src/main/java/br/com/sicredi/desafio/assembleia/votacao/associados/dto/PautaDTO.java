package br.com.sicredi.desafio.assembleia.votacao.associados.dto;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Pauta;
import br.com.sicredi.desafio.assembleia.votacao.associados.util.StatusPauta;
import jakarta.validation.constraints.NotBlank;

public record PautaDTO(
        @NotBlank String descricao
) {

    public Pauta converteParaPauta() {
        return new Pauta(this.descricao, StatusPauta.PENDENTE, 0, 0, 0);
    }
}