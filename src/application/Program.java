package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDAO sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("=== TEST 1: Seller findByID ===");		
		Seller seller = sellerDao.findById(3);		
		System.out.println(seller);
		
		System.out.println("\n=== TEST 2: Seller findByDepartment ===");
		Department department = new Department(2, null);
		List<Seller> listSeller = sellerDao.findByDepartment(department);
		for(Seller obj : listSeller) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST 3: Seller findByAll ===");
		listSeller = sellerDao.findAll();
		
		for(Seller obj : listSeller) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST 4: Seller insert ===");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New ID = "+ newSeller.getId());
		
		System.out.println("\n=== TEST 4: Seller update ===");
		seller = sellerDao.findById(2);
		seller.setNome("Martha Wayne");
		sellerDao.update(seller);
		System.out.println("Updated!");
		
		System.out.println("\n=== TEST 6: Seller delete ===");
		sellerDao.deleteById(9);
		sellerDao.deleteById(10);
		sellerDao.deleteById(11);
		sellerDao.deleteById(12);
		sellerDao.deleteById(13);
		System.out.println("Deleted!");

	}

}
