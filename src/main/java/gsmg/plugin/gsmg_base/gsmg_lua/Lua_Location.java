package gsmg.plugin.gsmg_base.gsmg_lua;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;

public class Lua_Location extends TwoArgFunction {
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
		library.set("new", new newLocation());
		return library;
	}

	public class newLocation extends VarArgFunction {
		public LuaValue onInvoke(Varargs args) throws LuaError {
			LuaValue tbl = tableOf();
			String worldName = null;
			Double x = null;
			Double y = null;
			Double z = null;
			Float yaw = null;
			Float pitch = null;
			if (args.isnoneornil(2)) {
				if (Bukkit.getPlayer(args.arg(1).tojstring()) == null) {
					throw new LuaError(String.format(
							"Player '%s' doesn't exist!", args.arg(1)
									.tojstring()));
				}
				Player ply = Bukkit.getPlayer(args.arg(1).tojstring());
				worldName = ply.getWorld().getName();
				Location loc = ply.getLocation();
				x = loc.getX();
				y = loc.getY();
				z = loc.getZ();
				yaw = loc.getYaw();
				pitch = loc.getPitch();
			} else {
				if (!args.isnoneornil(4)) {
					try {
						args.arg(1).checkstring();
						if (Bukkit.getWorld(args.arg(1).tojstring()) == null) {
							throw new LuaError(String.format(
									"World '%s' doesn't exist!", args.arg(1)
											.tojstring()));
						}
						worldName = Bukkit.getWorld(args.arg(1).tojstring())
								.getName();
						args.arg(2).checkdouble();
						x = Double.parseDouble(args.arg(2).tojstring());
						args.arg(3).checkdouble();
						y = Double.parseDouble(args.arg(3).tojstring());
						args.arg(4).checkdouble();
						z = Double.parseDouble(args.arg(4).tojstring());
					} catch (LuaError err) {
						throw new LuaError("Incorrect arguments!");
					}
					if (!args.isnoneornil(6)) {
						yaw = Float.parseFloat(args.arg(6).tojstring());
						pitch = Float.parseFloat(args.arg(5).tojstring());
					}
				} else {
					throw new LuaError("Incorrect arguments!");
				}
			}
			tbl.set("world", worldName);
			tbl.set("x", x);
			tbl.set("y", y);
			tbl.set("z", z);
			if (pitch != null && yaw != null) {
				tbl.set("yaw", yaw);
				tbl.set("pitch", pitch);
			}
			return tbl;
		}
	}

	public static Location LocationFromLuaValue(LuaValue tbl) {
		Location loc;
		if (tbl.get("pitch") != LuaValue.NIL && tbl.get("yaw") != LuaValue.NIL) {
			loc = new Location(Bukkit.getWorld(tbl.get("world").tojstring()),
					Double.parseDouble(tbl.get("x").tojstring()),
					Double.parseDouble(tbl.get("y").tojstring()),
					Double.parseDouble(tbl.get("z").tojstring()),
					Float.parseFloat(tbl.get("yaw").tojstring()),
					Float.parseFloat(tbl.get("pitch").tojstring()));
		} else {
			loc = new Location(Bukkit.getWorld(tbl.get("world").tojstring()),
					Double.parseDouble(tbl.get("x").tojstring()),
					Double.parseDouble(tbl.get("y").tojstring()),
					Double.parseDouble(tbl.get("z").tojstring()));
		}
		return loc;
	}

	public static LuaValue LuaValueFromLocation(Location loc) {
		return LuaValueFromLocation(loc, true);
	}

	public static LuaValue LuaValueFromLocation(Location loc, boolean pitch_yaw) {
		LuaValue tbl = tableOf();
		tbl.set("world", loc.getWorld().getName());
		tbl.set("x", loc.getX());
		tbl.set("y", loc.getY());
		tbl.set("z", loc.getZ());
		if (pitch_yaw) {
			tbl.set("yaw", loc.getYaw());
			tbl.set("pitch", loc.getPitch());
		}
		return tbl;
	}
}
