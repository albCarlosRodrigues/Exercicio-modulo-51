package br.com.ebac.cadastro;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {
    private final CadastroService service;

    public UsuarioController(CadastroService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar usuário", description =
            "Repetir o mesmo nome e idade devolve o cadastro original, inclusive o ID e o status 201.")
    @ApiResponse(responseCode = "201", description = "Cadastro criado ou resposta original repetida",
            content = @Content(schema = @Schema(implementation = Usuario.class)))
    @ApiResponse(responseCode = "400", description = "Campos inválidos ou corpo JSON ilegível",
            content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    @PostMapping(value = "/usuarios", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario cadastrar(@Valid @RequestBody CadastroUsuario cadastro) {
        return service.cadastrar(cadastro);
    }
}
