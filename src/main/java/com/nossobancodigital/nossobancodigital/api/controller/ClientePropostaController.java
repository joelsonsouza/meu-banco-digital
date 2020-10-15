package com.nossobancodigital.nossobancodigital.api.controller;

import com.nossobancodigital.nossobancodigital.api.Util;
import com.nossobancodigital.nossobancodigital.api.dto.ClienteDTO;
import com.nossobancodigital.nossobancodigital.api.dto.ClientePropostaContaDTO;
import com.nossobancodigital.nossobancodigital.api.dto.ClientePropostaDTOin;
import com.nossobancodigital.nossobancodigital.api.dto.ClientePropostaDTOout;
import com.nossobancodigital.nossobancodigital.domain.exceptions.DadosIncompletosException;
import com.nossobancodigital.nossobancodigital.domain.model.Cliente;
import com.nossobancodigital.nossobancodigital.domain.model.ClienteCpfFoto;
import com.nossobancodigital.nossobancodigital.domain.model.ClienteEndereco;
import com.nossobancodigital.nossobancodigital.domain.model.ClientePropostaConta;
import com.nossobancodigital.nossobancodigital.domain.repository.ClienteCpfFotoRepesitory;
import com.nossobancodigital.nossobancodigital.domain.repository.ClienteEnderecoRepository;
import com.nossobancodigital.nossobancodigital.domain.repository.ClienteRepository;
import com.nossobancodigital.nossobancodigital.domain.service.ClientePropostaContaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/clientes-proposta")
public class ClientePropostaController {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ClienteEnderecoRepository clienteEnderecoRepository;
    @Autowired
    private ClienteCpfFotoRepesitory clienteCpfFotoRepesitory;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ClientePropostaContaService clientePropostaContaService;

    @PostMapping("/{idCliente}")
    public ResponseEntity<?> adicionar(@PathVariable Long idCliente, @RequestBody ClientePropostaDTOin clientePropostaDTOin) {
        var params = new ArrayList<>();
        params.add(idCliente);
        String headerLocation = Util.headerBuilder(ClientePropostaController.class, params);

        //clienteEnderecoDTO = toClienteEndercoDTO(clienteEndereco);
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, headerLocation).body(null);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<?> apresentarDadosProposta(@PathVariable Long idCliente) {

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new DadosIncompletosException(
                String.format("Não existe cadastro de cliente com código %d", idCliente)));

        ClienteEndereco clienteEndereco = clienteEnderecoRepository.findByCliente(cliente);

        if (clienteEndereco == null) {
           throw  new DadosIncompletosException(
                    String.format("Não existe cadastro de endereco para o cliente com código %d", idCliente));
        }

        ClienteCpfFoto clienteCpfFoto = clienteCpfFotoRepesitory.findById(cliente.getIdCliente()).orElseThrow(() -> new DadosIncompletosException(
                String.format("Não existe cadastro de foto do cpf para o cliente com código %d", idCliente)));


        ClientePropostaDTOout clientePropostaDTOout = toClientePropostaDTOout(cliente, clienteEndereco, clienteCpfFoto);

        //Opções devoldidas utilizando a Expecificação HAL
        clientePropostaDTOout.add(linkTo(ClientePropostaController.class).slash(idCliente).slash("aceitar").withSelfRel());
        clientePropostaDTOout.add(linkTo(ClientePropostaController.class).slash(idCliente).slash("rejeitar").withSelfRel());
        clientePropostaDTOout.add(linkTo(ClienteController.class).slash(idCliente).withRel("corrigir-dados"));
        clientePropostaDTOout.add(linkTo(ClienteEnderecoController.class).slash(idCliente).withRel("corrigir-endereco"));
        clientePropostaDTOout.add(linkTo(ClienteFotoCpfController.class).slash(idCliente).withRel("corrigir-cpf-foto"));

        return ResponseEntity.status(HttpStatus.CREATED).body(clientePropostaDTOout);
    }

    private ClientePropostaDTOout toClientePropostaDTOout(Cliente cliente, ClienteEndereco clienteEndereco, ClienteCpfFoto clienteCpfFoto) {
        return ClientePropostaDTOout.builder()
                .nome(cliente.getNome())
                .sobrenome(cliente.getSobrenome())
                .email(cliente.getEmail())
                .dataNascimento(cliente.getDataNascimento())
                .cpf(cliente.getCpf())
                .tipoPessoa(cliente.getTipoPessoa())
                .cep(clienteEndereco.getCep())
                .rua(clienteEndereco.getRua())
                .bairro(clienteEndereco.getBairro())
                .complemento(clienteEndereco.getComplemento())
                .cidade(clienteEndereco.getCidade())
                .estado(clienteEndereco.getEstado())
                .descricaoCpfFoto(clienteCpfFoto.getDescricao())
                .build();
    }

    @PostMapping("{idCliente}/aceitar")
    public ResponseEntity<?> aceitar(@PathVariable Long idCliente) {

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new DadosIncompletosException(
                String.format("Não existe cadastro de cliente com código %d", idCliente)));

        ClienteEndereco clienteEndereco = clienteEnderecoRepository.findByCliente(cliente);

        if (clienteEndereco == null) {
            throw  new DadosIncompletosException(
                    String.format("Não existe cadastro de endereco para o cliente com código %d", idCliente));
        }

        var params = new ArrayList<>();
        params.add(idCliente);
        String headerLocation = Util.headerBuilder(ClientePropostaController.class, params);

        ClientePropostaConta clientePropostaConta = ClientePropostaConta.builder()
                .cliente(cliente)
                .build();

        clientePropostaConta.confirmar();

        //Implementar chamada p/ envio de e-mail

        clientePropostaContaService.salvar(clientePropostaConta);

        ClientePropostaContaDTO clientePropostaContaDTO =  mapper.map(clientePropostaConta, ClientePropostaContaDTO.class);
        ClienteDTO clienteDTO = mapper.map(clientePropostaConta.getCliente(), ClienteDTO.class);
        clientePropostaContaDTO.setClienteDTO(clienteDTO);

        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, headerLocation).body(clientePropostaContaDTO);
    }

    @PostMapping("{idCliente}/recusar")
    public ResponseEntity<?> recusar(@PathVariable Long idCliente) {

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new DadosIncompletosException(
                String.format("Não existe cadastro de cliente com código %d", idCliente)));

        ClienteEndereco clienteEndereco = clienteEnderecoRepository.findByCliente(cliente);

        if (clienteEndereco == null) {
            throw  new DadosIncompletosException(
                    String.format("Não existe cadastro de endereco para o cliente com código %d", idCliente));
        }


        ClientePropostaConta clientePropostaConta = ClientePropostaConta.builder()
                .cliente(cliente)
                .build();

        clientePropostaConta.recusar();

        //Implementar chamada p/ envio de e-mail

        clientePropostaContaService.salvar(clientePropostaConta);


        var params = new ArrayList<>();
        params.add(idCliente);
        String headerLocation = Util.headerBuilder(ClientePropostaController.class, params);
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, headerLocation).body(null);
    }


}
