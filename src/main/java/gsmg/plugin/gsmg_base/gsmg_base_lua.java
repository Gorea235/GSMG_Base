package gsmg.plugin.gsmg_base;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;
import org.luaj.vm2.lib.*;

import gsmg.plugin.gsmg_base.gsmg_lua.Lua_base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class gsmg_base_lua {
	static Globals globals = JsePlatform.standardGlobals();
	static List<File> LuaFolders = new ArrayList<File>();
	static List<File> QueuedLuaFiles = new ArrayList<File>();
	static String LuaClassLoc = "gsmg.plugin.gsmg_base.gsmg_lua.";
	
	public static void main() {
		globals.set("IncludePath", LuaValue.valueOf("plugins.GSMG_MiniGames.Includes."));
		globals.set("MiniGamePath", LuaValue.valueOf("plugins.GSMG_MiniGames.MiniGames."));
		LuaValue classes = LuaValue.tableOf();
		classes.set("base", LuaClassLoc + "Lua_base");
		classes.set("player", LuaClassLoc + "Lua_Player");
		globals.set("class", classes);
		LuaFolders.clear();
		LuaFolders.add(new File("plugins/GSMG_MiniGames"));
		LuaFolders.add(new File("plugins/GSMG_MiniGames/MiniGames"));
		LuaFolders.add(new File("plugins/GSMG_MiniGames/Includes"));
		for (File f : LuaFolders) {
			if (!f.isDirectory()) {
				f.mkdir();
			}
		}
		QueuedLuaFiles.clear();
		GetLuaFilesToRun("plugins/GSMG_MiniGames");
		for (String d : new File("plugins/GSMG_MiniGames/MiniGames").list()) {
			if (new File("plugins/GSMG_MiniGames/MiniGames/" + d).isDirectory()) {
				GetLuaFilesToRun("plugins/GSMG_MiniGames/MiniGames/" + d);
			}
		}
		for (File f : QueuedLuaFiles) {
			try {
				globals.loadfile(f.getAbsolutePath()).call();
			} catch (LuaError err) {
				gsmg_base_main.Log(String.format("Error loading file '%s', reason: %s", f, err.getMessage()));
			}
		}
	}
	
	public static void GetLuaFilesToRun(String dir) {
		for (String f : new File(dir).list()) {
			if (f.substring(f.length() - 4).equals(".lua")) {
				QueuedLuaFiles.add(new File(dir + "/" + f));
			}
		}
	}
}