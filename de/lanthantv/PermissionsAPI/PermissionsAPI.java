package de.lanthantv.PermissionsAPI;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by Fabio on 13.11.2015.
 */
public class PermissionsAPI extends Plugin{

    public void onEnable(){


        BungeeCord.getInstance().getPluginManager().registerListener(this, new events_global());

        BungeeCord.getInstance().getPluginManager().registerCommand(this, new updateRank("updaterank"));


        MySQL.connect();

        MySQL.update("CREATE TABLE IF NOT EXISTS groups (id INT AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(255), name VARCHAR(255), rank VARCHAR(255))");



    }

    public void onDisable(){



    }

    public static Groups getGroup(UUID uuid){

        try{

            final ResultSet rs =  MySQL.query("SELECT rank FROM groups WHERE uuid='" + uuid.toString() + "' LIMIT 1");
            if(rs.next()){
                String group = rs.getString(1);

                if(group.equalsIgnoreCase("admin")){

                    return Groups.ADMIN;

                }else if(group.equalsIgnoreCase("developer")){

                    return Groups.DEVELOPER;

                }else if(group.equalsIgnoreCase("moderator")){

                    return Groups.MODERATOR;

                }else if(group.equalsIgnoreCase("supporter")){

                    return Groups.SUPPORTER;

                }else if(group.equalsIgnoreCase("builder")){

                    return Groups.BUILDER;

                }else if(group.equalsIgnoreCase("youtuber")){

                    return Groups.YouTuber;

                }else if(group.equalsIgnoreCase("tessie")){

                    return Groups.Tessie;

                }else if(group.equalsIgnoreCase("premiumplus")){

                    return Groups.Premiumplus;

                }else if(group.equalsIgnoreCase("premium")){

                    return Groups.Premium;

                }else if(group.equalsIgnoreCase("user")){

                    return Groups.USER;

                }

            }
            rs.close();
        }catch(Exception e){
            System.err.println(e);
        }
        return Groups.ERROR;



    }


    public static boolean isRegisteredUser(UUID uuid){
        boolean is = false;
        try{

            final ResultSet rs =  MySQL.query("SELECT id FROM groups WHERE uuid='" + uuid.toString() + "' LIMIT 1");
            while(rs.next()){
                is = true;
            }
            rs.close();
        }catch(Exception e){
            System.err.println(e);
        }
        return is;
    }


    public static String getLanguage(UUID uuid){

        try {
            ResultSet rs = MySQL.query("SELECT language FROM users WHERE uuid='" + uuid.toString() + "'");


            if(rs.next()){

                String language = rs.getString(1);

                return language;


            }
        } catch (SQLException e) {

        }

        return "en";

    }


    public static String getNamefromUUID(UUID uuid){

        try {
            ResultSet rs = MySQL.query("SELECT name FROM users WHERE uuid='" + uuid.toString() + "'");
            if (rs.next()) {
                return rs.getString(1);
            }
        }
        catch (SQLException ex) {


        }
        return null;

    }

    public static String getUUIDfromName(String name){

        try {
            ResultSet rs = MySQL.query("SELECT uuid FROM users WHERE name='" + name + "'");
            if (rs.next()) {
                return rs.getString(1);
            }
        }
        catch (SQLException ex) {


        }
        return null;

    }


}
