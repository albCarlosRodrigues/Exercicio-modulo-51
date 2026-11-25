package br.com.ebac.cadastro;

import java.util.UUID;

public record Usuario(UUID id, String nome, int idade) {
}
