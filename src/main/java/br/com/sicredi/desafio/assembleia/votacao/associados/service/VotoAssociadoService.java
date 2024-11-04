package br.com.sicredi.desafio.assembleia.votacao.associados.service;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Pauta;
import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Sessao;
import br.com.sicredi.desafio.assembleia.votacao.associados.entity.VotoAssociado;
import br.com.sicredi.desafio.assembleia.votacao.associados.exception.BadRequestException;
import br.com.sicredi.desafio.assembleia.votacao.associados.repository.PautaRepository;
import br.com.sicredi.desafio.assembleia.votacao.associados.repository.SessaoRepository;
import br.com.sicredi.desafio.assembleia.votacao.associados.repository.VotoAssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VotoAssociadoService {

    @Autowired
    private VotoAssociadoRepository votoAssociadoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    public void registrarVoto(VotoAssociado votoAssociado) {
        try {
            votoAssociadoRepository.save(votoAssociado);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public List<VotoAssociado> buscaVotosPorSessao(Long idSessao) {
        return votoAssociadoRepository.findAllByIdSessao(idSessao);
    }

    public boolean existeVotoAssociadoParaPauta(Long idAssociado, Long idPauta) {
        return votoAssociadoRepository.findAllByIdAssociado(idAssociado)
                .stream()
                .map(VotoAssociado::getSessao)
                .anyMatch(sessao -> Objects.equals(sessao.getIdPauta(), idPauta));
    }
}
