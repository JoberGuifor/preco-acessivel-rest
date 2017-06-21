package com.precoacessivel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.precoacessivel.entidade.Usuario;
import com.precoacessivel.config.BDConfig;

public class UsuarioDAO {
	
	public List<Usuario> listarUsuarios() throws Exception{
		List<Usuario> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM usuarios";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Usuario usr = new Usuario();
			usr.setIdusuario(rs.getInt("idusuario"));
			usr.setLogin(rs.getString("login"));
			usr.setSenha(rs.getString("senha"));
			usr.setNome(rs.getString("nome"));

			lista.add(usr);
		}
		
		conexao.close();

		return lista;
	}
	
	public Usuario buscarUsuarioPorId(long idUsuario) throws Exception {
		Usuario usr = null;

		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM usuarios WHERE idusuario = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setLong(1, idUsuario);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			usr = new Usuario();
			usr.setIdusuario(rs.getInt("idusuario"));
			usr.setLogin(rs.getString("login"));
			usr.setSenha(rs.getString("senha"));
			usr.setNome(rs.getString("nome"));
		}
		
		conexao.close();

		return usr;
	}
	
	public long addUsuario(Usuario usr) throws Exception {
		long idGerado = 0;
		Connection conexao = BDConfig.getConnection();

		String sql = "INSERT INTO usuarios(login, senha, nome) VALUES(?, ?, ?)";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, usr.getLogin());
		statement.setString(2, usr.getSenha());
		statement.setString(3, usr.getNome());
		statement.execute();
		
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idGerado = rs.getLong(1);
		}
		
		conexao.close();
		
		return idGerado;
	}
	
	public void editarUsuario(Usuario usr, long idUsuario) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "UPDATE usuarios SET login = ?, senha = ?, nome = ? WHERE idusuario = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, usr.getLogin());
		statement.setString(2, usr.getSenha());
		statement.setString(3, usr.getNome());
		statement.setLong(4, idUsuario);
		statement.execute();
		conexao.close();
	}
	
	public void removerUsuario(long idUsuario) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "DELETE FROM usuarios WHERE idusuario = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setLong(1, idUsuario);
		statement.execute();
		
		conexao.close();
		
	}
	
}
