package de.lanthantv.PermissionsAPI;

public enum Groups {

	ADMIN("§c[Admin] "),
	DEVELOPER("§b[Dev] "),
	MODERATOR("§3[Mod] "),
	BT("§2[BT] "),
	YouTuber("§5"),
	PREMIUM("§6"),
	USER("§a"),
	ERROR("");

	String prefix;

	private Groups(String prefix){

		this.prefix = prefix;

	}
	
	public String getPrefix(){
		
		return prefix;
		
		
		
	}
	




}


