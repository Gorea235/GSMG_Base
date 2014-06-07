package gsmg.plugin.gsmg_base.gsmg_lua;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;

public class Lua_Event extends TwoArgFunction {
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
		
		return library;
	}
	
}
