package gsmg.plugin.gsmg_base;

import gsmg.plugin.gsmg_base.gsmg_lua.Lua_Event;
import gsmg.plugin.gsmg_base.gsmg_lua.Lua_Minigame;
import gsmg.plugin.gsmg_base.gsmg_lua.Lua_Player;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.luaj.vm2.LuaValue;

public class gsmg_base_events implements Listener {
	private Lua_Player _Lua_Player = new Lua_Player();

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		try {
			Player player = (Player) event.getPlayer();
			Block block = event.getClickedBlock();
			if ((block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST)
					&& (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				Sign sign = (Sign) block.getState();
				if (sign.getLine(0).equalsIgnoreCase(
						ChatColor.DARK_GREEN + "[GMSG]")) {
					if (sign.getLine(3).isEmpty()) {
						gsmg_base_lobby.TeleportPlayerToLobby(player,
								sign.getLine(1));
					} else {
						if (Lua_Minigame.minigames.onSignClickEvents
								.containsKey(sign.getLine(3))) {
							Lua_Minigame.minigames.onSignClickEvents.get(
									sign.getLine(3)).call(
									_Lua_Player.externalGetPlayer(player
											.getName()),
									LuaValue.valueOf(sign.getLine(1)),
									LuaValue.valueOf(sign.getLine(2)));
						}
					}
				}
			}
		} catch (Exception ex) {

		}
	}

	@EventHandler
	public void onSignChange(SignChangeEvent sign) {
		if (sign.getLine(0).equalsIgnoreCase("[GSMG]")) {
			if (sign.getLine(3).isEmpty()) {
				if (!gsmg_base_lobby.LobbyList.containsKey(sign.getLine(1))) {
					sign.setLine(0, ChatColor.ITALIC + "" + ChatColor.DARK_RED
							+ "#ERROR");
				} else {
					sign.setLine(0, ChatColor.DARK_GREEN + "[GMSG]");
				}
			} else {
				if (!Lua_Minigame.minigames.globals.containsKey(sign.getLine(3)
						.toLowerCase())) {
					sign.setLine(0, ChatColor.ITALIC + "" + ChatColor.DARK_RED
							+ "#ERROR");
				} else {
					sign.setLine(0, ChatColor.DARK_GREEN + "[GMSG]");
				}
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		String name = event.getPlayer().getName();
		for (LuaValue func : Lua_Event.PlayerJoinEvent.values()) {
			func.call(_Lua_Player.externalGetPlayer(name));
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		String name = event.getPlayer().getName();
		for (LuaValue func : Lua_Event.PlayerLeaveEvent.values()) {
			func.call(_Lua_Player.externalGetPlayer(name));
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		String name = event.getPlayer().getName();
		for (LuaValue func : Lua_Event.PlayerChatEvent.values()) {
			func.call(_Lua_Player.externalGetPlayer(name));
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		String name = event.getEntity().getName();
		for (LuaValue func : Lua_Event.PlayerDeathEvent.values()) {
			func.call(_Lua_Player.externalGetPlayer(name));
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		String name = event.getPlayer().getName();
		for (LuaValue func : Lua_Event.PlayerRespawnEvent.values()) {
			func.call(_Lua_Player.externalGetPlayer(name));
		}
	}
}
