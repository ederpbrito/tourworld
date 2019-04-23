package br.com.bexs.tourworld.aplicacao.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.bexs.tourworld.aplicacao.service.RotaService;
import br.com.bexs.tourworld.domain.model.Rota;
import br.com.bexs.tourworld.infraestrutura.Servlet.ApplicationServlet;


@Path("rota")
public class RotaController {
	
	private RotaService service;
	private String nomeArquivo;	
	
	public RotaController() throws IllegalAccessException, IOException {
		nomeArquivo = ApplicationServlet.getNomeArquivo();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("get/{rota}") 
	public String getRota(@PathParam("rota") String	rota) throws IllegalAccessException {
		
		service =  new RotaService(rota, nomeArquivo);
		return service.getMelhorRota();
	}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response addRota(String r) throws IllegalAccessException, FileNotFoundException {
		
		String[] rota = r.split(",");		
		
		Rota rota1 = new Rota(rota[0], rota[1], Integer.parseInt(rota[2]));
		
		service =  new RotaService(nomeArquivo);
		service.addRota(rota1);
		
		return	Response.ok().build(); 
	}
}
