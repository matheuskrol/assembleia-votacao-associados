package br.com.sicredi.desafio.assembleia.votacao.associados.repository;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PautaRepository extends JpaRepository<Pauta, Long> {

    /*@Query("SELECT CASE WHEN count(v) > 0 THEN true ELSE false END " +
            "FROM votos v " +
            "JOIN v.sessoes s " +
            "JOIN s.pautas p " +
            "WHERE s.id_pauta = ?1 " +
            "AND v.id_associado = ?2 " +
            "AND v.id_sessao <> ?3"
    )
    boolean existeVotoAssociadoParaPauta(Long id, Long idAssociado, Long idSessao);*/
}