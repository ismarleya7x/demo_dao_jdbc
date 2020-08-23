package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDAO {
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletebyId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {

		PreparedStatement stm = null;
		ResultSet res = null;
		
		try {
			String sql = "SELECT seller.*, department.Name as DepName "+
						 "FROM seller JOIN department ON seller.DepartmentId = department.Id "+
						 "WHERE seller.Id = ?";
			
			stm = conn.prepareStatement(sql);
			
			stm.setInt(1, id);
			res = stm.executeQuery();
			if(res.next()) {
				Department tmpDep = instantiateDepartment(res);
				
				Seller tmpSeller = instantiateSeller(res, tmpDep);
				
				return tmpSeller;
			}
			
			return null;
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		}finally {
			DB.closeStatement(stm);
			DB.closeResultSet(res);
		}
		
	}

	private Seller instantiateSeller(ResultSet res, Department tmpDep) throws SQLException {
		Seller tmpSeller = new Seller();
		tmpSeller.setId(res.getInt("Id"));
		tmpSeller.setNome(res.getString("Name"));
		tmpSeller.setEmail(res.getString("Email"));
		tmpSeller.setBaseSalary(res.getDouble("BaseSalary"));
		tmpSeller.setBirthDate(res.getDate("BirthDate"));
		tmpSeller.setDepartment(tmpDep);
		
		return tmpSeller;
	}

	private Department instantiateDepartment(ResultSet res) throws SQLException {
		Department tmpDep = new Department();
		tmpDep.setId(res.getInt("DepartmentId"));
		tmpDep.setName(res.getString("DepName"));
		
		return tmpDep;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
