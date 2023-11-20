package co.empresa.bbva.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.empresa.bbva.modelo.Bill;
import co.empresa.bbva.modelo.User;
import co.empresa.bbva.util.Conexion;

public class BillDao {
	
	private Conexion conexion;
	
	private static final String INSERT_BILL_SQL = "INSERT INTO bill(date_bill,user_id,value,type,observation) VALUES (?,?,?,?,?);";
	private static final String UPDATE_BILL_SQL = "DELETE FROM bill WHERE id = ?;";
	private static final String DELETE_BILL_SQL = "UPDATE bill SET date_bill = ? , user_id = ?,value= ? , type=?,observation=?; ";
	private static final String SELECT_BILL_BY_ID = "SELECT * FROM bill WHERE id= ? ;";
	private static final String SELECT_ALL_BILL = "SELECT * FROM bill;";
	
	public BillDao() {
		this.conexion = conexion.getConexion();
	}
	
	public void insert(Bill bill) {
		try {
			PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(INSERT_BILL_SQL);
			preparedStatement.setDate(1,bill.getDate_bill());
			preparedStatement.setInt(2,bill.getUser_id());
			preparedStatement.setInt(3,bill.getValue());
			preparedStatement.setInt(4,bill.getType());
			preparedStatement.setString(5,bill.getObservation());
			conexion.execute();
		}catch(SQLException e) {
			
		}
	}
	
	public void delete(int id) {
		try {
			PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(DELETE_BILL_SQL);
			preparedStatement.setInt(1,id);
			conexion.execute();
		}catch(SQLException e) {
			
		}
	}
	
	public void update(Bill bill) {
		try {
			PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(INSERT_BILL_SQL);
			preparedStatement.setDate(1,bill.getDate_bill());
			preparedStatement.setInt(2,bill.getUser_id());
			preparedStatement.setInt(3,bill.getValue());
			preparedStatement.setInt(4,bill.getType());
			preparedStatement.setString(5,bill.getObservation());;
			preparedStatement.setInt(6, bill.getId());
		}catch(SQLException e) {
			
		}
	}
	
	public List<Bill> selectAll(){
		List <Bill> bills = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(SELECT_ALL_BILL);
			
			ResultSet rs = conexion.query();
			while(rs.next()) {
				int id = rs.getInt("id");
				Date date_bill = rs.getDate("date_bill");
				Integer user_id = rs.getInt("user_id");
				Integer value  = rs.getInt("value");
				bills.add(new Bill(id,date_bill,user_id,value));
			}

		}catch(SQLException e) {
			
		}
		return bills;
		
	}
	
	public User select(int id){
		User usuario = null;
		List <User> usuarios = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(SELECT_BILL_BY_ID);
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

}
