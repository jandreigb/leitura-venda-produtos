package br.com.jgb.leituraVendaProdutos.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	@Autowired
	private ProdutoService produtoService;

	/**
	 * Efetua a leitura de dados, através dos arquivos armazenados
	 */
	public void efetuaLeituraDados() {

		System.out.println("********** Inicialização da leitura de arquivos...");
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
						System.out.println("----- Erro na leirura do arquivo.");
						e.printStackTrace();
					}
				});

			}

		} catch (IOException e) {
			System.out.println("----- Erro na leirura dos arquivos.");
			e.printStackTrace();
		}

		System.out.println("********** Fim da leitura de arquivos. ");
		long tempoFinal = System.currentTimeMillis();
		System.out.printf("Duração: %.3f segundos", (tempoFinal - tempoInicial) / 1000d);
	}
}