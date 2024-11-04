package br.com.sicredi.desafio.assembleia.votacao.associados.web;

import br.com.sicredi.desafio.assembleia.votacao.associados.dto.PautaDTO;
import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Pauta;
import br.com.sicredi.desafio.assembleia.votacao.associados.service.PautaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping(path = "/inserir")
    ResponseEntity<Pauta> inserir(@Valid @RequestBody PautaDTO dto) {
        Pauta pauta = dto.converteParaPauta();
        pautaService.salvar(pauta);
        log.info("Pauta ID {} ({}) inserida com sucesso", pauta.getId(), pauta.getDescricao());
        return ResponseEntity.status(HttpStatus.CREATED).body(pauta);
    }

    @GetMapping(path = "/listar")
    ResponseEntity<List<Pauta>> listar() {
        log.info("Pautas listadas com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(pautaService.listar());
    }
}