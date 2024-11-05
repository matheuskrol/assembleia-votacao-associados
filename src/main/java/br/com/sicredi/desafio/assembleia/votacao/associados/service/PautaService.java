package br.com.sicredi.desafio.assembleia.votacao.associados.service;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Pauta;
import br.com.sicredi.desafio.assembleia.votacao.associados.exception.BadRequestException;
import br.com.sicredi.desafio.assembleia.votacao.associados.repository.PautaRepository;
import br.com.sicredi.desafio.assembleia.votacao.associados.util.StatusPauta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public void salvar(Pauta pauta) {
        try {
            pautaRepository.save(pauta);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public Pauta buscaPautaPorId(Long id) {
        return pautaRepository.findById(id).orElseThrow(() ->
                new BadRequestException("Pauta %d não existe! ".formatted(id))
        );
    }

    public List<Pauta> listar() {
        return pautaRepository.findAll();
    }

    public void atualizarStatusProcessando(Long id) {
        Pauta pauta = pautaRepository.findById(id).orElseThrow(() ->
                new BadRequestException("Pauta %d não existe! ".formatted(id))
        );
        pauta.setStatus(StatusPauta.PROCESSANDO);
    }
}