package com.precoacessivel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.precoacessivel.config.BDConfig;
import com.precoacessivel.entidade.Estabelecimento;
import com.precoacessivel.entidade.Produto;

public class ProdutoDAO {
	
	public long addProduto(Produto prod) throws Exception {
		long idGerado = 0;
		Connection conexao = BDConfig.getConnection();

		String sql = "INSERT INTO produtos(codbar, descricao, ncm) VALUES(?, ?, ?)";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setLong(1, prod.getCodbar());
		statement.setString(2, prod.getDescricao());
		statement.setLong(3, prod.getNcm());
		statement.execute();
		
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idGerado = rs.getLong(1);
		}
		
		prod.setIdproduto(idGerado);
		
		conexao.close();
		
		return idGerado;
	}
	
	public Produto buscarProdutoPorCodBarras(long cod) throws Exception {
		Produto prod = null;

		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM produtos WHERE codbar = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setLong(1, cod);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			prod = new Produto();
			prod.setIdproduto(rs.getLong("idproduto"));
			prod.setCodbar(rs.getLong("codbar"));
			prod.setDescricao(rs.getString("descricao"));
			prod.setNcm(rs.getLong("ncm"));
		}
		
		conexao.close();

		return prod;
	}
	
	public Produto buscarProdutoPorCodBarrasDescricao(long cod, String descr) throws Exception {
		Produto prod = null;
		String s_where = "WHERE ";
		Connection conexao = BDConfig.getConnection();
		
		if(cod != 0){
			s_where += " codbar = ? ";
		}
		if(descr.length() > 0){
			if(s_where.length() > 6){
				s_where += " or ";
			}
			
			s_where += " descricao = ?";
			
		}
		
		String sql = "SELECT * FROM produtos "+ s_where;

		PreparedStatement statement = conexao.prepareStatement(sql);
		boolean buscaCodBar = false;
		if(s_where.contains("codbar")){
			statement.setLong(1, cod);
			buscaCodBar = true;
		}
		if(buscaCodBar){
			statement.setString(2, descr);
		}else{
			statement.setString(1, descr);
		}
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			prod = new Produto();
			prod.setIdproduto(rs.getLong("idproduto"));
			prod.setCodbar(rs.getLong("codbar"));
			prod.setDescricao(rs.getString("descricao"));
			prod.setNcm(rs.getLong("ncm"));
		}
		
		conexao.close();

		return prod;
	}
	
	public void editarProduto(Produto prod, long idProduto) throws Exception {
		
		Connection conexao = BDConfig.getConnection();

		String sql = "UPDATE produtos SET codbar = ?, descricao = ?, ncm = ? WHERE idproduto = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setLong(1, prod.getCodbar());
		statement.setString(2, prod.getDescricao());
		statement.setLong(3, prod.getNcm());
		statement.setLong(4, idProduto);
		statement.execute();
		conexao.close();
		
	}
	
	public long addProdutoEstabelecimento(Produto prod) {
		
		long idGerado = 0;
		
		Connection conexao = null;
		try {
			conexao = BDConfig.getConnection();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return idGerado;
		}

		String sql = "INSERT INTO produtos_estabelecimentos(idproduto, cod_estabelecimento, valor, datamovimento) VALUES(?, ?, ?, ?)";

		PreparedStatement statement;
		
		try {
			statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setLong(1, prod.getIdproduto());
			statement.setString(2, prod.getEstab().getCodigo());
			statement.setDouble(3, prod.getValor());
			java.sql.Date dtValue = java.sql.Date.valueOf(prod.getDatamov());
			statement.setDate(4, dtValue);
			statement.execute();
			
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				idGerado = rs.getLong(1);
			}
			
			conexao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return idGerado;
		}
		
		return idGerado;
	}
	
	public boolean addProdutoPromocao(Produto prod) {
		
		Connection conexao = null;
		try {
			conexao = BDConfig.getConnection();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}

		String sql = "INSERT INTO produtos_estabelecimentos(idproduto, cod_estabelecimento, valor, datamovimento) VALUES(?, ?, ?, ?)";

		PreparedStatement statement;
		
		try {
			statement = conexao.prepareStatement(sql);
			statement.setLong(1, prod.getIdproduto());
			statement.setString(2, prod.getEstab().getCodigo());
			statement.setDouble(3, prod.getValor());
			java.sql.Date dtValue = java.sql.Date.valueOf(prod.getDatamov());
			
			statement.setDate(4, dtValue);
			statement.execute();
			
			conexao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public Produto buscarProdutoEstabelecimento(long cod, String codEstab) throws Exception {
		Produto prod = null;

		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM produtos_estabelecimentos WHERE idproduto = ? and cod_estabelecimento = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setLong(1, cod);
		statement.setString(2, codEstab);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			prod = new Produto();
			prod.setIdproduto(rs.getLong("idproduto"));
			Estabelecimento est = new Estabelecimento();
			est.setCodigo(rs.getString("cod_estabelecimento"));
			prod.setEstab(est);
			prod.setValor(rs.getDouble("valor"));
			prod.setDatamov(rs.getString("datamovimento"));
		}
		
		conexao.close();

		return prod;
	}
	
	public Produto buscarProdutoEstabelecimento(long cod, String codEstab, String data) throws Exception {
		Produto prod = null;

		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM produtos_estabelecimentos WHERE idproduto = ? and cod_estabelecimento = ? and datamovimento = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setLong(1, cod);
		statement.setString(2, codEstab);
		java.sql.Date dtValue = java.sql.Date.valueOf(data);
		statement.setDate(3, dtValue);
		
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			prod = new Produto();
			prod.setIdproduto(rs.getLong("idproduto"));
			Estabelecimento est = new Estabelecimento();
			est.setCodigo(rs.getString("cod_estabelecimento"));
			prod.setEstab(est);
			prod.setValor(rs.getDouble("valor"));
			prod.setDatamov(rs.getString("datamovimento"));
			prod.setCodprodEst(rs.getLong("idprodest"));
		}
		
		conexao.close();

		return prod;
	}
	
	public ArrayList<Produto> buscaTodosProdsEstabelecimento(Estabelecimento est) throws ClassNotFoundException, SQLException{
		
		ArrayList<Produto> prods = new ArrayList<Produto>();
		Connection conexao = BDConfig.getConnection();

		String sql = "select Distinct ON (pe.idproduto) pe.idproduto,p.codbar,p.descricao,p.ncm,pe.valor, " 
					+" MAX(pe.datamovimento) as datamovimento,pe.cod_estabelecimento, MAX(pe.idprodest) as idprodest "
					+" from produtos_estabelecimentos pe, produtos p "
					+" where pe.idproduto = p.idproduto and cod_estabelecimento = ? " 
					+" group by pe.idproduto,p.codbar,p.descricao,p.ncm,pe.valor,pe.cod_estabelecimento " 
					+" order by pe.idproduto, datamovimento DESC  ";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, est.getCodigo());
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Produto prod = new Produto();
			prod.setIdproduto(rs.getLong("idproduto"));
			prod.setEstab(est);
			prod.setValor(rs.getDouble("valor"));
			prod.setDatamov(rs.getString("datamovimento"));
			prod.setCodbar(rs.getLong("codbar"));
			prod.setDescricao(rs.getString("descricao"));
			prod.setCodprodEst(rs.getLong("idprodest"));
			
			prods.add(prod);
		}
		
		conexao.close();

		return prods;
		
	}
	
	public void removerProdutoPromocao(long idpromocao) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "DELETE FROM produtos_promocoes WHERE idprodpromocao = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setLong(1, idpromocao);
		statement.execute();
		
		conexao.close();
		
	}
	
	public long addProdutoPromocao(Produto prodNormal, Produto prodProm) throws Exception {
		long idGerado = 0;
		Connection conexao = BDConfig.getConnection();

		String sql = "INSERT INTO produtos_promocoes(idprodestpromocao,idprodestnormal) VALUES(?, ?)";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setLong(1, prodProm.getCodprodEst());
		statement.setLong(2, prodNormal.getCodprodEst());
		statement.execute();
		
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idGerado = rs.getLong(1);
		}
		
		conexao.close();
		
		return idGerado;
	}
	
	public long buscarProdutoPromocao(long codProdEst) throws Exception {
		long codprom = 0;

		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT idprodpromocao FROM produtos_promocoes WHERE idprodestpromocao = ? or idprodestnormal = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setLong(1, codProdEst);
		statement.setLong(2, codProdEst);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			codprom = rs.getLong("idprodpromocao");
		}
		
		conexao.close();

		return codprom;
	}

	public ArrayList<Produto> buscaPromocoesProximas(String local) throws SQLException{
		
		ArrayList<Produto> prods = new ArrayList<Produto>();
		Connection conexao;
		try {
			conexao = BDConfig.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		String sql = "select * from produtosempromocao WHERE SUBSTR(local, 0, 7) = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, local.substring(0, 6));
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Produto prod = new Produto();
			
			prod.setIdproduto(rs.getLong("idproduto"));
			prod.setValor(rs.getDouble("valornormal"));
			prod.setDatamov(rs.getString("dtiniciopromocao"));
			prod.setCodbar(rs.getLong("codbar"));
			prod.setDescricao(rs.getString("descricao"));
			prod.setValorpromo(rs.getDouble("valorpromocao"));
			
			Estabelecimento est = new Estabelecimento();
			est.setCodigo(rs.getString("codigo"));
			est.setFantasia(rs.getString("fantasia"));
			est.setRazao_social(rs.getString("razao_social"));
			est.setLocal(rs.getString("local"));
			prod.setEstab(est);
			
			prods.add(prod);
			
		}
		
		conexao.close();

		return prods;
		
	}
	
	public ArrayList<Produto> buscaPromocoesPorEstabelecimento(String local) throws SQLException{
		
		ArrayList<Produto> prods = new ArrayList<Produto>();
		Connection conexao;
		try {
			conexao = BDConfig.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		String sql = "select * from produtosempromocao WHERE local = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, local);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Produto prod = new Produto();
			
			prod.setIdproduto(rs.getLong("idproduto"));
			prod.setValor(rs.getDouble("valornormal"));
			prod.setDatamov(rs.getString("dtiniciopromocao"));
			prod.setCodbar(rs.getLong("codbar"));
			prod.setDescricao(rs.getString("descricao"));
			prod.setValorpromo(rs.getDouble("valorpromocao"));
			
			Estabelecimento est = new Estabelecimento();
			est.setCodigo(rs.getString("codigo"));
			est.setFantasia(rs.getString("fantasia"));
			est.setRazao_social(rs.getString("razao_social"));
			est.setLocal(rs.getString("local"));
			prod.setEstab(est);
			
			prods.add(prod);
			
		}
		
		conexao.close();

		return prods;
		
	}
	
}