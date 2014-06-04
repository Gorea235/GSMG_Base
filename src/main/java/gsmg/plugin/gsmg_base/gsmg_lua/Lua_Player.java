package gsmg.plugin.gsmg_base.gsmg_lua;

import gsmg.plugin.gsmg_base.gsmg_base_main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;

public class Lua_Player extends TwoArgFunction {

	public Lua_Player() {
	}

	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
		library.set("teleportToPlayer", new teleportToPlayer());
		library.set("teleportToLocation", new teleportToLocation());
		return library;
	}

	public class teleportToPlayer extends TwoArgFunction {
		public LuaValue call(LuaValue actionPlayer, LuaValue toPlayer) {
			Player firstPlayer = null;
			Player secondPlayer = null;
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getDisplayName().equals(actionPlayer.tojstring())) {
					firstPlayer = p;
				} else if (p.getDisplayName().equals(toPlayer.tojstring())) {
					secondPlayer = p;
				}
			}
			if (!(firstPlayer == null) && !(secondPlayer == null)) {
				try {
					firstPlayer.teleport(secondPlayer);
					return LuaValue.TRUE;
				} catch (Exception ex) {
					gsmg_base_main
							.Log(String
									.format("Could not teleport player '%s' to player '%s',  reason: %s",
											actionPlayer, toPlayer,
											ex.getMessage()));
				}
			}
			return LuaValue.FALSE;
		}
	}

	public class teleportToLocation extends VarArgFunction {
		public LuaValue call(LuaValue player, LuaValue x, LuaValue y, LuaValue z) {
			Player mainPlayer = null;
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getDisplayName().equals(player)) {
					mainPlayer = p;
				}
			}
			if (!(mainPlayer == null)) {
				try {
					mainPlayer.teleport(new Location(mainPlayer.getWorld(),
							Double.parseDouble(x.tojstring()), Double
									.parseDouble(y.tojstring()), Double
									.parseDouble(z.tojstring())));
					return LuaValue.TRUE;
				} catch (Exception ex) {
					gsmg_base_main
							.Log(String
									.format("Could not teleport player '%s' to location '%s, %s, %s',  reason: %s",
											mainPlayer, x.tojstring(),
											y.tojstring(), z.tojstring(),
											ex.getMessage()));
				}
			}
			return LuaValue.FALSE;
		}
	}

}
