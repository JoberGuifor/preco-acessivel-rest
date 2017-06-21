package com.precoacessivel.rest;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.precoacessivel.controller.ProdutoController;
import com.precoacessivel.dao.UsuarioDAO;
import com.precoacessivel.entidade.Produto;
import com.precoacessivel.entidade.Usuario;

@Path("/produtos")
public class ProdutoService {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	private ProdutoController prodController;
	
	@PostConstruct
	private void init(){
		prodController = new ProdutoController();
	}
	
	@GET
	@Path("/get/")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public ArrayList<Produto> buscaProdutos(@QueryParam("termo") @DefaultValue("") String termo,
			@QueryParam("local") @DefaultValue("") String local, 
			@QueryParam("dist") @DefaultValue("0") int distancia) {
		
		ArrayList<Produto> prods = null;
		
		prods = prodController.buscaProdutos(termo, local, distancia);
		
		return prods;
	}
	
	@GET
	@Path("/promocoesproximas/{local}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public ArrayList<Produto> buscaPromocoesProximas(@PathParam("local") String local) {
		
		ArrayList<Produto> prods = null;
		
		prods = prodController.buscaPromocoesProximas(local);
		
		return prods;
		
	}
	
	@GET
	@Path("/promocoesestabelecimento/{local}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public ArrayList<Produto> buscaPromocoesPorEstabelecimento(@PathParam("local") String local) {
		
		ArrayList<Produto> prods = null;
		
		prods = prodController.buscaPromocoesPorEstabelecimento(local);
		
		return prods;
		
	}
	
}
