package gsmg.plugin.gsmg_base;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;

public class gsmg_base_events implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		try {
			Player player = (Player) event.getPlayer();
			Block block = event.getClickedBlock();
			player.sendMessage("[GSMG] Checking if block is sign");
			if ((block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST)
					&& (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				Sign sign = (Sign) block.getState();
				player.sendMessage("[GSMG] Sign cheack started");
				if (sign.getLine(0).equalsIgnoreCase("[GSMG]")) {
					player.sendMessage("[GSMG] Stage one passed");
					if (sign.getLine(1).equalsIgnoreCase("Test")) {
						player.sendMessage("[GSMG] Line 2 has 'Test' on it");
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
}
