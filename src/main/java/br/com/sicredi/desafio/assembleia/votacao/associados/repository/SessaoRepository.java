package br.com.sicredi.desafio.assembleia.votacao.associados.repository;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

}
