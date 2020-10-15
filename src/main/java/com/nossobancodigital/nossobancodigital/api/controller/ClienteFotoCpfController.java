package com.nossobancodigital.nossobancodigital.api.controller;

import com.nossobancodigital.nossobancodigital.api.Util;
import com.nossobancodigital.nossobancodigital.api.dto.ClienteCpfFotoDTOin;
import com.nossobancodigital.nossobancodigital.api.dto.ClienteCpfFotoDTOout;
import com.nossobancodigital.nossobancodigital.domain.exceptions.RegraNegocioException;
import com.nossobancodigital.nossobancodigital.domain.model.Cliente;
import com.nossobancodigital.nossobancodigital.domain.model.ClienteCpfFoto;
import com.nossobancodigital.nossobancodigital.domain.repository.ClienteRepository;
import com.nossobancodigital.nossobancodigital.domain.service.CadastroClienteCpfFotoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;


@RestController
@RequestMapping("/clientes-cpf")
public class ClienteFotoCpfController {

    private final String DEFINE_LOCAL_OR_CLOUD_PATH = "";
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private CadastroClienteCpfFotoService cadastroClienteCpfFotoService;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ModelMapper mapper;

    @PutMapping(path = "/{idCliente}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizarFoto(@PathVariable Long idCliente, @Valid ClienteCpfFotoDTOin clienteFotoCpfDTOin) {

        var nomeArquivo = UUID.randomUUID().toString()
                + "_" + clienteFotoCpfDTOin.getArquivo().getOriginalFilename();

        var arquivoFoto = Path.of(DEFINE_LOCAL_OR_CLOUD_PATH, nomeArquivo);

        try {
            clienteFotoCpfDTOin.getArquivo().transferTo(arquivoFoto);
        } catch (NoSuchFileException e) {
            //Como a implemtação é para fins didáticos, e para quem quiser rodar o projeto não ser parado neste método, o mesmo trata execeção para diretório não localizado
            //Nas próximas etapas, deverão ser definidas pré-congiurações para salvar arquivos na nuvem ou em storage em um servidor local
            logger.error("Configurar storage de imagens na aws ou storage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new RegraNegocioException(
                String.format("Não existe cadastro de cliente com código %d", idCliente)));

        MultipartFile file = clienteFotoCpfDTOin.getArquivo();

        var cpfFoto = ClienteCpfFoto.builder()
                .id(cliente.getIdCliente())
                .cliente(cliente)
                .descricao(clienteFotoCpfDTOin.getDescricao())
                .contentType(file.getContentType())
                .tamanho(file.getSize())
                .nomeArquivo(file.getOriginalFilename())
                .build();

        cpfFoto = cadastroClienteCpfFotoService.salvar(cpfFoto);

        var params = new ArrayList<>();
        params.add(idCliente);
        String headerLocation = Util.headerBuilder(ClientePropostaController.class, params);

        ClienteCpfFotoDTOout cpfFotoDTOout = toClienteCpfFotoDTOout(cpfFoto);

        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, headerLocation).body(cpfFotoDTOout);

    }

    private ClienteCpfFotoDTOout toClienteCpfFotoDTOout(ClienteCpfFoto clienteCpfFoto) {
        return mapper.map(clienteCpfFoto, ClienteCpfFotoDTOout.class);
    }

}
