package main;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Main {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();
				
		System.out.println("=== teste 1 - SellerDaoJDBC.findById() ===");
		Seller seller1 = sellerDao.findById(3);
		System.out.println(seller1);
		
		System.out.println("\n=== teste 2 - SellerDao - findByDepartment() ===");
		Department depart = new Department(2, null);
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
		
		System.out.println("\n=== teste 4 - SellerDao - insert() ===");
		Seller seller2 = new Seller(null, "Ted Dowson", "ted@gmail.com", setFormattedDate("01/05/1990"), 3500.0, depart);
		sellerDao.insert(seller2);
		System.out.println("New seller added. Id = " + seller2.getId());
		
		System.out.println("\n=== teste 5 - SellerDao - update() ===");
		Seller seller3 = sellerDao.findById(7);
		seller3.setName("Jane Fonda");
		seller3.setEmail("jane@gmail.com");
		sellerDao.update(seller3);
		System.out.println("Seller updated: " + seller3.toString());
		
	}
	
	private static Date setFormattedDate(String date){
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return Date.valueOf(LocalDate.parse(date, fmt));
	}

}
