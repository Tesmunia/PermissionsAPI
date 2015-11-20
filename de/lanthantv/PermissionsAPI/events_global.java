package de.lanthantv.PermissionsAPI;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by Fabio on 13.11.2015.
 */
public class events_global implements Listener {



    @EventHandler
    public void onLogin(LoginEvent event){

        if(PermissionsAPI.isRegisteredUser(event.getConnection().getUniqueId())){


            if (PermissionsAPI.getNamefromUUID(event.getConnection().getUniqueId()).equalsIgnoreCase(event.getConnection().getName())) {



            } else {


                MySQL.update("UPDATE groups SET name='" + event.getConnection().getName() + "' WHERE uuid='" + event.getConnection().getUniqueId().toString() + "'");
            }

        }else{

            MySQL.update("INSERT INTO groups (uuid, name, rank) VALUES('" + event.getConnection().getUniqueId() + "', '" + event.getConnection().getName() + "', 'user')");


        }


    }



}
