package gsmg.plugin.gsmg_base;

import java.util.Map;
import java.util.logging.Level;

import gsmg.plugin.gsmg_base.gsmg_lua.Lua_Event;
import gsmg.plugin.gsmg_base.gsmg_lua.Lua_Event.EventEnums;
import gsmg.plugin.gsmg_base.gsmg_lua.Lua_Minigame;
import gsmg.plugin.gsmg_base.gsmg_lua.Lua_Player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.luaj.vm2.*;

public class gsmg_base_events implements Listener {
	private static Lua_Player _Lua_Player = new Lua_Player();

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

	private static void printEventHandleError(
			Map.Entry<LuaValue, LuaValue> handler, Event event, LuaError err) {
		printEventHandleError(handler, event.getEventName(), err);
	}

	private static void printEventHandleError(
			Map.Entry<LuaValue, LuaValue> handler, String eventName,
			LuaError err) {
		gsmg_base_main.logger.log(Level.WARNING, String.format(
				"The handler '%s' for event '%s' has caused an error: %s",
				handler.getKey().tojstring(), eventName, err.getMessage()));
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		String name = event.getPlayer().getName();
		for (Map.Entry<LuaValue, LuaValue> handler : Lua_Event.events.get(
				EventEnums.PlayerJoinEvent).entrySet()) {
			try {
				handler.getValue().call(_Lua_Player.externalGetPlayer(name));
			} catch (LuaError err) {
				printEventHandleError(handler, event, err);
			}
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		String name = event.getPlayer().getName();
		for (Map.Entry<LuaValue, LuaValue> handler : Lua_Event.events.get(
				EventEnums.PlayerLeaveEvent).entrySet()) {
			try {
				handler.getValue().call(_Lua_Player.externalGetPlayer(name));
			} catch (LuaError err) {
				printEventHandleError(handler, event, err);
			}
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		String name = event.getPlayer().getName();
		LuaValue msg = LuaValue.valueOf(event.getMessage());
		LuaValue format = LuaValue.valueOf(event.getFormat());
		Varargs args;
		for (Map.Entry<LuaValue, LuaValue> handler : Lua_Event.events.get(
				EventEnums.PlayerChatEvent).entrySet()) {
			try {
				args = handler.getValue().call(
						_Lua_Player.externalGetPlayer(name), msg, format);
				if (args.isnoneornil(1)) {
					gsmg_base_main.logger
							.log(Level.WARNING,
									String.format(
											"The format return was missing, ignoring ChatEvent handler %s",
											handler.getKey()));
				} else {
					format = args.arg1();
				}
			} catch (LuaError err) {
				printEventHandleError(handler, event, err);
			}
		}
		event.setMessage(msg.tojstring());
		event.setFormat(format.tojstring());
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		String name = event.getEntity().getName();
		for (Map.Entry<LuaValue, LuaValue> handler : Lua_Event.events.get(
				EventEnums.PlayerDeathEvent).entrySet()) {
			try {
				handler.getValue().call(_Lua_Player.externalGetPlayer(name));
			} catch (LuaError err) {
				printEventHandleError(handler, event, err);
			}
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		final PlayerRespawnEvent _event = event;
		Bukkit.getScheduler().runTaskLater(gsmg_base_main.plugin,
				new Runnable() {

					@Override
					public void run() {
						String name = _event.getPlayer().getName();
						for (Map.Entry<LuaValue, LuaValue> handler : Lua_Event.events
								.get(EventEnums.PlayerRespawnEvent).entrySet()) {
							try {
								handler.getValue().call(
										_Lua_Player.externalGetPlayer(name));
							} catch (LuaError err) {
								printEventHandleError(handler, _event, err);
							}
						}
					}
				}, 1);
	}

	public static void CallShutdownEvent() {
		try {
			for (Map.Entry<LuaValue, LuaValue> handler : Lua_Event.events.get(
					EventEnums.ShutdownEvent).entrySet()) {
				try {
					handler.getValue().call();
				} catch (LuaError err) {
					printEventHandleError(handler, "Shutdown Event", err);
				}
			}
		} catch (Exception ex) {
			gsmg_base_main.logger
					.log(Level.WARNING,
							String.format(
									"An exception occured while trying to fire shutdown event, reason: %s",
									ex.getLocalizedMessage()));
		}
	}
}
