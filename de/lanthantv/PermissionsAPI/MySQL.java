package de.lanthantv.PermissionsAPI;

import java.sql.*;

/**
 * Created by Fabio on 11.11.2015.
 */
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
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db + "?autoReconnect=true", user, pass);


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("MySQL> Konnte keine Verbindung aufbauen!");
        }

    }

    public static void close(){

        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void update(String qry){
        reconnect();
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
        reconnect();

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
