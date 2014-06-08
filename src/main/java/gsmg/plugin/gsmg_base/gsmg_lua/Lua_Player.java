package gsmg.plugin.gsmg_base.gsmg_lua;

import gsmg.plugin.gsmg_base.gsmg_base_main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;

public class Lua_Player extends TwoArgFunction {
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
		library.set("teleport", new teleport());
		library.set("teleportToPlayer", new teleportToPlayer());
		library.set("getLocation", new getLocation());
		library.set("exists", new exists());
		library.set("getPlayer", new getPlayer());
		library.set("sendMessgae", new sendMessage());
		return library;
	}

	public class teleport extends TwoArgFunction {
		public LuaValue call(LuaValue player, LuaValue location)
				throws LuaError {
			try {
				Player ply = Bukkit.getPlayer(player.tojstring());
				if (!(ply == null)) {
					ply.teleport(Lua_Location.LocationFromLuaValue(location));
				} else {
					return LuaValue.FALSE;
				}
			} catch (Exception ex) {
				gsmg_base_main.Log(String.format(
						"Could not teleport player '%s', reason: %s",
						player.tojstring(), ex.getMessage()));
			}
			return LuaValue.TRUE;
		}
	}

	public class teleportToPlayer extends TwoArgFunction {
		public LuaValue call(LuaValue actionPlayer, LuaValue toPlayer)
				throws LuaError {
			Player firstPlayer = Bukkit.getPlayer(actionPlayer.tojstring());
			Player secondPlayer = Bukkit.getPlayer(toPlayer.tojstring());
			if (!(firstPlayer == null) && !(secondPlayer == null)) {
				try {
					firstPlayer.teleport(secondPlayer);
					return LuaValue.TRUE;
				} catch (Exception ex) {
					throw new LuaError(
							String.format(
									"Could not teleport player '%s' to player '%s',  reason: %s",
									actionPlayer, toPlayer, ex.getMessage()));
				}
			}
			return LuaValue.FALSE;
		}
	}

	public class getLocation extends OneArgFunction {
		public LuaValue call(LuaValue player) throws LuaError {
			Player ply = Bukkit.getPlayer(player.tojstring());
			if (!(ply == null)) {
				try {
					return Lua_Location.LuaValueFromLocation(ply.getLocation());
				} catch (Exception ex) {
					throw new LuaError(String.format(
							"An exception occured, type: %s, reason: %s",
							ex.getClass(), ex.getMessage()));
				}
			} else {
				return LuaValue.NIL;
			}
		}
	}

	public class exists extends OneArgFunction {
		public LuaValue call(LuaValue player) {
			if (Bukkit.getPlayer(player.tojstring()) != null) {
				return LuaValue.TRUE;
			} else {
				return LuaValue.FALSE;
			}
		}
	}
	
	public class sendMessage extends TwoArgFunction {
		public LuaValue call(LuaValue player, LuaValue msg) {
			Player ply = Bukkit.getPlayer(player.tojstring());
			if (!(ply == null)) {
				ply.sendMessage(msg.tojstring());
				return LuaValue.TRUE;
			}
			return LuaValue.FALSE;
		}
	}

	public class getPlayer extends OneArgFunction {
		private Lua_Player.teleport _teleport = new Lua_Player.teleport();
		private Lua_Player.teleportToPlayer _teleportToPlayer = new Lua_Player.teleportToPlayer();
		private Lua_Player.getLocation _getLocation = new Lua_Player.getLocation();
		private Lua_Player.sendMessage _sendMessage = new Lua_Player.sendMessage();

		private void stillExists(LuaValue _player) throws LuaError {
			if (Bukkit.getPlayer(_player.tojstring()) == null) {
				throw new LuaError(String.format(
						"Player '%s' no longer exists!", _player.tojstring()));
			}
		}

		public LuaValue call(LuaValue player) {
			if (Bukkit.getPlayer(player.tojstring()) == null) {
				return LuaValue.NIL;
			}
			LuaValue functions = tableOf();
			functions.set("teleport", new teleport(player));
			functions.set("teleportToPlayer", new teleportToPlayer(player));
			functions.set("getLocation", new getLocation(player));
			functions.set("exists", new exists(player));
			functions.set("getName", new getName(player));
			functions.set("sendMessage", new sendMessage(player));
			return functions;
		}

		private class exists extends ZeroArgFunction {
			public LuaValue _player = null;

			public exists(LuaValue player) {
				this._player = player;
			}

			public LuaValue call() {
				if (Bukkit.getPlayer(this._player.tojstring()) != null) {
					return LuaValue.TRUE;
				}
				return LuaValue.FALSE;
			}
		}

		private class getName extends ZeroArgFunction {
			public LuaValue _player = null;

			public getName(LuaValue player) {
				this._player = player;
			}

			public LuaValue call() {
				stillExists(this._player);
				return this._player;
			}
		}

		private class teleport extends OneArgFunction {
			public LuaValue _player = null;

			public teleport(LuaValue player) {
				this._player = player;
			}

			public LuaValue call(LuaValue location) {
				stillExists(this._player);
				return _teleport.call(this._player, location);
			}
		}

		private class teleportToPlayer extends OneArgFunction {
			public LuaValue _player = null;

			public teleportToPlayer(LuaValue player) {
				this._player = player;
			}

			public LuaValue call(LuaValue actionPlayer) {
				stillExists(this._player);
				return _teleportToPlayer.call(this._player, actionPlayer);
			}
		}

		private class getLocation extends ZeroArgFunction {
			public LuaValue _player = null;

			public getLocation(LuaValue player) {
				this._player = player;
			}

			public LuaValue call() {
				stillExists(this._player);
				return _getLocation.call(this._player);
			}
		}

		private class sendMessage extends OneArgFunction {
			public LuaValue _player = null;

			public sendMessage(LuaValue player) {
				this._player = player;
			}

			public LuaValue call(LuaValue msg) {
				stillExists(this._player);
				return _sendMessage.call(this._player, msg);
			}
		}
	}
}
/*
 * ======= This is so the 'getPlayer' class has continuity with the main class.
 * Lua Functions:
 * 
 * teleport, args: LuaValue player, LuaValue location
 * 
 * teleportToPlayer, args: LuaValue actionPlayer, LuaValue toPlayer
 * 
 * getLocation, args: LuaValue player
 * 
 * sendMessage, args: LuaValue player, LuaValue msg
 */
