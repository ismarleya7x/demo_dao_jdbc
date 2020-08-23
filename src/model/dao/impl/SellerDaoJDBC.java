package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		PreparedStatement stm = null;

		String sql = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?,?,?,?,?)";

		try {
			stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stm.setString(1, seller.getNome());
			stm.setString(2, seller.getEmail());
			stm.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			stm.setDouble(4, seller.getBaseSalary());
			stm.setInt(5, seller.getDepartment().getId());

			int rowsAffected = stm.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet res = stm.getGeneratedKeys();

				if (res.next()) {
					int id = res.getInt(1);
					seller.setId(id);
				}
				DB.closeResultSet(res);
			} else {
				throw new DBException("Unexpected error! No rows affected");
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(stm);
		}

	}

	@Override
	public void update(Seller seller) {
		PreparedStatement stm = null;

		String sql = "UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?"
					 + " WHERE Id = ?";

		try {
			stm = conn.prepareStatement(sql);

			stm.setString(1, seller.getNome());
			stm.setString(2, seller.getEmail());
			stm.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			stm.setDouble(4, seller.getBaseSalary());
			stm.setInt(5, seller.getDepartment().getId());
			stm.setInt(6, seller.getId());
			
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(stm);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement stm = null;

		String sql = "DELETE FROM seller WHERE Id = ?";
		try {
			stm = conn.prepareStatement(sql);
			
			stm.setInt(1, id);
			
			stm.executeUpdate();
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(stm);
		}

	}

	@Override
	public Seller findById(Integer id) {

		PreparedStatement stm = null;
		ResultSet res = null;

		try {
			String sql = "SELECT seller.*, department.Name as DepName "
					+ "FROM seller JOIN department ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?";

			stm = conn.prepareStatement(sql);

			stm.setInt(1, id);
			res = stm.executeQuery();
			if (res.next()) {
				Department tmpDep = instantiateDepartment(res);

				Seller tmpSeller = instantiateSeller(res, tmpDep);

				return tmpSeller;
			}

			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
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
		PreparedStatement stm = null;
		ResultSet res = null;

		try {
			String sql = "SELECT seller.*, department.Name as DepName "
					+ "FROM seller JOIN department ON seller.DepartmentId = department.Id ";

			stm = conn.prepareStatement(sql);

			res = stm.executeQuery();
			List<Seller> listTmpSeller = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (res.next()) {
				Department dep = map.get(res.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(res);
					map.put(res.getInt("DepartmentId"), dep);
				}

				listTmpSeller.add(instantiateSeller(res, dep));

			}

			return listTmpSeller;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(stm);
			DB.closeResultSet(res);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement stm = null;
		ResultSet res = null;

		try {
			String sql = "SELECT seller.*, department.Name as DepName "
					+ "FROM seller JOIN department ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ?";

			stm = conn.prepareStatement(sql);

			stm.setInt(1, department.getId());
			res = stm.executeQuery();
			List<Seller> listTmpSeller = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (res.next()) {
				Department dep = map.get(res.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(res);
					map.put(res.getInt("DepartmentId"), dep);
				}

				listTmpSeller.add(instantiateSeller(res, dep));

			}

			return listTmpSeller;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(stm);
			DB.closeResultSet(res);
		}
	}

}
