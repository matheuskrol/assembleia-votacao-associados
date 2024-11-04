package br.com.sicredi.desafio.assembleia.votacao.associados.scheduler;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Pauta;
import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Sessao;
import br.com.sicredi.desafio.assembleia.votacao.associados.entity.VotoAssociado;
import br.com.sicredi.desafio.assembleia.votacao.associados.service.PautaService;
import br.com.sicredi.desafio.assembleia.votacao.associados.service.VotoAssociadoService;
import br.com.sicredi.desafio.assembleia.votacao.associados.util.DateUtils;
import br.com.sicredi.desafio.assembleia.votacao.associados.util.StatusPauta;
import br.com.sicredi.desafio.assembleia.votacao.associados.util.Voto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public record FecharSessaoTask(
        Sessao sessao,
        VotoAssociadoService votoAssociadoService,
        PautaService pautaService
) implements Runnable {

    @Override
    public void run() {
        List<VotoAssociado> votos = votoAssociadoService.buscaVotosPorSessao(sessao.getId())
                .stream()
                .filter(v -> DateUtils.horarioEntrePeriodos(v.getHorarioVoto(), sessao.getHorarioInicio(), sessao.getHorarioTermino()))
                .toList();
        int totalVotos = votos.size();
        int votosFavoraveis = (int) votos.stream().filter(v -> v.getVoto().equals(Voto.SIM)).count();
        int votosContra = (int) votos.stream().filter(v -> v.getVoto().equals(Voto.NAO)).count();
        StatusPauta statusPauta = votosFavoraveis > votosContra
                ? StatusPauta.APROVADA
                : (votosContra > votosFavoraveis ? StatusPauta.REPROVADA : StatusPauta.PENDENTE);
        Pauta pauta = pautaService.buscaPautaPorId(sessao.getIdPauta());
        totalVotos = totalVotos + pauta.getTotalVotos();
        votosFavoraveis = votosFavoraveis + pauta.getVotosFavoraveis();
        votosContra = votosContra + pauta.getVotosContra();
        pauta.setStatus(statusPauta);
        pauta.setTotalVotos(totalVotos);
        pauta.setVotosFavoraveis(votosFavoraveis);
        pauta.setVotosContra(votosContra);
        log.info("Sessão ID {} fechada para votação", sessao.getId());
        pautaService.salvar(pauta);
    }
}