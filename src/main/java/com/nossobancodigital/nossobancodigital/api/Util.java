package com.nossobancodigital.nossobancodigital.api;

import com.nossobancodigital.nossobancodigital.api.controller.ClienteEnderecoController;
import com.nossobancodigital.nossobancodigital.api.controller.ClienteFotoCpfController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class Util {

    public static String headerBuilder(Class <?> clazz, List<Object> parametros) {
        StringBuilder hyperMedia = new StringBuilder();
        String dianamicResource = "";
        try {
            dianamicResource = linkTo(clazz).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        hyperMedia.append(dianamicResource);

        parametros.forEach((valor) -> hyperMedia.append("/".concat(String.valueOf(valor))));

        return hyperMedia.toString();

    }

    private static Class getClass(String clazz) {
        switch (clazz) {
            case "ClienteEnderecoController":
                return ClienteEnderecoController.class;
            case "ClienteFotoCpfController":
                return ClienteFotoCpfController.class;
            default:
                return null;
        }
    }
}
