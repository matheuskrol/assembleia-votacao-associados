package br.com.sicredi.desafio.assembleia.votacao.associados.web;

import br.com.sicredi.desafio.assembleia.votacao.associados.dto.SessaoDTO;
import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Sessao;
import br.com.sicredi.desafio.assembleia.votacao.associados.kafka.NotificacaoProducer;
import br.com.sicredi.desafio.assembleia.votacao.associados.scheduler.FecharSessaoScheduler;
import br.com.sicredi.desafio.assembleia.votacao.associados.service.PautaService;
import br.com.sicredi.desafio.assembleia.votacao.associados.service.SessaoService;
import br.com.sicredi.desafio.assembleia.votacao.associados.service.VotoAssociadoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private VotoAssociadoService votoAssociadoService;

    @Autowired
    private NotificacaoProducer notificacaoProducer;

    @Transactional
    @PostMapping(path = "/abrirSessao")
    ResponseEntity<Sessao> abrirSessao(@Valid @RequestBody SessaoDTO dto) {
        log.info("Chamada para a requisição de abertura de sessão");
        Sessao sessao = dto.converteParaSessao();
        int tempoAbertura = dto.segundosAbertura() == null ? 60 : dto.segundosAbertura();
        sessaoService.abrirSessao(sessao);
        pautaService.atualizarStatusProcessando(sessao.getIdPauta());
        log.info("Para realizar a tarefa de fechar a sessão após o período definido, optei por utilizar o ScheduledExecutorService");
        ScheduledExecutorService fecharSessao = Executors.newScheduledThreadPool(1);
        fecharSessao.schedule(new FecharSessaoScheduler(sessao, votoAssociadoService, pautaService, notificacaoProducer), tempoAbertura, TimeUnit.SECONDS);
        log.info("Sessão ID {} aberta para votação", sessao.getId());
        return ResponseEntity.status(HttpStatus.OK).body(sessao);
    }
}