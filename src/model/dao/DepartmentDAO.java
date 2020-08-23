package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDAO {

	void insert(Department dpt);
	void update(Department dpr);
	void deletebyId(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
}
