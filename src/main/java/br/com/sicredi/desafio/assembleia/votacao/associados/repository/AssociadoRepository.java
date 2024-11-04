package br.com.sicredi.desafio.assembleia.votacao.associados.repository;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Associado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {

}