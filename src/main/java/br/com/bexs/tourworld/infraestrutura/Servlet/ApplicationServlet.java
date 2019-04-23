package br.com.bexs.tourworld.infraestrutura.Servlet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.com.bexs.tourworld.aplicacao.controller.RotaController;

@ApplicationPath("resource")
public class ApplicationServlet extends Application {

	private static String nomeArquivo;

	@Override
	public Set<Class<?>> getClasses() {		
		readProperties();

		Set<Class<?>> classes = new HashSet<>();
		classes.add(RotaController.class);

		return classes;
	}

	private void readProperties() {
		Map<String, Object> properties = new HashMap<>();
		properties.put("jersey.config.server.provider.packages", "br.com.bexs.tourworld.aplicacao.controller");
		nomeArquivo = getClass().getClassLoader().getResource("input-routes.csv").getFile();
	}

	public static String getNomeArquivo() {
		return nomeArquivo;
	}
}
