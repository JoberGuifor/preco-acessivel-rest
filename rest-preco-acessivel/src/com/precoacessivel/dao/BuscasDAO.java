package com.precoacessivel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.precoacessivel.config.BDConfig;
import com.precoacessivel.entidade.Buscas;
import com.precoacessivel.entidade.Usuario;

public class BuscasDAO {
	
	public void addBusca(String retBusca) throws Exception {
		
		Connection conexao = BDConfig.getConnection();

		String sql = "INSERT INTO buscas(resultado) VALUES(?)";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, retBusca);
		statement.execute();
		
		conexao.close();
		
	}
	
	public ArrayList<Buscas> listarBuscas(int numbuscas) throws Exception{
		ArrayList<Buscas> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM buscas limit "+numbuscas;

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Buscas busc = new Buscas();
			busc.setIdbusca(rs.getLong("idbusca"));
			busc.setResultado(rs.getString("resultado"));
			busc.setProcessado(rs.getString("processado"));

			lista.add(busc);
		}
		
		conexao.close();

		return lista;
	}
	
	public ArrayList<Buscas> listarBuscasProcessadas() throws Exception{
		ArrayList<Buscas> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM buscas WHERE processado = 'T'";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Buscas busc = new Buscas();
			busc.setIdbusca(rs.getLong("idbusca"));
			busc.setResultado(rs.getString("resultado"));
			busc.setProcessado(rs.getString("processado"));

			lista.add(busc);
		}
		
		conexao.close();

		return lista;
	}
	
	public void removerBusca(long idBusca) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "DELETE FROM buscas WHERE idbusca = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setLong(1, idBusca);
		statement.execute();
		
		conexao.close();
		
	}
	
	public void editarBusca(Buscas busca) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "UPDATE buscas SET resultado = ?, processado = ? WHERE idbusca = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, busca.getResultado());
		statement.setString(2, busca.getProcessado());
		statement.setLong(3, busca.getIdbusca());
		statement.execute();
		conexao.close();
	}

}
