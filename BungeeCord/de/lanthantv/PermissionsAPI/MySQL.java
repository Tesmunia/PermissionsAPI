package de.lanthantv.PermissionsAPI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {



	public static String host = "localhost";
	public static String db = "minecraft";
	public static String user = "minecraft";
	public static String pass = "xM8ypx5HU5FRfeRy";
	public static int port = 3306;



	public static Connection con;

	public static void connect(){

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db + "?autoReconnect=true&cachePrepStmts=true&useServerPrepStmts=true", user, pass);
			System.out.println("MySQL> Verbindung hergestellt!");

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("MySQL> Konnte keine Verbindung aufbauen!");
		}

	}

	public static void close(){

		if(con != null){
			try {
				con.close();
				System.out.println("MySQL> Verbindung geschlossen!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void update(String qry){
		if(con != null){
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(qry);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{

			System.out.println("MySQL> Es besteht keine Verbindung!");

		}

	}




	public static ResultSet query(String qry){
		ResultSet rs = null;

		try {
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(qry);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	public static void reconnect(){

		close();
		connect();

	}


}