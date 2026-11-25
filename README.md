# Exercício módulo 51 — Cadastro de usuários

API Spring Boot com um único endpoint de negócio, `POST /usuarios`. Os dois campos obrigatórios são `nome` (texto com pelo menos três caracteres após remover espaços nas pontas) e `idade` (inteiro maior que zero).

## Executar

Com Java 21 e Maven instalados, abra o terminal na pasta do projeto e execute:

```powershell
mvn spring-boot:run
```

Abra o [Swagger UI](http://localhost:8080/swagger-ui/index.html), expanda **POST /usuarios**, clique em **Try it out** e envie o exemplo abaixo com **Execute**. O [contrato OpenAPI](http://localhost:8080/v3/api-docs) é gerado a partir do controlador e dos tipos Java.

```json
{"nome":"Ana","idade":25}
```

A primeira chamada retorna **201 Created** com `id`, `nome` e `idade`. Clique em **Execute** novamente com os mesmos dados para conferir que o status e o ID se repetem. Neste exercício, o par nome e idade identifica o cadastro. Duas pessoas com o mesmo nome e idade compartilham o registro. Os dados ficam em memória enquanto a aplicação está em execução.

Para testar a validação, envie `{"nome":"Al","idade":0}`. A resposta será **400 Bad Request**:

```json
{
  "mensagem": "Revise os campos do cadastro.",
  "campos": {
    "idade": "A idade deve ser maior que zero.",
    "nome": "O nome deve ter pelo menos 3 caracteres."
  }
}
```

## Testes

```powershell
mvn test
```
