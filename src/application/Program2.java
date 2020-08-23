package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program2 {

	public static void main(String[] args) {

		DepartmentDAO departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("=== TEST 1: Department findByID ===");		
		Department department = departmentDao.findById(3);		
		System.out.println(department);
		
		System.out.println("\n=== TEST 2: Department findByAll ===");
		List<Department> deparments = departmentDao.findAll();
		
		for(Department obj : deparments) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST 3: Department insert ===");
		Department newDep = new Department(null, "New Dep Test");
		departmentDao.insert(newDep);
		System.out.println("Inserted! New ID = "+ newDep.getId());
		
		System.out.println("\n=== TEST 5: Department update ===");
		department = departmentDao.findById(2);
		department.setName("New Department Name");
		departmentDao.update(department);
		System.out.println("Updated!");
		
		System.out.println("\n=== TEST 6: Department delete ===");
		departmentDao.deleteById(newDep.getId());
		System.out.println("Deleted!");

	}

}
