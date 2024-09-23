package main;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Main {

	public static void main(String[] args) {

		System.out.println("=== teste 1 - SellerDaoJDBC.findById() ===");
		SellerDao sellerDao = DaoFactory.createSellerDao();
		Seller seller1 = sellerDao.findById(3);
		System.out.println(seller1);
		
		System.out.println("\n=== teste 2 - SellerDao - findByDepartment() ===");
		Department depart = new Department(1, null);
//		SellerDao sellerDao2 = DaoFactory.createSellerDao();
		List<Seller> list = sellerDao.findByDepartment(depart);
		for(Seller s : list) {
			System.out.println(s);
		}
		
		System.out.println("\n=== teste 3 - SellerDao - findAll() ===");
		list = sellerDao.findAll();
		for(Seller s : list) {
			System.out.println(s);
		}
		
		
		
	}

}
