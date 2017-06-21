package com.precoacessivel.controller;

import java.util.ArrayList;

import com.precoacessivel.dao.EstabelecimentoDAO;
import com.precoacessivel.entidade.Estabelecimento;

public class EstabelecimentoController {
	private EstabelecimentoDAO estDAO;
	
	public EstabelecimentoController() {
		estDAO = new EstabelecimentoDAO();
	}
	
	public ArrayList<Estabelecimento> buscarEstabelecimentosProx(String local){
		
		ArrayList<Estabelecimento> estabs = null;
		
		try {
			estabs = estDAO.buscarEstabelecimentosProx(local);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return estabs;
		
	}
	
	public long[] buscarTotalEstabelecimentos(){
		long tot[] = null;
		
		try {
			tot = estDAO.buscarTotalEstabelecimentos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tot;
	}
	
	public void removerTodosEstabelecimentosMonitorados(){
		
		try {
			estDAO.removerTodosEstabelecimentosMonitorados();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
