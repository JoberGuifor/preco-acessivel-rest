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

import com.precoacessivel.controller.BuscaController;
import com.precoacessivel.controller.EstabelecimentoController;
import com.precoacessivel.controller.ProdutoController;
import com.precoacessivel.entidade.Estabelecimento;
import com.precoacessivel.entidade.Produto;

@Path("estabelecimento")
public class EstabelecimentoService {
	
	private EstabelecimentoController estabControl;
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	@PostConstruct
	private void init(){
		estabControl = new EstabelecimentoController();
	}
	
	@GET
	@Path("/proximos/{local}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public ArrayList<Estabelecimento> buscaEstabelecimentosProx(@PathParam("local") String local) {
		
		ArrayList<Estabelecimento> ests = estabControl.buscarEstabelecimentosProx(local);
		
		return ests;
		
	}
	
}
