package com.nossobancodigital.nossobancodigital.api.exceptiohandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    REGRA_NEGOCIO_QUEBRADA("/regra-negocio-quebrada", "Regra de negócio quebrada"),
    JSON_INVALIDO("/corpo-requisicao-invalido", "Corpo da requisição inválido"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado","Recurso não encontrado"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
    DADOS_INCOMPLETOS_PARA_PROSSEGUIR("/dados-incompletos-para-prosseguir","A requisição esta inabilitada para ser seguida");
    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://nossobancodigital" + path;
        this.title = title;
    }
}
