package br.com.sicredi.desafio.assembleia.votacao.associados.web;

import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Associado;
import br.com.sicredi.desafio.assembleia.votacao.associados.service.AssociadoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/associados")
public class AssociadoController {

    @Autowired
    private AssociadoService associadoService;

    @PostMapping(path = "/inserir")
    ResponseEntity<Associado> inserir(@Valid @RequestBody Associado associado) {
        associadoService.salvar(associado);

        return ResponseEntity.status(HttpStatus.CREATED).body(associado);
    }

    @PutMapping(path = "/alterar/{id}")
    ResponseEntity<Associado> alterar(@PathVariable Long id, @Valid @RequestBody Associado associado) {
        associadoService.atualizar(id, associado);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(associado);
    }

    @GetMapping(path = "/listar")
    ResponseEntity<List<Associado>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(associadoService.listar());
    }
}