package br.com.ebac.cadastro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DocumentacaoTest {
    @Autowired MockMvc mvc;

    @Test
    void publicaContratoDoCadastro() throws Exception {
        mvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/usuarios'].post.responses['201']").exists())
                .andExpect(jsonPath("$.paths['/usuarios'].post.responses['400']").exists())
                .andExpect(jsonPath("$.components.schemas.CadastroUsuario.required").value(hasItems("nome", "idade")))
                .andExpect(jsonPath("$.components.schemas.CadastroUsuario.properties.nome.minLength").value(3));
    }

    @Test
    void disponibilizaInterfaceDoSwagger() throws Exception {
        mvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("swagger-ui")));
    }
}
