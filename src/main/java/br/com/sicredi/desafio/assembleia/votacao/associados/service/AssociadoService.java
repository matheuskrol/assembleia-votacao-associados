package br.com.sicredi.desafio.assembleia.votacao.associados.service;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Associado;
import br.com.sicredi.desafio.assembleia.votacao.associados.exception.BadRequestException;
import br.com.sicredi.desafio.assembleia.votacao.associados.repository.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociadoService {

    @Autowired
    private AssociadoRepository associadoRepository;

    public void salvar(Associado associado) {
        try {
            associadoRepository.save(associado);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public void atualizar(Long id, Associado associado) {
        associadoRepository.findById(id).ifPresentOrElse((existe) -> {
                    associado.setId(id);
                    associadoRepository.save(associado);
                }, () -> {
                    throw new BadRequestException("Associado %d não existe! ".formatted(id));
                }
        );
    }

    public List<Associado> listar() {
        Sort sort = Sort.by("nome").ascending();
        return associadoRepository.findAll(sort);
    }
}