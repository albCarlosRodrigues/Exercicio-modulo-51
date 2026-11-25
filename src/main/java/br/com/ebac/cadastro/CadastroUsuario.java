package br.com.ebac.cadastro;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CadastroUsuario(
        @NotNull(message = "Informe o nome.")
        @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres.")
        @Schema(description = "Nome com pelo menos 3 caracteres após remover espaços nas pontas.", example = "Ana")
        String nome,

        @NotNull(message = "Informe a idade.")
        @Positive(message = "A idade deve ser maior que zero.")
        @Schema(description = "Idade em anos completos.", minimum = "1", example = "25")
        Integer idade) {

    public CadastroUsuario {
        if (nome != null) {
            nome = nome.strip();
        }
    }
}
