package bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
	
	private Connection con;
	private Scanner sc;
	
	public User(Connection con, Scanner sc) {
		this.con = con;
		this.sc = sc;
	}
	
//	for registration of new users
	public void register() {
		sc.nextLine();
        System.out.print("Full Name: ");
        String full_name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
		
		if(user_exist(email)) {
			System.out.println("User already exist for this emal address!");
			return;
		}
		String query = "INSERT INTO user(full_name,email,password) VALUES(?,?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, full_name);
			ps.setString(2, email);
			ps.setString(3, password);
			
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected > 0) {
				System.out.println("Registration Successful!");
			}
			else {
				System.out.println("Registration Failed!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteAccount() {
		sc.nextLine();
        System.out.print("Full Name: ");
        String full_name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
		
		
		String query = "DELETE FROM user where email = ? AND password = ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected > 0) {
				System.out.println("Account Deleted!");
			}
			else {
				System.out.println("Deletion Failed!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
//	method to login the existing users
//	String type kaisiliye rkha hai taaki if user once login then his email can be shared by other functions so that it can generate account number
	public String login() {
		
		System.out.println("Email: ");
		String email = sc.next();
		System.out.println("Password: ");
		String password = sc.next();
		
		String login_query = "SELECT * FROM user WHERE email = ? AND password = ?";
		
		try{
			PreparedStatement ps = con.prepareStatement(login_query);
			ps.setString(1, email);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return email;
			}
			else {
				return null;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	to check weather user already exist or not
	public boolean user_exist(String email) {
		String query = "select * from user where email = ?";
		
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
