package de.lanthantv.PermissionsAPI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class PermissionsAPI extends Plugin implements Listener{



	public void onEnable(){

		
		BungeeCord.getInstance().getPluginManager().registerListener(this, this);
		BungeeCord.getInstance().getPluginManager().registerCommand(this, new groupset("groupset"));
		BungeeCord.getInstance().getPluginManager().registerCommand(this, new groupset("setgroup"));

		
		
		MySQL.connect();
		
		

		MySQL.update("CREATE TABLE IF NOT EXISTS groups (id INT AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(255), name VARCHAR(255), gruppe VARCHAR(255))");
		MySQL.update("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(255), name VARCHAR(255), coins INT(255), tokens INT(255), team VARCHAR(255), premium VARCHAR(255), maintenance VARCHAR(255))");
	}

	public void onDisable(){



	}



	@EventHandler
	public static void onJoin(LoginEvent event){

		

		if(isRegistered(event.getConnection().getUniqueId())){

			if (getNamefromUUID(event.getConnection().getUniqueId()).equalsIgnoreCase(event.getConnection().getName())) {



			} else {

				MySQL.update("UPDATE users SET name='" + event.getConnection().getName() + "' WHERE uuid='" + event.getConnection().getUniqueId().toString() + "'");
				MySQL.update("UPDATE groups SET name='" + event.getConnection().getName() + "' WHERE uuid='" + event.getConnection().getUniqueId().toString() + "'");
			}


		}else{

			MySQL.update("INSERT INTO groups (uuid, name, gruppe) VALUES ('" + event.getConnection().getUniqueId().toString() + "', '" + event.getConnection().getName() + "', 'spieler')");

		}



	}

	public static boolean isRegistered(UUID uuid){
		boolean is = false;
		try{

			final ResultSet rs = MySQL.query("SELECT id FROM groups WHERE uuid='" + uuid.toString() + "' LIMIT 1");
			while(rs.next()){
				is = true;
			}
			rs.close();
		}catch(Exception e){
			System.err.println(e);
		}
		return is;
	}

	public static String getNamefromUUID(UUID uuid){

		try {
			final PreparedStatement ps = MySQL.con.prepareStatement("SELECT name FROM users WHERE uuid='" + uuid.toString() + "'");
			final ResultSet set = ps.executeQuery();
			if (set.next()) {
				return set.getString(1);
			}
		}
		catch (SQLException ex) {


		}
		return null;

	}

	public static String getUUIDfromName(String name){

		try {
			final PreparedStatement ps = MySQL.con.prepareStatement("SELECT uuid FROM users WHERE name='" + name + "'");
			final ResultSet set = ps.executeQuery();
			if (set.next()) {
				return set.getString(1);
			}
		}
		catch (SQLException ex) {


		}
		return null;

	}

	public static Groups getGroup(UUID uuid){


		try {

			PreparedStatement preparedStatement = MySQL.con.prepareStatement("SELECT gruppe FROM groups WHERE uuid='" + uuid.toString() + "'");
			final ResultSet set = preparedStatement.executeQuery();
			if (set.next()) {
				if(set.getString(1).equalsIgnoreCase("admin")){

					return Groups.ADMIN;

				}else if(set.getString(1).equalsIgnoreCase("developer")){

					return Groups.DEVELOPER;

				}else if(set.getString(1).equalsIgnoreCase("moderator")){

					return Groups.MODERATOR;

				}else if(set.getString(1).equalsIgnoreCase("bt")){

					return Groups.BT;

				}else if(set.getString(1).equalsIgnoreCase("youtuber")){

					return Groups.YouTuber;

				}else if(set.getString(1).equalsIgnoreCase("premium")){

					return Groups.PREMIUM;

				}else if(set.getString(1).equalsIgnoreCase("spieler")){

					return Groups.USER;

				}else {
					return Groups.ERROR;

				}
			}
		}
		catch (SQLException ex) {


		}
		return null;





	}


	


}
