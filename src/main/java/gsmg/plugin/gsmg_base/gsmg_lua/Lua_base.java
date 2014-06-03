package gsmg.plugin.gsmg_base.gsmg_lua;

import gsmg.plugin.gsmg_base.gsmg_base_main;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

public class Lua_base extends TwoArgFunction {
	
	public Lua_base() {}

	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
		library.set("printout", new PrintOut());
		return library;
	}
	
	public class PrintOut extends OneArgFunction {
		public LuaValue call(LuaValue toPrint) {
			gsmg_base_main.Log(toPrint.tojstring());
			return null;
		}
	}
	
}
