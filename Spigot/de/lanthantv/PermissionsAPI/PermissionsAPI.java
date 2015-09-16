package de.lanthantv.PermissionsAPI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

public class PermissionsAPI extends JavaPlugin{


	public static PermissionsAPI instance;


	public void onEnable(){
		instance = this;
		MySQL.connect();

	}

	public void onDisable(){



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
