package br.com.sicredi.desafio.assembleia.votacao.associados.kafka;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Pauta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoProducer {

    @Autowired
    KafkaTemplate<String, Pauta> kafkaTemplate;

    public void enviaNotificacao(Pauta pauta) {
        kafkaTemplate.send("pauta-notificacao", pauta);
    }
}