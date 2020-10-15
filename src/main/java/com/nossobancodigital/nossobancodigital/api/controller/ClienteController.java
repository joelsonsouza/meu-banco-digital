package com.nossobancodigital.nossobancodigital.api.controller;

import com.nossobancodigital.nossobancodigital.api.Util;
import com.nossobancodigital.nossobancodigital.api.dto.ClienteDTO;
import com.nossobancodigital.nossobancodigital.domain.exceptions.RegraNegocioException;
import com.nossobancodigital.nossobancodigital.domain.model.Cliente;
import com.nossobancodigital.nossobancodigital.domain.service.CadastroClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private CadastroClienteService cadastroClienteService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<?> adicionar(@Valid @RequestBody ClienteDTO clienteDTO) {

        Cliente cliente;
        cliente = toCliente(clienteDTO);

        try {
            cliente = cadastroClienteService.salvar(cliente);
        } catch (RegraNegocioException e) {
            throw new RegraNegocioException(e.getMessage(), e);
        }

        clienteDTO = toClienteDTO(cliente);

        var params =  new ArrayList<>();
        params.add(cliente.getIdCliente());
        String headerLocation = Util.headerBuilder(ClienteEnderecoController.class, params);

        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, headerLocation).body(clienteDTO);
    }

    private Cliente toCliente(ClienteDTO clienteDTO) {
        return mapper.map(clienteDTO, Cliente.class);
    }

    private ClienteDTO toClienteDTO(Cliente cliente) {
        return mapper.map(cliente, ClienteDTO.class);
    }

}
