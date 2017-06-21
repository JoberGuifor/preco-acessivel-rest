package com.precoacessivel.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import com.precoacessivel.entidade.Estabelecimento;
import com.precoacessivel.entidade.Produto;


public class BuscaNotaParana {
	
	private static String URL_NOTAPARANA = "https://menorpreco.notaparana.pr.gov.br/api/v1/produtos";
	
	public static boolean isNumber(String valor){
		
		long numero;
		boolean ehNumero = false;
		
		try {
			numero = (Long.parseLong(valor));
	        ehNumero = true;
		} catch (NumberFormatException e) {	  
	        ehNumero = false;
		}
		
		return ehNumero;
		
	}
	
	public ArrayList<Produto> buscaProdutos(String descr, String local, int distancia) throws IOException, ParserConfigurationException, SAXException{
		
		String url_complementar;
		ArrayList<Produto> prodsRet = null;
		
		if(descr.equals("") || distancia < 0){
			return prodsRet;
		}
		
		//se for somente numeros busca pelo cod de barras
		if(isNumber(descr)){
			url_complementar = "?gtin="+descr+"&local="+local+"&raio="+distancia+"&offset=0&ordem_val=asc&data=2";
		}else{
			url_complementar = "?termo="+descr+"&local="+local+"&raio="+distancia+"&offset=0&ordem_val=asc&data=2";
		}
		
		URL url = new URL(URL_NOTAPARANA+url_complementar);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		try{
			if (conn.getResponseCode() != 200) {
				System.out.print(" retornou falha req NP: "+conn.getResponseCode());
				conn.disconnect();
				return prodsRet;
			}
		}catch(ConnectException c){
			conn.disconnect();
			return prodsRet;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		
		String output;
		String retornoCompleto = "";
		//prodsRet = new ArrayList<Produto>();
		
		while ((output = br.readLine()) != null) {
			retornoCompleto += output;
		}
		
		BuscaController bc = new BuscaController();
		
		prodsRet = bc.formataProdutosJson(retornoCompleto);
		
		BuscaController busca = new BuscaController();
		busca.addBusca(retornoCompleto);
		
		conn.disconnect();
			
		return prodsRet;
	}

}
