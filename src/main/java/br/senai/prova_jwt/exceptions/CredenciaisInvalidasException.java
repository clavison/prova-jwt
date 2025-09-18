package br.senai.prova_jwt.exceptions;

public class CredenciaisInvalidasException extends RuntimeException {
    public CredenciaisInvalidasException() {
        super("Usuário e/ou senha inválidos");
    }


}
