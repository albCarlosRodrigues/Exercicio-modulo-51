package br.com.ebac.cadastro;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Service;

@Service
public class CadastroService {
    private final ConcurrentMap<CadastroUsuario, Usuario> usuarios = new ConcurrentHashMap<>();

    public Usuario cadastrar(CadastroUsuario cadastro) {
        return usuarios.computeIfAbsent(cadastro,
                dados -> new Usuario(UUID.randomUUID(), dados.nome(), dados.idade()));
    }
}
