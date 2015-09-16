package de.lanthantv.PermissionsAPI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PermissionsAPI extends JavaPlugin implements Listener{

	public static HashMap<UUID, Groups> cache = new HashMap<>();
	public static HashMap<Long, UUID> time = new HashMap<>();


	public static PermissionsAPI instance;


	public void onEnable(){

		instance = this;
		
		Bukkit.getPluginManager().registerEvents(this, this);

		MySQL.connect();
		
		updateCache();




	}

	public void onDisable(){



	}
	
	@EventHandler
	public static void onJoin(PlayerJoinEvent event){
		
		if(time.containsValue(event.getPlayer().getUniqueId())){
			
			time.remove(event.getPlayer().getUniqueId());
			cache.remove(cache.get(event.getPlayer().getUniqueId()));
			getGroup(event.getPlayer().getUniqueId());
			
		}
		
		
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
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PermissionsAPI.instance, ()->{
			
			for(long time : time.keySet()){
				
				if((System.nanoTime() - time) > TimeUnit.NANOSECONDS.convert(3, TimeUnit.MINUTES)){
					
					UUID uuid = PermissionsAPI.time.get(time);
					
					cache.remove(cache.get(uuid));
					PermissionsAPI.time.remove(uuid);
					
				}
				
			}
			
		}, 60*20, 0L);
		
		
	}


}
