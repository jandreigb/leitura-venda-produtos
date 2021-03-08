package br.com.jgb.leituraVendaProdutos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.jgb.leituraVendaProdutos.service.LeituraDadosService;

/**
 * Inicialzação da aplicação com a leitura dos dados
 */
@Component
public class Inicializacao implements ApplicationRunner {

	@Autowired
	private LeituraDadosService leituraDadosService;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		leituraDadosService.efetuaLeituraDados();
	}
}