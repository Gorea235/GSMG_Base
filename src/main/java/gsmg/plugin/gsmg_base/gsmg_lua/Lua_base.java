package gsmg.plugin.gsmg_base.gsmg_lua;

import gsmg.plugin.gsmg_base.gsmg_base_main;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;

public class Lua_Base extends TwoArgFunction {
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
		library.set("printout", new PrintOut());
		return library;
	}

	public static class PrintOut extends VarArgFunction {
		public LuaValue onInvoke(Varargs toPrint) {
			doPrint(toPrint, "[GSMG][Lua]");
			return LuaValue.NIL;
		}

		public static void doPrint(Varargs msg, String prefix) {
			int arg = 1;
			if (msg.isnoneornil(arg)) {
				gsmg_base_main.Log(prefix, true);
			}
			while (msg.isnoneornil(arg) == false) {
				if (arg > 1) {
					gsmg_base_main.Log(prefix, true);
				}
				gsmg_base_main.Log(prefix + " " + msg.arg(arg).tojstring(),
						true);
				arg += 1;
			}
		}
	}

}
