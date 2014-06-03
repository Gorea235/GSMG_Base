package gsmg.plugin.gsmg_base;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;
import org.luaj.vm2.lib.*;

import java.io.File;

public class gsmg_base_lua {
	static Globals globals = JsePlatform.standardGlobals();
	
	public static void main() {
		File LuaDir = new File("plugins/GSMG_MiniGames");
		if (!LuaDir.isDirectory()) {
			LuaDir.mkdir();
		} else {
			for (String f : LuaDir.list()) {
				try {
					globals.loadfile("plugins/GSMG_MiniGames/" + f).call();
				} catch (LuaError err) {
					gsmg_base_main.Log(String.format("Error loading file '%s', reason: %s", f, err.getMessage()));
				}
			}
		}
	}
	
	public class base extends TwoArgFunction {
		
		public base() {}

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
}