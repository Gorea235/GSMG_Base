package gsmg.plugin.gsmg_base;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

// hide global players
public class gsmg_base_hub {
	public void hideAllPlayers(Player player) {
		for (Player p : Bukkit.getOnlinePlayers())
			player.hidePlayer(p);
		player.sendMessage("All Players Have Been Vanished");
	}

	// show global players
	public void showAllPlayers(Player player) {
		for (Player p : Bukkit.getOnlinePlayers())
			player.showPlayer(p);
		player.sendMessage("All Players Will Now Be Shown");
	}
}