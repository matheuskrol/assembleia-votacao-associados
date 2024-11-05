package br.com.sicredi.desafio.assembleia.votacao.associados.service;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Associado;
import br.com.sicredi.desafio.assembleia.votacao.associados.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class ValidacaoCpfAssociadoService {

    private final RestClient restClient;

    public ValidacaoCpfAssociadoService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://user-info.herokuapp.com/users").build();
    }

    public void valida(Associado associado) {
        var response = restClient.get().uri(uriBuilder ->
                uriBuilder.path("/" + associado.getCpf()).build()
        ) .retrieve().toEntity(CpfValido.class);
        if (response.getStatusCode().isError() || !Objects.requireNonNull(response.getBody()).valido())
            throw new BadRequestException("CPF inválido");
    }
}

record CpfValido(String status) {
    public boolean valido() {
        return status.equals("ABLE_TO_VOTE");
    }
}