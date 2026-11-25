package br.com.ebac.cadastro;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {
    @Autowired MockMvc mvc;

    @Test
    void repetirCadastroDevolveAMesmaResposta() throws Exception {
        String cadastro = """
                {"nome":"Ana","idade":1}
                """;
        var resposta = mvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(cadastro))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.nome").value("Ana"))
                .andExpect(jsonPath("$.idade").value(1))
                .andReturn().getResponse().getContentAsString();

        mvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(cadastro))
                .andExpect(status().isCreated())
                .andExpect(content().json(resposta));

        String idOriginal = JsonPath.read(resposta, "$.id");
        mvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content("""
                        {"nome":"Ana","idade":2}
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(not(idOriginal)));
    }

    @Test
    void explicaOsErrosDeCadaCampo() throws Exception {
        mvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content("""
                        {"nome":"Al","idade":0}
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Revise os campos do cadastro."))
                .andExpect(jsonPath("$.campos.nome").value("O nome deve ter pelo menos 3 caracteres."))
                .andExpect(jsonPath("$.campos.idade").value("A idade deve ser maior que zero."));
    }

    @ParameterizedTest
    @ValueSource(strings = {"{}", "{\"nome\":null,\"idade\":null}"})
    void exigeNomeEIdade(String corpo) throws Exception {
        mvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(corpo))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campos.nome").value("Informe o nome."))
                .andExpect(jsonPath("$.campos.idade").value("Informe a idade."));
    }

    @ParameterizedTest
    @ValueSource(strings = {"{", "", "{\"nome\":\"Ana\",\"idade\":1.5}", "{\"nome\":\"Ana\",\"idade\":\"25\"}"})
    void rejeitaCorpoInvalido(String corpo) throws Exception {
        mvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(corpo))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Envie um objeto JSON com nome em texto e idade inteira."));
    }
}
