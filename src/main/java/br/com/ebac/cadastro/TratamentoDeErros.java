package br.com.ebac.cadastro;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratamentoDeErros {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta tratarValidacao(MethodArgumentNotValidException erro) {
        Map<String, String> campos = new TreeMap<>();
        for (FieldError campo : erro.getBindingResult().getFieldErrors()) {
            campos.put(campo.getField(), campo.getDefaultMessage());
        }
        return new ErroResposta("Revise os campos do cadastro.", campos);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta tratarCorpoInvalido() {
        return new ErroResposta("Envie um objeto JSON com nome em texto e idade inteira.", Map.of());
    }
}
