package de.lanthantv.PermissionsAPI;

/**
 * Created by Fabio on 13.11.2015.
 */
public enum Groups {

    ADMIN("§c[ADMIN] "),
    DEVELOPER("§b[DEV] "),
    MODERATOR("§3[MOD] "),
    SUPPORTER("§9[SUP] "),
    BUILDER("§2[BUILDER]"),
    YouTuber("§5"),
    Tessie("§e[TESSIE] "),
    Premiumplus("§6[+] "),
    Premium("§6"),
    USER("§a"),
    ERROR("");

    String prefix;


    Groups(String prefix){

        this.prefix = prefix;

    }

    public String getPrefix(){

        return prefix;

    }

}
