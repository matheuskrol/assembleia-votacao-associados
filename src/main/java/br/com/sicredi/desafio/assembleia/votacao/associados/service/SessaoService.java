package br.com.sicredi.desafio.assembleia.votacao.associados.service;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Pauta;
import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Sessao;
import br.com.sicredi.desafio.assembleia.votacao.associados.exception.BadRequestException;
import br.com.sicredi.desafio.assembleia.votacao.associados.repository.PautaRepository;
import br.com.sicredi.desafio.assembleia.votacao.associados.repository.SessaoRepository;
import br.com.sicredi.desafio.assembleia.votacao.associados.util.StatusPauta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    public void abrirSessao(Sessao sessao) {
        try {
            Pauta pauta = pautaRepository.findById(sessao.getIdPauta()).orElseThrow(() ->
                    new BadRequestException("Pauta %d não existe! ".formatted(sessao.getIdPauta()))
            );
            if (!pauta.getStatus().equals(StatusPauta.PENDENTE)) {
                throw new BadRequestException("A pauta informada não se encontra pendente de votação");
            }
            sessaoRepository.save(sessao);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public Sessao buscaSessaoPorId(Long id) {
        return sessaoRepository.findById(id).orElseThrow(() ->
                new BadRequestException("Sessão %d não existe! ".formatted(id))
        );
    }
}