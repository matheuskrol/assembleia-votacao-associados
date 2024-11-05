package br.com.sicredi.desafio.assembleia.votacao.associados.web;

import br.com.sicredi.desafio.assembleia.votacao.associados.dto.VotoAssociadoDTO;
import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Associado;
import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Pauta;
import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Sessao;
import br.com.sicredi.desafio.assembleia.votacao.associados.entity.VotoAssociado;
import br.com.sicredi.desafio.assembleia.votacao.associados.exception.BadRequestException;
import br.com.sicredi.desafio.assembleia.votacao.associados.service.AssociadoService;
import br.com.sicredi.desafio.assembleia.votacao.associados.service.PautaService;
import br.com.sicredi.desafio.assembleia.votacao.associados.service.SessaoService;
import br.com.sicredi.desafio.assembleia.votacao.associados.service.VotoAssociadoService;
import br.com.sicredi.desafio.assembleia.votacao.associados.util.DateUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/votos")
public class VotoAssociadoController {

    @Autowired
    private VotoAssociadoService votoAssociadoService;

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private AssociadoService associadoService;

    @Autowired
    private PautaService pautaService;

    @PostMapping(path = "/registrarVoto")
    ResponseEntity<VotoAssociado> registrarVoto(@Valid @RequestBody VotoAssociadoDTO dto) {
        VotoAssociado votoAssociado = dto.converteParaVotoAssociado();
        Sessao sessao = sessaoService.buscaSessaoPorId(votoAssociado.getIdSessao());
        Associado associado = associadoService.buscaAssociadoPorId(votoAssociado.getIdAssociado());
        Pauta pauta = pautaService.buscaPautaPorId(sessao.getIdPauta());
        if (!DateUtils.horarioEntrePeriodos(votoAssociado.getHorarioVoto(), sessao.getHorarioInicio(), sessao.getHorarioTermino())) {
            throw new BadRequestException("Sessão já encerrada para votação");
        }
        boolean existeVotoAssociado = votoAssociadoService.existeVotoAssociadoParaPauta(votoAssociado.getIdAssociado(), sessao.getIdPauta());
        if (existeVotoAssociado) {
            throw new BadRequestException("Associado já votou para esta pauta");
        }
        votoAssociadoService.registrarVoto(votoAssociado);
        log.info("Voto do associado {} para a pauta ({}) registrado com sucesso", associado.getNome(), pauta.getDescricao());
        return ResponseEntity.status(HttpStatus.OK).body(votoAssociado);
    }
}