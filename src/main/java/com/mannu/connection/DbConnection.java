package com.mannu.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
	public static Connection con90,con78;
	
	public static Connection conn98() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con90=DriverManager.getConnection("jdbc:sqlserver://192.168.84.90;user=sa;password=Karvy@123;database=management");
		} catch (Exception e) {
			return con90;
		}
		return con90;
	}
	
	public static Connection conn78() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con78=DriverManager.getConnection("jdbc:sqlserver://192.168.84.78;user=sa;password=Karvy@123;database=TINTAX");
		} catch (Exception e) {
			return con78;
		}
		return con78;
	}
	
}
