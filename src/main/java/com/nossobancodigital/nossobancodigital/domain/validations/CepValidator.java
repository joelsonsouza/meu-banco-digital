package com.nossobancodigital.nossobancodigital.domain.validations;

import lombok.Builder;

@Builder
public class CepValidator {

    private String cep;
    private static String  FORMAT = "\\d{5}-\\d{3}";

    public boolean cepInvalido() {
        return !cep.matches(FORMAT) ? true : false;
    }

}
