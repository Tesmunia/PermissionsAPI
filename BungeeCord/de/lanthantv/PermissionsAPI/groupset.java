package de.lanthantv.PermissionsAPI;

import java.util.UUID;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class groupset extends Command{



	public groupset(String name) {
		super(name);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {

		if(commandSender instanceof ProxiedPlayer){

			ProxiedPlayer player = (ProxiedPlayer) commandSender;
			if(PermissionsAPI.getGroup(player.getUniqueId()) == Groups.ADMIN || PermissionsAPI.getGroup(player.getUniqueId()) == Groups.DEVELOPER){

				if(args.length == 2){

					String group = args[1];
					String p = args[0];

					UUID uuid = UUID.fromString(PermissionsAPI.getUUIDfromName(p));

					if(uuid == null){

						commandSender.sendMessage("§cDieser Spieler war noch nie online!");

					}else{

						if(group.equalsIgnoreCase("Admin")){

							group = "admin";

						}else if(group.equalsIgnoreCase("Developer")){

							group = "developer";

						}else if(group.equalsIgnoreCase("Moderator")){

							group = "moderator";

						}else if(group.equalsIgnoreCase("BT")){

							group = "bt";

						}else if(group.equalsIgnoreCase("YouTuber")){

							group = "youtuber";

						}else if(group.equalsIgnoreCase("Premium")){

							group = "premium";

						}else if(group.equalsIgnoreCase("Spieler")){

							group = "spieler";

						}else{

							group = null;

						}

						if(group != null){
							MySQL.update("UPDATE groups SET gruppe='" + group + "' WHERE uuid='" + uuid.toString() + "'");
							commandSender.sendMessage("§aDie Gruppe von §e" + p + " §awurde erfolgreich aktualisiert.");
							if(PermissionsAPI.time.containsValue(uuid)){

								PermissionsAPI.time.remove(uuid);
								PermissionsAPI.cache.remove(PermissionsAPI.cache.get(uuid));
								PermissionsAPI.getGroup(uuid);

							}
						}else{



						}

					}

				}else{

					commandSender.sendMessage("§cBenutze: §e/groupset <Spieler> <Admin|Developer|Moderator|BT|YouTuber|Premium|Spieler>");

				}
			}else{

				player.sendMessage("§cUnbekannter Befehl!");

			}


		}else {

			if(args.length == 2){
				String group = args[1];
				String p = args[0];

				UUID uuid = UUID.fromString(PermissionsAPI.getUUIDfromName(p));

				if(uuid == null){

					commandSender.sendMessage("§cDieser Spieler war noch nie online!");

				}else{

					if(group.equalsIgnoreCase("Admin")){

						group = "admin";

					}else if(group.equalsIgnoreCase("Developer")){

						group = "developer";

					}else if(group.equalsIgnoreCase("Moderator")){

						group = "moderator";

					}else if(group.equalsIgnoreCase("BT")){

						group = "bt";

					}else if(group.equalsIgnoreCase("YouTuber")){

						group = "youtuber";

					}else if(group.equalsIgnoreCase("Premium")){

						group = "premium";

					}else if(group.equalsIgnoreCase("Spieler")){

						group = "spieler";

					}else{

						group = null;

					}

					if(group != null){
						MySQL.update("UPDATE groups SET gruppe='" + group + "' WHERE uuid='" + uuid.toString() + "'");
						commandSender.sendMessage("§aDie Gruppe von §e" + p + " §awurde erfolgreich aktualisiert.");
					}else{



					}

				}

			}else{

				commandSender.sendMessage("§cBenutze: §e/groupset <Spieler> <Admin|Developer|Moderator|BT|YouTuber|Premium|Spieler>");

			}

		}


	}

}
