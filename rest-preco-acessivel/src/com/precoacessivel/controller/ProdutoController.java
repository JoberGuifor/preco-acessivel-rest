package com.precoacessivel.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.precoacessivel.dao.EstabelecimentoDAO;
import com.precoacessivel.dao.ProdutoDAO;
import com.precoacessivel.entidade.Estabelecimento;
import com.precoacessivel.entidade.Produto;

public class ProdutoController {
	
	private ProdutoDAO prodDAO = null;
	private EstabelecimentoDAO estDAO = null;
	
	public ProdutoController() {
		prodDAO = new ProdutoDAO();
		estDAO = new EstabelecimentoDAO();
	}
	
	public boolean addProduto(Produto prod){
		
		Produto prodBusca = null;
		Estabelecimento estBusca = null;
		long idProduto;
		
		try {
			prodBusca = prodDAO.buscarProdutoPorCodBarrasDescricao(prod.getCodbar(), prod.getDescricao());
			if(prodBusca != null){
				prod.setIdproduto(prodBusca.getIdproduto());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
			
		
		if(prodBusca == null){
			try {
				idProduto = prodDAO.addProduto(prod);
				prod.setIdproduto(idProduto);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}else if(prod.getDescricao().length() > prodBusca.getDescricao().length()){
			//verifica se o produto tem a descrição mais detalhada que o ja inserido e altera.
			try {
				prodDAO.editarProduto(prod, prodBusca.getIdproduto());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			estBusca = estDAO.buscarEstabelecimentoPorCod(prod.getEstab().getCodigo());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		if(estBusca == null){
			try {
				estDAO.addEstabelecimento(prod.getEstab());
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		prodBusca = null;
		try {
			prodBusca = prodDAO.buscarProdutoEstabelecimento(prod.getIdproduto(), prod.getEstab().getCodigo());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		if(prodBusca == null){
			if(prodDAO.addProdutoEstabelecimento(prod) == 0){
				return false;
			}
		}
		
		return true;
	}
	
	public ArrayList<Produto> buscaProdutos(String descr, String local, int distancia){
		
		ArrayList<Produto> prods = null;
		
		//Primeiro faz a busca no nota-Paraná
		BuscaNotaParana buscaNP = new BuscaNotaParana();
		
		try {
			prods = buscaNP.buscaProdutos(descr, local, distancia);
		} catch (IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
			return null;
		}
		
		/*
		for(int i = 0;i<prods.size();i++){
			Produto p = prods.get(i);
			addProduto(p);
		}
		*/
		
		return prods;
	}
	
	public long addProdutoEstabelecimento(Produto prod){
		return prodDAO.addProdutoEstabelecimento(prod);
	}
	public ArrayList<Produto> buscaTodosProdsEstabelecimento(Estabelecimento est){
		
		ArrayList<Produto> prods = null;
		
		try {
			prods = prodDAO.buscaTodosProdsEstabelecimento(est);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return prods;
	}
	
	public Produto buscarProdutoEstabelecimento(long cod, String codEstab, String data){
		Produto prod = null;
		
		try {
			prod = prodDAO.buscarProdutoEstabelecimento(cod, codEstab, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return prod;
		
	}
	public void removerProdutoPromocao(long idpromocao){
		
		try {
			prodDAO.removerProdutoPromocao(idpromocao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public long addProdutoPromocao(Produto prodNormal, Produto prodProm){
		
		long idprom = 0;
		
		try {
			idprom = prodDAO.addProdutoPromocao(prodNormal, prodProm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return idprom;
		
	}
	
	public long buscarProdutoPromocao(long codProdEst){
		
		long codprom = 0;
		
		try {
			codprom = prodDAO.buscarProdutoPromocao(codProdEst);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return codprom;
		
	}
	
	public ArrayList<Produto> buscaPromocoesProximas(String local){
		ArrayList<Produto> prods = null;
		
		try {
			prods = prodDAO.buscaPromocoesProximas(local);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prods;
	}
	
	public ArrayList<Produto> buscaPromocoesPorEstabelecimento(String local){
		ArrayList<Produto> prods = null;
		
		try {
			prods = prodDAO.buscaPromocoesPorEstabelecimento(local);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prods;
		
	}
}
