package de.lanthantv.PermissionsAPI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class PermissionsAPI extends Plugin implements Listener{

	public static PermissionsAPI instance;

	public static HashMap<UUID, Groups> cache = new HashMap<>();
	public static HashMap<Long, UUID> time = new HashMap<>();

	public void onEnable(){

		instance = this;

		BungeeCord.getInstance().getPluginManager().registerListener(this, this);
		BungeeCord.getInstance().getPluginManager().registerCommand(this, new groupset("groupset"));
		BungeeCord.getInstance().getPluginManager().registerCommand(this, new groupset("setgroup"));

		
		
		MySQL.connect();
		
		updateCache();

		MySQL.update("CREATE TABLE IF NOT EXISTS groups (id INT AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(255), name VARCHAR(255), gruppe VARCHAR(255))");
		MySQL.update("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(255), name VARCHAR(255), coins INT(255), tokens INT(255), team VARCHAR(255), premium VARCHAR(255), maintenance VARCHAR(255))");
	}

	public void onDisable(){



	}



	@EventHandler
	public static void onJoin(LoginEvent event){

		if(time.containsValue(event.getConnection().getUniqueId())){

			time.remove(event.getConnection().getUniqueId());
			cache.remove(cache.get(event.getConnection().getUniqueId()));
			getGroup(event.getConnection().getUniqueId());

		}

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


		if(cache.containsKey(uuid)){

			return cache.get(uuid);

		}else{

			try {

				PreparedStatement preparedStatement = MySQL.con.prepareStatement("SELECT gruppe FROM groups WHERE uuid='" + uuid.toString() + "'");
				final ResultSet set = preparedStatement.executeQuery();
				if (set.next()) {
					if(set.getString(1).equalsIgnoreCase("admin")){

						cache.put(uuid, Groups.ADMIN);
						time.put(System.nanoTime(), uuid);

						return Groups.ADMIN;

					}else if(set.getString(1).equalsIgnoreCase("developer")){

						cache.put(uuid, Groups.DEVELOPER);
						time.put(System.nanoTime(), uuid);
						return Groups.DEVELOPER;

					}else if(set.getString(1).equalsIgnoreCase("moderator")){
						cache.put(uuid, Groups.MODERATOR);
						time.put(System.nanoTime(), uuid);
						return Groups.MODERATOR;

					}else if(set.getString(1).equalsIgnoreCase("bt")){
						cache.put(uuid, Groups.BT);
						time.put(System.nanoTime(), uuid);
						return Groups.BT;

					}else if(set.getString(1).equalsIgnoreCase("youtuber")){
						cache.put(uuid, Groups.YouTuber);
						time.put(System.nanoTime(), uuid);
						return Groups.YouTuber;

					}else if(set.getString(1).equalsIgnoreCase("premium")){
						cache.put(uuid, Groups.PREMIUM);
						time.put(System.nanoTime(), uuid);
						return Groups.PREMIUM;

					}else if(set.getString(1).equalsIgnoreCase("spieler")){
						cache.put(uuid, Groups.USER);
						time.put(System.nanoTime(), uuid);
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


	public static void updateCache(){


		BungeeCord.getInstance().getScheduler().schedule(PermissionsAPI.instance, ()->{

			for(long time : time.keySet()){

				if((System.nanoTime() - time) > TimeUnit.NANOSECONDS.convert(3, TimeUnit.MINUTES)){

					UUID uuid = PermissionsAPI.time.get(time);

					cache.remove(cache.get(uuid));
					PermissionsAPI.time.remove(uuid);

				}

			}

		}, 60, TimeUnit.SECONDS);




	}


}
