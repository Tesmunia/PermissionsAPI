package de.lanthantv.PermissionsAPI;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Fabio on 13.11.2015.
 */
public class updateRank extends Command implements TabExecutor{

    public updateRank(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {

        if(commandSender instanceof ProxiedPlayer){

            ProxiedPlayer player = (ProxiedPlayer) commandSender;


            if(PermissionsAPI.getGroup(player.getUniqueId()) == Groups.ADMIN || PermissionsAPI.getGroup(player.getUniqueId()) == Groups.DEVELOPER){


                if(args.length == 2){


                    String group = args[1];
                    String p = args[0];
                    boolean team = false;
                    boolean premium = false;

                    String uuid = PermissionsAPI.getUUIDfromName(p);

                    if(uuid == null){

                        if(PermissionsAPI.getLanguage(player.getUniqueId()).equalsIgnoreCase("de")) {

                            player.sendMessage(TextComponent.fromLegacyText("§cUns ist kein Spieler mit dem Namen §e" + p + " §cbekannt!"));

                        }else{

                            player.sendMessage(TextComponent.fromLegacyText("§cWe don't know a player named §e" + p + "§c!"));

                        }

                    }else {

                        if (group.equalsIgnoreCase("Admin")) {

                            group = "admin";
                            team = true;

                        } else if (group.equalsIgnoreCase("Developer")) {

                            group = "developer";
                            team = true;

                        } else if (group.equalsIgnoreCase("Moderator")) {

                            group = "moderator";
                            team = true;

                        } else if (group.equalsIgnoreCase("Supporter")) {

                            group = "supporter";
                            team = true;

                        } else if (group.equalsIgnoreCase("Builder")) {

                            group = "builder";
                            team = true;

                        } else if (group.equalsIgnoreCase("YouTuber")) {

                            group = "youtuber";
                            premium = true;

                        } else if (group.equalsIgnoreCase("Tessie")) {

                            group = "tessie";
                            premium = true;

                        } else if (group.equalsIgnoreCase("Premiumplus")) {

                            group = "premiumplus";
                            premium = true;

                        } else if (group.equalsIgnoreCase("Premium")) {

                            group = "premium";
                            premium = true;

                        } else if (group.equalsIgnoreCase("user")) {

                            group = "user";

                        } else {

                            group = null;

                        }


                        if(group != null){

                            MySQL.update("UPDATE groups SET rank='" + group + "' WHERE uuid='" + uuid.toString() + "'");

                            if(team == true){

                                MySQL.update("UPDATE users SET team='true' WHERE uuid='" + uuid.toString() + "'");


                            }else{

                                MySQL.update("UPDATE users SET team='false' WHERE uuid='" + uuid.toString() + "'");

                            }

                            if(premium == true){

                                MySQL.update("UPDATE users SET premium='true' WHERE uuid='" + uuid.toString() + "'");

                            }else{

                                MySQL.update("UPDATE users SET premium='false' WHERE uuid='" + uuid.toString() + "'");

                            }

                            if(PermissionsAPI.getLanguage(player.getUniqueId()).equalsIgnoreCase("de")) {

                                player.sendMessage(TextComponent.fromLegacyText("§aDer Rang von §e" + p + " §awurde erfolgreich aktualisiert."));

                            }else{

                                player.sendMessage(TextComponent.fromLegacyText("§aThe Rank of §e" + p + " §awas successfully updated."));


                            }

                        }

                    }


                }else{

                    if(PermissionsAPI.getLanguage(player.getUniqueId()).equalsIgnoreCase("de")){

                        player.sendMessage(TextComponent.fromLegacyText("§cBenutze: §e/updaterank <Spieler> <Gruppe>"));

                    }else{

                        player.sendMessage(TextComponent.fromLegacyText("§cUse: §e/updaterank <Player> <Group>"));

                    }

                }


            }else{


                if(PermissionsAPI.getLanguage(player.getUniqueId()).equalsIgnoreCase("de")){

                    player.sendMessage(TextComponent.fromLegacyText("§cUnbekannter Befehl!"));

                }else{

                    player.sendMessage(TextComponent.fromLegacyText("§cUnknown Command!"));

                }


            }



        }

    }

    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] args) {
        Set<String> matches = new HashSet<>();

        if(commandSender instanceof ProxiedPlayer) {

            ProxiedPlayer player = (ProxiedPlayer) commandSender;

            if(PermissionsAPI.getGroup(player.getUniqueId()).equals(Groups.ADMIN) || PermissionsAPI.getGroup(player.getUniqueId()).equals(Groups.DEVELOPER)) {

                if (args.length == 1) {


                    String search = args[0].toLowerCase();

                    for (ProxiedPlayer players : BungeeCord.getInstance().getPlayers()) {

                        if (players.getName().toLowerCase().startsWith(search)) {

                            matches.add(players.getName());

                        }

                    }


                } else if (args.length == 2) {

                    String search = args[1].toLowerCase();

                    if ("admin".startsWith(search)) {

                        matches.add("Admin");

                    }
                    if ("developer".startsWith(search)) {

                        matches.add("Developer");

                    }
                    if ("moderator".startsWith(search)) {

                        matches.add("Moderator");

                    }
                    if ("supporter".startsWith(search)) {

                        matches.add("Supporter");

                    }
                    if ("builder".startsWith(search)) {

                        matches.add("Builder");

                    }
                    if ("youtuber".startsWith(search)) {

                        matches.add("YouTuber");

                    }
                    if ("tessie".startsWith(search)) {

                        matches.add("Tessie");

                    }
                    if ("premiumplus".startsWith(search)) {

                        matches.add("Premiumplus");

                    }
                    if ("user".startsWith(search)) {

                        matches.add("User");

                    }

                } else {


                    if (PermissionsAPI.getLanguage(player.getUniqueId()).equalsIgnoreCase("de")) {

                        player.sendMessage(TextComponent.fromLegacyText("§cBenutze: §e/updaterank <Spieler> <Gruppe>"));

                    } else {

                        player.sendMessage(TextComponent.fromLegacyText("§cUse: §e/updaterank <Player> <Group>"));

                    }


                }
                return matches;
            }

            return matches;

        }
        return matches;
    }

}
