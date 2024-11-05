package br.com.sicredi.desafio.assembleia.votacao.associados.kafka;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Pauta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificacaoConsumer {

    @KafkaListener(topics = "pauta-notificacao", groupId = "assembleia-votacao-associados")
    public void recebeNotificacao(Pauta pauta) {
        log.info("Resultado da votação para a pauta ID {} ({}): {}", pauta.getId(), pauta.getDescricao(), pauta.getStatus());
    }
}
