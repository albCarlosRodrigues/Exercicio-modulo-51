package br.com.ebac.cadastro;

import java.util.Map;

public record ErroResposta(String mensagem, Map<String, String> campos) {
}
