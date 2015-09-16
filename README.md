# PermissionsAPI
Implement it in your BungeeCord/Spigot Plugin!

Don't forget to add depend: [PermissionsAPI] to your plugin.yml File

Usage:

If you want to lookup if someone has Admin Rights you use:

if(PermissionsAPI.getGroup(UUIDofPlayer) == Groups.ADMIN){

//Run Codes for Admins only

}

if you get the GroupType Groups.Error there is something wrong with MySQL
