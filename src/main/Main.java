package main;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Main {

	public static void main(String[] args) {
		
		Department obj = new Department(1, "Books");
		Seller seller = new Seller(21, "John Doe", "john@gmail.com", new Date(), 1000.0, obj);
		System.out.println(seller);
	}

}
