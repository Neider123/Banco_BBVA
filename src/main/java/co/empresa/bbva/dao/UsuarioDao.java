package co.empresa.bbva.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.empresa.bbva.modelo.User;
import co.empresa.bbva.util.Conexion;




public class UsuarioDao  {
			
	private Conexion conexion;
	
	private static final String INSERT_USUARIO_SQL = "INSERT INTO user(username,pass,email) VALUES (?,?,?);";
	private static final String UPDATE_USUARIO_SQL = "DELETE FROM user WHERE id = ?;";
	private static final String DELETE_USUARIO_SQL = "UPDATE user SET username = ? , pass= ?,email=? ; ";
	private static final String SELECT_USUARIO_BY_ID = "SELECT * FROM user WHERE id= ? ;";
	private static final String SELECT_ALL_USUARIOS = "SELECT * FROM user;";
	private static final String VERIFY_LOGIN  = "SELECT * FROM user WHERE username=? AND pass=?";
	
	public UsuarioDao() {
		this.conexion = conexion.getConexion();
	}
	
	public void insert(User usuario) {
		try {
			PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(INSERT_USUARIO_SQL);
			preparedStatement.setString(1, usuario.getUsername());
			preparedStatement.setString(2, usuario.getPass());
			preparedStatement.setString(3, usuario.getEmail());
			conexion.execute();
		}catch(SQLException e) {
			
		}
	}
	
	public void delete(int id) {
		try {
			PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(DELETE_USUARIO_SQL);
			preparedStatement.setInt(1,id);
			conexion.execute();
		}catch(SQLException e) {
			
		}
	}
	
	public void update(User usuario) {
		try {
			PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(INSERT_USUARIO_SQL);
			preparedStatement.setString(1, usuario.getUsername());
			preparedStatement.setString(2, usuario.getPass());
			preparedStatement.setString(3, usuario.getEmail());
			preparedStatement.setInt(4, usuario.getId());
		}catch(SQLException e) {
			
		}
	}
	
	public List<User> selectAll(){
		List <User> usuarios = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(SELECT_ALL_USUARIOS);
			
			ResultSet rs = conexion.query();
			while(rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String pass = rs.getString("pass");
				String email = rs.getString("email");
				usuarios.add(new User(username,pass,email));
			}

		}catch(SQLException e) {
			
		}
		return usuarios;
		
	}
	
	
	
	public User select(int id){
		User usuario = null;
		List <User> usuarios = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(SELECT_USUARIO_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet rs = conexion.query();
			while(rs.next()) {
				String username = rs.getString("username");
				String pass = rs.getString("pass");
				String email = rs.getString("email");
				usuario = (new User(id,username,pass,email));
			}

		}catch(SQLException e) {
			
		}
		return usuario;
		
	}
	
	public boolean validarLogin(String username, String password) {
	    try {
	        PreparedStatement preparedStatement = conexion.setPreparedStatement(VERIFY_LOGIN);
	        preparedStatement.setString(1, username);
	        preparedStatement.setString(2, password);
	        ResultSet rs = conexion.query();

	        // Si hay un resultado en el conjunto de resultados, significa que las credenciales son válidas
	        return rs.next();

	    } catch (SQLException e) {
	        // Manejo de la excepción
	        e.printStackTrace();
	        return false;
	    }
	}

	
	
	
}










