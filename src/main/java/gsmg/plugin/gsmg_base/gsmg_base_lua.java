package gsmg.plugin.gsmg_base;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;

public class gsmg_base_lua {
	public static void main() {
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load("print 'hello, world'");
		chunk.call();
	}
}
