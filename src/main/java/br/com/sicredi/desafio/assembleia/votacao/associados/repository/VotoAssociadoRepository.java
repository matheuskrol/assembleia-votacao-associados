package br.com.sicredi.desafio.assembleia.votacao.associados.repository;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.VotoAssociado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VotoAssociadoRepository extends JpaRepository<VotoAssociado, Long> {

    List<VotoAssociado> findAllByIdSessao(Long idSessao);

    List<VotoAssociado> findAllByIdAssociado(Long idAssociado);
}
