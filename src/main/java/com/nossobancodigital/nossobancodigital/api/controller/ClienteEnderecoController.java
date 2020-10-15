package com.nossobancodigital.nossobancodigital.api.controller;

import com.nossobancodigital.nossobancodigital.api.Util;
import com.nossobancodigital.nossobancodigital.api.dto.ClienteDTO;
import com.nossobancodigital.nossobancodigital.api.dto.ClienteEnderecoDTO;
import com.nossobancodigital.nossobancodigital.domain.exceptions.RegraNegocioException;
import com.nossobancodigital.nossobancodigital.domain.listeners.ClienteListener;
import com.nossobancodigital.nossobancodigital.domain.model.Cliente;
import com.nossobancodigital.nossobancodigital.domain.model.ClienteEndereco;
import com.nossobancodigital.nossobancodigital.domain.repository.ClienteEnderecoRepository;
import com.nossobancodigital.nossobancodigital.domain.repository.ClienteRepository;
import com.nossobancodigital.nossobancodigital.domain.service.CadastroClienteEnderecoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/clientes-endereco")
public class ClienteEnderecoController {

    @Autowired
    private CadastroClienteEnderecoService cadastroClienteEnderecoService;
    @Autowired
    private ClienteEnderecoRepository clienteEnderecoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ClienteListener clienteListener;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/{idCliente}")
    public ResponseEntity<?> adicionar(@PathVariable Long idCliente, @Valid @RequestBody ClienteEnderecoDTO clienteEnderecoDTO) {

        ClienteEndereco clienteEndereco = clienteEndereco = toClienteEndereco(clienteEnderecoDTO);

        Cliente oldCliente = clienteRepository.findById(idCliente).orElseThrow(() -> new RegraNegocioException(
                String.format("Não existe cadastro de cliente com código %d", idCliente)));

        Cliente newCliente = clienteEndereco.getCliente();
        newCliente.setIdCliente(idCliente);

        clienteListener.run(oldCliente, newCliente);
        ClienteEndereco oldClienteEndereco = clienteEnderecoRepository.findByCliente(clienteEndereco.getCliente());

        if (oldClienteEndereco != null) {
            clienteEndereco.setIdClienteEndereco(oldClienteEndereco.getIdClienteEndereco());
        }

        try {
            clienteEndereco = cadastroClienteEnderecoService.salvar(clienteEndereco);
        } catch (RegraNegocioException e) {
            throw new RegraNegocioException(e.getMessage(), e);
        }

        var params =  new ArrayList<>();
        params.add(idCliente);
        String headerLocation = Util.headerBuilder(ClienteFotoCpfController.class, params);

        clienteEnderecoDTO = toClienteEndercoDTO(clienteEndereco);
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, headerLocation).body(clienteEnderecoDTO);
    }

    private ClienteEnderecoDTO toClienteEndercoDTO(ClienteEndereco clienteEndereco) {
        return mapper.map(clienteEndereco, ClienteEnderecoDTO.class);
    }

    private ClienteEndereco toClienteEndereco(ClienteEnderecoDTO clienteEnderecoDTO) {
        return mapper.map(clienteEnderecoDTO, ClienteEndereco.class);
    }

    private void teste(){
        List<Object> obj =  new ArrayList<>();
        //obj.forEach((valor) -> hyperMedia.append("/".concat(valor.toString())));
    }

    public ClienteEnderecoController(){

    }

}
