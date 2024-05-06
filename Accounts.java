package bank;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Accounts 
{
	
	private Connection con;
	private Scanner sc;
	
	public Accounts(Connection con, Scanner sc) 
	{
		this.con = con;
		this.sc = sc;
	}
	
	public void deleteAccount() {
		sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Scurity Pin: ");
        String security_pin = sc.nextLine();
		
		
		String query = "DELETE FROM accounts where email = ? AND security_pin = ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, security_pin);
			
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
	
	public long open_Account(String email) 
	{
		if(!account_exist(email))
		{
			String open_account_query = "INSERT INTO Accounts(account_number, full_name, email, balance, security_pin) VALUES(?, ?, ?, ?, ?)";
            sc.nextLine();
            System.out.print("Enter Full Name: ");
            String full_name = sc.nextLine();
            System.out.print("Enter Initial Amount: ");
            double balance = sc.nextDouble();
            sc.nextLine();
            System.out.print("Enter Security Pin: ");
            String security_pin = sc.nextLine();
			
			try
			{
				long account_number = generateAccountNumber();
				PreparedStatement ps = con.prepareStatement(open_account_query);
				ps.setDouble(1, account_number);
				ps.setString(2, full_name);
				ps.setString(3, email);
				ps.setDouble(4, balance);
				ps.setString(5, security_pin);
				
				int rowsAffected = ps.executeUpdate();
				if(rowsAffected > 0) 
				{
					return account_number;
				}
				else
				{
					throw new RuntimeException("Account creation failed!");
					
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		throw new RuntimeException("Account Already Exist");
	}
	
	public long getAccount_number(String email)
	{
		String query = "select account_number from accounts where email = ?";
		
		try 
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				return rs.getLong("account_number");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		throw new RuntimeException("Account number doesn't exist");
	}
	
	public long generateAccountNumber() 
	{
//		limit 1 krne se jo recent account number hoga vo miljaaega
		String query = "select account_number from accounts ORDER BY account_number DESC LIMIT 1";
		
		try 
		{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) 
			{
				long last_account_number = rs.getLong("account_number");
				return last_account_number + 1;
			}
			else 
			{
				return 10000100;
			}
			
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		return 10000100;
	}
	
	
	public boolean account_exist(String email) 
	{
		String query = "select account_number from accounts where email = ?";
		
		try
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) 
			{
				return true;
			}
			else 
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

}
