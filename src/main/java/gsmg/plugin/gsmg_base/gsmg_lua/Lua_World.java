package gsmg.plugin.gsmg_base.gsmg_lua;

import gsmg.plugin.gsmg_base.gsmg_base_world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;

public class Lua_World extends TwoArgFunction {
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
		library.set("getList", new getList());
		library.set("create", new create());
		library.set("unload", new unload());
		library.set("copy", new copy());
		library.set("delete", new delete());
		library.set("copyAndReplace", new copyAndReplace());
		library.set("getPlayers", new getPlayers());
		return library;
	}

	public class getList extends ZeroArgFunction {
		public LuaValue call() {
			LuaValue list = tableOf();
			int i = 1;
			for (World world : Bukkit.getWorlds()) {
				list.set(i, LuaValue.valueOf(world.getName()));
				i++;
			}
			return list;
		}
	}

	public class create extends ThreeArgFunction {
		public LuaValue call(LuaValue name, LuaValue env, LuaValue seed) {
			String _name = name.tojstring();
			String s_env = env.tojstring();
			World.Environment _env = null;
			Long _seed = null;
			if (env.isnil() || s_env.equalsIgnoreCase("normal")) {
				_env = World.Environment.NORMAL;
			} else if (s_env.equalsIgnoreCase("nether")) {
				_env = World.Environment.NETHER;
			} else if (s_env.equalsIgnoreCase("the_end")) {
				_env = World.Environment.THE_END;
			}
			if (!seed.isnil() || seed.islong()) {
				_seed = Long.parseLong(seed.tojstring());
			}
			gsmg_base_world.create(_name, _env, _seed);
			return LuaValue.NIL;
		}
	}

	public class unload extends TwoArgFunction {
		public LuaValue call(LuaValue name, LuaValue save) {
			String _name = name.tojstring();
			Boolean _save = true;
			if (!save.isnil()) {
				_save = Boolean.parseBoolean(save.tojstring());
			}
			return LuaValue.valueOf(gsmg_base_world.unload(_name, _save));
		}
	}

	public class copy extends TwoArgFunction {
		public LuaValue call(LuaValue source, LuaValue dest) {
			return LuaValue.valueOf(gsmg_base_world.copy(source.tojstring(),
					dest.tojstring()));
		}
	}

	public class delete extends OneArgFunction {
		public LuaValue call(LuaValue source) {
			return LuaValue.valueOf(gsmg_base_world.delete(source.tojstring()));
		}
	}

	public class copyAndReplace extends TwoArgFunction {
		public LuaValue call(LuaValue source, LuaValue dest) {
			return LuaValue.valueOf(gsmg_base_world.copyAndReplace(source.tojstring(),
					dest.tojstring()));
		}
	}
	
	public class getPlayers extends OneArgFunction {
		public LuaValue call(LuaValue world) {
			World _world = Bukkit.getWorld(world.tojstring());
			if (_world != null) {
				LuaValue list = tableOf();
				int i = 1;
				for (Player p : _world.getPlayers()) {
					list.set(i, p.getName());
					i++;
				}
				return list;
			}
			return LuaValue.NIL;
		}
	}
}
