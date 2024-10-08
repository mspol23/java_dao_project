package main;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Main {

	public static void main(String[] args) {
		
		DepartmentDao departDao = DaoFactory.createDepartmenteDao();
		Department depart = new Department();
		
		System.out.println("\n==== Teste 1 - findById() =====");
		depart = departDao.findById(7);
		System.out.println("Department found: " + depart);
		
		System.out.println("\n==== Teste 2 - insert() =====");
//		Department depart2 = new Department(null, "Law");
//		departDao.insert(depart2);
		
		System.out.println("\n==== Teste 3 - update() =====");
		Department departTest3 = departDao.findById(7);
		departTest3.setName("Cinema");
		departDao.update(departTest3);
		
		System.out.println("\n==== Teste 4 - findAll() =====");
		List<Department> list = departDao.findAll();
		for(Department d : list) {
			System.out.println(d);
		}
		
		System.out.println("\n==== Teste 5 - delete() =====");
		departDao.delete(9);
	}
}

// Testes feitos com a classe Seller:

// 		Scanner sc = new Scanner(System.in);
//
//		SellerDao sellerDao = DaoFactory.createSellerDao();
//				
//		System.out.println("=== teste 1 - SellerDaoJDBC.findById() ===");
//		Seller seller1 = sellerDao.findById(3);
//		System.out.println(seller1);
//		
//		System.out.println("\n=== teste 2 - SellerDao - findByDepartment() ===");
//		Department depart = new Department(2, null);
////		SellerDao sellerDao2 = DaoFactory.createSellerDao();
//		List<Seller> list = sellerDao.findByDepartment(depart);
//		for(Seller s : list) {
//			System.out.println(s);
//		}
//		
//		System.out.println("\n=== teste 3 - SellerDao - findAll() ===");
//		list = sellerDao.findAll();
//		for(Seller s : list) {
//			System.out.println(s);
//		}
//		
//		System.out.println("\n=== teste 4 - SellerDao - insert() ===");
//		Seller seller2 = new Seller(null, "Ted Dowson", "ted@gmail.com", setFormattedDate("01/05/1990"), 3500.0, depart);
//		sellerDao.insert(seller2);
//		System.out.println("New seller added. Id = " + seller2.getId());
//		
//		System.out.println("\n=== teste 5 - SellerDao - update() ===");
//		Seller seller3 = sellerDao.findById(7);
//		seller3.setName("Jane Fonda");
//		seller3.setEmail("jane@gmail.com");
//		sellerDao.update(seller3);
//		System.out.println("Seller updated: " + seller3.toString());
//		
//		System.out.println("\n=== teste 6 - SellerDao - delete() ===");
//		char confirm = 'x';
//		while (confirm != 'y' && confirm != 'n') {
//			System.out.print("Enter seller id to delete: ");
//			int sellerId = sc.nextInt();
//			sc.nextLine();
//			System.out.println(sellerDao.findById(sellerId));
//			System.out.print("Confirm seller to delete (y/n)? ");
//			confirm = sc.nextLine().charAt(0);
//			if(confirm == 'y') {
//				sellerDao.delete(sellerId);
//				System.out.println("Seller deleted!");
//			}
//			if(confirm == 'n') {
//				System.out.print("Try another id (y/n)? ");
//				confirm = sc.nextLine().charAt(0);
//				if(confirm == 'y') {
//					confirm = 'x';					
//				}
//			}
//		}
//		System.out.println("Process finished.");
//		
//		
//		sc.close();
//	}
//	
//	private static Date setFormattedDate(String date){
//		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		return Date.valueOf(LocalDate.parse(date, fmt));
//	}
