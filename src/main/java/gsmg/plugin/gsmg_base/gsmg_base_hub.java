
// hide global players
public class  Vanish extends JavaPlugin {	
	public void hideAllPlayers(Player player){
		for (Player p : Bukkit.getOnlinePlayers())
			player.hidePlayer(p);
		player.sendMessage("All Players Have Been Vanished");
	}
// show global players
	public void showAllPlayers(Player player){
		for (Player p : Bukkit.getOnlinePlayers())
			player.showPlayer(p);
		player.sendMessage("All Players Will Now Be Shown");
	}
