package com.precoacessivel.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.precoacessivel.dao.BuscasDAO;
import com.precoacessivel.entidade.Buscas;
import com.precoacessivel.entidade.Estabelecimento;
import com.precoacessivel.entidade.Produto;

public class BuscaController {
	
	private BuscasDAO buscaDao;
	
	public BuscaController() {
		buscaDao = new BuscasDAO();
	}
	
	public void addBusca(String retBusca){
		try {
			buscaDao.addBusca(retBusca);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Buscas> listarBuscas(int numbuscas){
		
		ArrayList<Buscas> ret = null;
		
		try {
			ret = buscaDao.listarBuscas(numbuscas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
		
	}
	
	public ArrayList<Buscas> listarBuscasProcessadas(){
		
		ArrayList<Buscas> ret = null;
		
		try {
			ret = buscaDao.listarBuscasProcessadas();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
		
	}
	
	public void removerBusca(long idBusca){
		
		try {
			buscaDao.removerBusca(idBusca);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void editarBusca(Buscas busca){
		
		try {
			buscaDao.editarBusca(busca);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Produto> formataProdutosJson(String jsonProds){
		ArrayList<Produto> prodsRet = new ArrayList<Produto>();
		
		//instancia um novo JSONObject passando a string como entrada
		JSONObject json_completo = new JSONObject(jsonProds);
		
		JSONArray prods = json_completo.getJSONArray("produtos");
		
		for(int i = 0;i<prods.length();i++){
			Produto p = new Produto();
			JSONObject jp1 = (JSONObject)prods.get(i);
			
			p.setDescricao(jp1.getString("desc"));
			try{
				p.setCodbar(jp1.getLong("gtin"));
			}catch(JSONException e){
				p.setCodbar(0);
			}
			p.setNcm(jp1.getLong("ncm"));
			p.setValor(jp1.getDouble("valor"));
			p.setDatamov(jp1.getString("datahora").substring(0, jp1.getString("datahora").indexOf("T")));
			
			JSONObject jestab = jp1.getJSONObject("estabelecimento");
			
			Estabelecimento est = new Estabelecimento();
			est.setCodigo(jestab.getString("codigo"));
			est.setFantasia(jestab.getString("nm_fan"));
			est.setRazao_social(jestab.getString("nm_emp"));
			est.setEndereco(jestab.getString("tp_logr") +" " + jestab.getString("nm_logr") + ", " 
					+ jestab.getString("nr_logr")+ " - Bairro "+jestab.getString("bairro")+", "+jestab.getString("mun"));
			est.setLocal(jp1.getString("local"));
			
			p.setEstab(est);
			
			prodsRet.add(p);
			
		}
		
		return prodsRet;
		
	}
	
}
