package gsmg.plugin.gsmg_base;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

public class gsmg_base_events implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		try {
			Player player = (Player) event.getPlayer();
			Block block = event.getClickedBlock();
			if ((block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST)
					&& (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				Sign sign = (Sign) block.getState();
				if (sign.getLine(0).equalsIgnoreCase("[GSMG]")) {
					if (sign.getLine(1).equalsIgnoreCase("Test")) {
						sign.setLine(0, ChatColor.DARK_RED + "[GSMG]");
						sign.setLine(2, ChatColor.BLUE + "Donkey");
						sign.setLine(3, sign.getLine(3));
						sign.update();
					}
				}
			}
		} catch (Exception ex) {

		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		
	}
}
