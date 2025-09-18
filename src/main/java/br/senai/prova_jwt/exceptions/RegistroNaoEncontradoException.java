package br.senai.prova_jwt.exceptions;

public class RegistroNaoEncontradoException extends RuntimeException {

    public RegistroNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RegistroNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
