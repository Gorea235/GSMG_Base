package gsmg.plugin.gsmg_base.gsmg_lua;

import gsmg.plugin.gsmg_base.gsmg_base_lobby;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;

public class Lua_Lobby extends TwoArgFunction {
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
		library.set("getList", new getList());
		library.set("getLocation", new getLocation());
		library.set("teleportPlayerTo", new teleportPlayerTo());
		return library;
	}

	public class getList extends ZeroArgFunction {
		public LuaValue call() {
			LuaValue list = tableOf();
			int i = 1;
			for (String name : gsmg_base_lobby.LobbyList.keySet()) {
				list.set(i, LuaValue.valueOf(name));
				i++;
			}
			return list;
		}
	}

	public class getLocation extends OneArgFunction {
		public LuaValue call(LuaValue lobby) {
			String _lobby = lobby.tojstring();
			if (gsmg_base_lobby.LobbyList.containsKey(_lobby)) {
				return Lua_Location.LuaValueFromLocation(
						gsmg_base_lobby.LobbyList.get(_lobby).location, false);
			} else {
				return LuaValue.NIL;
			}
		}
	}
	
	public class teleportPlayerTo extends TwoArgFunction {
		public LuaValue call(LuaValue player, LuaValue lobby) {
			Player _player = Bukkit.getPlayer(player.tojstring());
			if (_player != null) {
				if (gsmg_base_lobby.TeleportPlayerToLobby(_player, lobby.tojstring())) {
					return LuaValue.TRUE;
				}
			}
			return LuaValue.FALSE;
		}
	}
}
