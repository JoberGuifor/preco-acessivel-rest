package com.precoacessivel.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.precoacessivel.config.BDConfig;
import com.precoacessivel.entidade.Estabelecimento;


public class EstabelecimentoDAO {
	
	public void addEstabelecimento(Estabelecimento est) throws Exception {
		
		Connection conexao = BDConfig.getConnection();

		String sql = "INSERT INTO estabelecimentos(codigo, fantasia, razao_social, endereco, local) VALUES(?, ?, ?, ?, ?)";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, est.getCodigo());
		statement.setString(2, est.getFantasia());
		statement.setString(3, est.getRazao_social());
		statement.setString(4, est.getEndereco());
		statement.setString(5, est.getLocal());
		statement.execute();
		
		conexao.close();
		
	}
	
	public Estabelecimento buscarEstabelecimentoPorCod(String cod) throws Exception {
		Estabelecimento est = null;

		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM estabelecimentos WHERE codigo = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, cod);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			est = new Estabelecimento();
			est.setCodigo(rs.getString("codigo"));
			est.setFantasia(rs.getString("fantasia"));
			est.setRazao_social(rs.getString("razao_social"));
			est.setEndereco(rs.getString("endereco"));
			est.setLocal(rs.getString("local"));
		}
		
		conexao.close();
		
		return est;
	}
	
	public ArrayList<Estabelecimento> buscarEstabelecimentosProx(String local) throws Exception {
		ArrayList<Estabelecimento> ests = new ArrayList<Estabelecimento>();

		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM estabelecimentos WHERE SUBSTR(local, 0, 7) = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, local.substring(0, 6));
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Estabelecimento est = new Estabelecimento();
			est.setCodigo(rs.getString("codigo"));
			est.setFantasia(rs.getString("fantasia"));
			est.setRazao_social(rs.getString("razao_social"));
			est.setEndereco(rs.getString("endereco"));
			est.setLocal(rs.getString("local"));
			ests.add(est);
		}
		
		conexao.close();
		
		return ests;
	}
	
	public ArrayList<Estabelecimento> buscarEstabelecimentos(int qtd) throws Exception {
		ArrayList<Estabelecimento> ests = new ArrayList<Estabelecimento>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select * from estabelecimentos WHERE codigo not in "
						+ " (select cod_estabelecimento from estabelecimentos_monitorados) limit ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, qtd);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Estabelecimento est = new Estabelecimento();
			est.setCodigo(rs.getString("codigo"));
			est.setFantasia(rs.getString("fantasia"));
			est.setRazao_social(rs.getString("razao_social"));
			est.setEndereco(rs.getString("endereco"));
			est.setLocal(rs.getString("local"));
			ests.add(est);
		}
		
		conexao.close();
		
		return ests;
	}
	
	public void addEstabelecimentoMonitorado(Estabelecimento est) throws Exception {
		
		Connection conexao = BDConfig.getConnection();

		String sql = "INSERT INTO estabelecimentos_monitorados VALUES(?, ?)";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, est.getCodigo());
		Date data = new Date(System.currentTimeMillis());  
    	SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
    	java.sql.Date dt = java.sql.Date.valueOf(formatarDate.format(data));
		statement.setDate(2, dt);
		statement.execute();
		
		conexao.close();
		
	}
	
	public long[] buscarTotalEstabelecimentos() throws Exception {
		
		long tot[] = new long[2];
		
		Connection conexao = BDConfig.getConnection();

		String sql = "select * from "
			+" (select Count(codigo) as totest from estabelecimentos) as estabs, "
			+" (select Count(cod_estabelecimento) as totestmon from estabelecimentos_monitorados) as estabsmon";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			tot[0] = rs.getLong("totest"); //total geral de estabelecimentos
			tot[1] = rs.getLong("totestmon"); //total de estabelecimentos ja monitorados
		}
		
		conexao.close();
		
		return tot;
	}
	
	public void removerTodosEstabelecimentosMonitorados() throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "DELETE FROM estabelecimentos_monitorados";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.execute();
		
		conexao.close();
		
	}
	
}
