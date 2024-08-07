package org.example.cuoiki_code_tutorial.Utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnection {
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/codelearn", "root", "thanhbinh1411");
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return con;
	}
	public static void main(String[] args) throws SQLException {
		Connection conn = MySQLConnection.getConnection();
		if(conn != null) {
			System.out.println("Connect to MySQL successfully!");
			conn.close();
		}else
			System.out.println("Can not connect to MySQL!");


	}
	
}
