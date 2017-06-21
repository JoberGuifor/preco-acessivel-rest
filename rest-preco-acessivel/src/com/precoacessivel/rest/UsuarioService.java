package com.precoacessivel.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.precoacessivel.dao.UsuarioDAO;
import com.precoacessivel.entidade.Usuario;

@Path("/usuarios")
public class UsuarioService {
	
	private UsuarioDAO usrDAO;
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	@PostConstruct
	private void init(){
		usrDAO = new UsuarioDAO();
	}
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON )
	public List<Usuario> listarUsuarios() {
		List<Usuario> lista = null;
		try {
			lista = usrDAO.listarUsuarios();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN )
	public String addUsuario(Usuario usr) {
		String msg = "";
		
		try {
			long idGerado = 0;
			if(usr.getLogin().equals("")){
				return "Login inválido!";
			}
			if(usr.getNome().equals("")){
				return "Nome inválido!";
			}
			if(usr.getSenha().isEmpty()){
				return "Informe uma senha!";
			}
			
			idGerado = usrDAO.addUsuario(usr);
			
			msg = String.valueOf(idGerado);
		} catch (Exception e) {
			msg = "Problema ao cadastrar o usuário!";
			e.printStackTrace();
		}
		return msg;
	}
	
	@GET
	@Path("/get/{idusuario}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Usuario buscaPorId(@PathParam("idusuario") long idUsuario) {
		Usuario usr = null;
		
		try {
			usr = usrDAO.buscarUsuarioPorId(idUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usr;
	}
	
	@PUT
	@Path("/edit/{id}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarNota(Usuario usr, @PathParam("id") int idUsuario) {
		String msg = "";
		
		try {
			usrDAO.editarUsuario(usr, idUsuario);
			
			msg = "Usuário alterado com sucesso!";
		} catch (Exception e) {
			msg = "Erro ao editar o usuário!";
			e.printStackTrace();
		}
		
		return msg;
	}

}
