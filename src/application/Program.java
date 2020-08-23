package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Department dpt = new Department(1, "Books");
		Seller seller = new Seller(21, "Ismarley", "exemplo@gmail.com", new Date(), 3000.0, dpt);
		
		SellerDAO sellerDao = DaoFactory.createSellerDao();
		
		System.out.println(seller);

	}

}
