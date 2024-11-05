package br.com.sicredi.desafio.assembleia.votacao.associados;

import br.com.sicredi.desafio.assembleia.votacao.associados.dto.PautaDTO;
import br.com.sicredi.desafio.assembleia.votacao.associados.dto.SessaoDTO;
import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Associado;
import br.com.sicredi.desafio.assembleia.votacao.associados.entity.Pauta;
import br.com.sicredi.desafio.assembleia.votacao.associados.util.StatusPauta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AssembleiaVotacaoAssociadosApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void testeInsercaoAssociadoSucesso() {
		var associado = new Associado("Associado Teste", "41751618994");
		webTestClient.post()
				.uri("/associados/inserir")
				.bodyValue(associado)
				.exchange()
				.expectStatus()
				.isCreated()
				.expectBody()
				.jsonPath("$.nome").isEqualTo(associado.getNome())
				.jsonPath("$.cpf").isEqualTo(associado.getCpf());
	}


	@Test
	void testeInsercaoAssociadoErro() {
		var associado = new Associado("Associado Teste", "");
		webTestClient.post()
				.uri("/associados/inserir")
				.bodyValue(associado)
				.exchange()
				.expectStatus()
				.isBadRequest();
	}

	@Sql("/SQL/insert_associados.sql")
	@Test
	void testeAlteracaoAssociadoSucesso() {
		var associado = new Associado("Associado Teste 3", "22338940814");
		webTestClient.put()
				.uri("/associados/alterar/30")
				.bodyValue(associado)
				.exchange()
				.expectStatus()
				.isAccepted()
				.expectBody()
				.jsonPath("$.nome").isEqualTo(associado.getNome())
				.jsonPath("$.cpf").isEqualTo(associado.getCpf());
	}

	@Sql("/SQL/insert_associados.sql")
	@Test
	void testeAlteracaoAssociadoErro() {
		var associado = new Associado("Associado Teste", "0337158290a");
		webTestClient.put()
				.uri("/associados/alterar/31")
				.bodyValue(associado)
				.exchange()
				.expectStatus()
				.isBadRequest();
	}

	@Sql("/SQL/insert_associados.sql")
	@Test
	void testeListarAssociadoSucesso() {
		var associado = new Associado("Associado Teste", "43942450950");
		var associado2 = new Associado("Associado Teste 2", "03371582906");
		webTestClient.get()
				.uri("/associados/listar")
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.size()").isEqualTo(2)
				.jsonPath("$[0].nome").isEqualTo(associado.getNome())
				.jsonPath("$[0].cpf").isEqualTo(associado.getCpf())
				.jsonPath("$[1].nome").isEqualTo(associado2.getNome())
				.jsonPath("$[1].cpf").isEqualTo(associado2.getCpf());
	}

	@Test
	void testeInsercaoPautaSucesso() {
		var pauta = new PautaDTO("Pauta");
		webTestClient.post()
				.uri("/pautas/inserir")
				.bodyValue(pauta)
				.exchange()
				.expectStatus()
				.isCreated()
				.expectBody()
				.jsonPath("$.descricao").isEqualTo(pauta.descricao())
				.jsonPath("$.status").isEqualTo(StatusPauta.PENDENTE.name())
				.jsonPath("$.totalVotos").isEqualTo(0)
				.jsonPath("$.votosFavoraveis").isEqualTo(0)
				.jsonPath("$.votosContra").isEqualTo(0);
	}

	@Test
	void testeInsercaoPautaErro() {
		var pauta = new PautaDTO("");
		webTestClient.post()
				.uri("/pautas/inserir")
				.bodyValue(pauta)
				.exchange()
				.expectStatus()
				.isBadRequest();
	}

	@Sql("/SQL/insert_pautas.sql")
	@Test
	void testeListarPautaSucesso() {
		var pauta = new Pauta("Pauta 1", StatusPauta.PENDENTE, 0, 0, 0);
		var pauta2 = new Pauta("Pauta 2", StatusPauta.PENDENTE, 0, 0, 0);
		var pauta3 = new Pauta("Pauta 3", StatusPauta.PROCESSANDO, 0, 0, 0);
		webTestClient.get()
				.uri("/pautas/listar")
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.size()").isEqualTo(3)
				.jsonPath("$[0].descricao").isEqualTo(pauta.getDescricao())
				.jsonPath("$[0].status").isEqualTo(pauta.getStatus().name())
				.jsonPath("$[0].totalVotos").isEqualTo(pauta.getTotalVotos())
				.jsonPath("$[0].votosFavoraveis").isEqualTo(pauta.getVotosFavoraveis())
				.jsonPath("$[0].votosContra").isEqualTo(pauta.getVotosContra())
				.jsonPath("$[1].descricao").isEqualTo(pauta2.getDescricao())
				.jsonPath("$[1].status").isEqualTo(pauta2.getStatus().name())
				.jsonPath("$[1].totalVotos").isEqualTo(pauta2.getTotalVotos())
				.jsonPath("$[1].votosFavoraveis").isEqualTo(pauta2.getVotosFavoraveis())
				.jsonPath("$[1].votosContra").isEqualTo(pauta2.getVotosContra())
				.jsonPath("$[2].descricao").isEqualTo(pauta3.getDescricao())
				.jsonPath("$[2].status").isEqualTo(pauta3.getStatus().name())
				.jsonPath("$[2].totalVotos").isEqualTo(pauta3.getTotalVotos())
				.jsonPath("$[2].votosFavoraveis").isEqualTo(pauta3.getVotosFavoraveis())
				.jsonPath("$[2].votosContra").isEqualTo(pauta3.getVotosContra());
	}

	@Sql("/SQL/insert_pautas.sql")
	@Test
	void testeAbrirSessaoSucesso() {
		var sessao = new SessaoDTO(10L, null);
		webTestClient.post()
				.uri("/sessoes/abrirSessao")
				.bodyValue(sessao)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$.idPauta").isEqualTo(sessao.idPauta())
				.jsonPath("$.horarioInicio").isNotEmpty()
				.jsonPath("$.horarioTermino").isNotEmpty();
	}

	@Sql("/SQL/insert_pautas.sql")
	@Test
	void testeAbrirSessaoErro() {
		var sessao = new SessaoDTO(40L, null);
		webTestClient.post()
				.uri("/sessoes/abrirSessao")
				.bodyValue(sessao)
				.exchange()
				.expectStatus()
				.isBadRequest();
	}
}