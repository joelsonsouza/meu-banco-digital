package com.nossobancodigital.nossobancodigital.domain.exceptions;

public class DadosIncompletosException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DadosIncompletosException(String mensagem) {
        super(mensagem);
    }

    public DadosIncompletosException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
