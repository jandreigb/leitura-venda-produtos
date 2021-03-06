package br.com.jgb.leituraVendaProdutos.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jgb.leituraVendaProdutos.dto.ArquivoProdutos;

/**
 * Realiza a leitura de dados
 */
@Service
public class LeituraDadosService {

	Logger logger = LoggerFactory.getLogger(LeituraDadosService.class);

	@Autowired
	private ProdutoService produtoService;

	/**
	 * Efetua a leitura de dados, através dos arquivos armazenados
	 */
	public void efetuaLeituraDados() {

		logger.trace("********** Inicialização da leitura de arquivos...");

		long tempoInicial = System.currentTimeMillis();

		try {

			String scannedPackage = "arquivos/*";
			PathMatchingResourcePatternResolver scanner = new PathMatchingResourcePatternResolver();
			Resource[] resources = scanner.getResources(scannedPackage);
			List<Resource> arquivos = new ArrayList<Resource>(Arrays.asList(resources));

			if (resources != null && resources.length > 0) {

				ObjectMapper mapper = new ObjectMapper();
				TypeReference<ArquivoProdutos> typeReference = new TypeReference<ArquivoProdutos>() {
				};

				// leitura de forma paralela
				arquivos.parallelStream().forEach(resource -> {

					try {
						ArquivoProdutos arquivo = mapper.readValue(resource.getInputStream(), typeReference);
						produtoService.salvar(arquivo.getData(), resource.getFilename());

					} catch (IOException e) {
						logger.error("Erro na leirura do arquivo.", e);
					}
				});

			}

		} catch (IOException e) {
			logger.error("Erro na leirura dos arquivos.", e);
		}

		logger.trace("********** Fim da leitura de arquivos. ");
		long tempoFinal = System.currentTimeMillis();
		logger.trace("Duração: %.3f segundos", (tempoFinal - tempoInicial) / 1000d);
	}
}