package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDAO {
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department dpt) {
		PreparedStatement stm = null;
		String sql = "INSERT INTO department (Name) VALUES (?)";
		
		try {
			stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stm.setString(1,dpt.getName());
						
			int rowsAffected = stm.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet res = stm.getGeneratedKeys();
				if(res.next()) {
					int id = res.getInt(1);
					dpt.setId(id);
				}
				DB.closeResultSet(res);
			}
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		}finally {
			DB.closeStatement(stm);
		}
		
	}

	@Override
	public void update(Department dpt) {
		PreparedStatement stm = null;
		String sql = "UPDATE department SET Name = ? WHERE Id = ?";
		
		try {
			stm = conn.prepareStatement(sql);
			
			stm.setString(1,dpt.getName());
			stm.setInt(2,dpt.getId());
						
			stm.executeUpdate();
			
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		}finally {
			DB.closeStatement(stm);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement stm = null;
		String sql = "DELETE FROM department WHERE Id = ?";
		
		try {
			stm = conn.prepareStatement(sql);
			
			stm.setInt(1, id);
						
			stm.executeUpdate();
			
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		}finally {
			DB.closeStatement(stm);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement stm = null;
		ResultSet res = null;
		String sql = "SELECT * FROM department WHERE Id = ?";
		
		try {
			stm = conn.prepareStatement(sql);
			
			stm.setInt(1, id);
						
			res = stm.executeQuery();
			
			if(res.next()) {
				Department dep = new Department(res.getInt("Id"), res.getString("Name"));
				return dep;
			}			
			return null;
			
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		}finally {
			DB.closeStatement(stm);
			DB.closeResultSet(res);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement stm = null;
		ResultSet res = null;
		String sql = "SELECT * FROM department";
		
		try {
			stm = conn.prepareStatement(sql);
						
			res = stm.executeQuery();
			List<Department> dep = new ArrayList<>(); 
			while(res.next()) {
				dep.add(new Department(res.getInt("Id"), res.getString("Name")));
			}

			return dep;
			
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		}finally {
			DB.closeStatement(stm);
			DB.closeResultSet(res);
		}
	}

}
