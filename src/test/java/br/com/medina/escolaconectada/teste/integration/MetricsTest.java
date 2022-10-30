package br.com.medina.escolaconectada.teste.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.anything;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import br.com.medina.escolaconectada.BaseTest;
import br.com.medina.escolaconectada.models.Cidade;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureMockMvc
@WebMvcTest
public class MetricsTest extends BaseTest {
	@Autowired
	private MockMvc mockMvc;

	private static final String JSON_LOCATION = "data/cidades.json";

	@SuppressWarnings("unchecked")
	@DisplayName("Listar todas as cidades")
	@Test
	public void teste01() throws Exception {
		log.info("## inicio teste 01");
		Gson g = new Gson();
		List<Cidade> element = g.fromJson(readFile(JSON_LOCATION), List.class);
		when(cidadeSvc.findAll()).thenReturn(element);
		MockHttpServletResponse response = this.mockMvc.perform(get("/api/cidade")).andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		List<Cidade> returnElement = g.fromJson(response.getContentAsString(), List.class);
		assertThat(returnElement.size() > 2);
	}

	@DisplayName("Buscar metricas da primeira serie")
	@Test
	public void teste02() throws Exception {
		log.info("## inicio teste 02");
		MockHttpServletResponse response = this.mockMvc.perform(get("/api/metrics/sala").param("seriesIds", "1")
				.param("escolaIds", "6328fa78cc662a35a71406f4").param("ano", "2022")).andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	@DisplayName("Buscar metricas da segunda serie")
	@Test
	public void teste03() throws Exception {
		log.info("## inicio teste 03");
		MockHttpServletResponse response = this.mockMvc.perform(get("/api/metrics/sala").param("seriesIds", "2")
				.param("escolaIds", "6328fa78cc662a35a71406f4").param("ano", "2022")).andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	


	@DisplayName("Teste envio sem apikey - erro 406")
	@Test
	public void teste04() throws Exception {
		log.info("## inicio teste 04");
		MockHttpServletResponse response = this.mockMvc
				.perform(post("/api/cidade").contentType(MediaType.APPLICATION_JSON).content(
						"{\r\n" + "    \"nome\":\"Campos dos goytacazes\",\r\n" + "    \"estado\" : \"RJ\"\r\n" + "}"))
				.andDo(print()).andExpect(status().is4xxClientError()).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());
	}
	
	@DisplayName("Teste envio com apikey - 200 ok")
	@Test
	public void teste05() throws Exception {
		log.info("## inicio teste 05");
		Cidade cidade = new Cidade();
		cidade.setEstado("RJ");
		cidade.setNome("Campos dos goytacazes");
		when(cidadeSvc.update(cidade)).thenReturn(cidade);//inserir apikey to run
		MockHttpServletResponse response = this.mockMvc
				.perform(post("/api/cidade").header("APIKEY", "").header("SECRET", "").contentType(MediaType.APPLICATION_JSON).content(
						"{\r\n" + "    \"nome\":\"Campos dos goytacazes\",\r\n" + "    \"estado\" : \"RJ\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	@DisplayName("Buscar metricas de uma escola")
	@Test
	public void teste06() throws Exception {
		log.info("## inicio teste 06");
		MockHttpServletResponse response = this.mockMvc.perform(get("/api/metrics/escola").param("seriesIds", "2")
				.param("escolaIds", "6328fa78cc662a35a71406f4").param("ano", "2022")).andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	@DisplayName("Buscar metricas de uma escola - sem parametros - erro 400")
	@Test
	public void teste07() throws Exception {
		log.info("## inicio teste 07");
		MockHttpServletResponse response = this.mockMvc.perform(get("/api/metrics/escola").param("seriesIds", "2")
				.param("ano", "2022")).andDo(print())
				.andExpect(status().is4xxClientError()).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

}
