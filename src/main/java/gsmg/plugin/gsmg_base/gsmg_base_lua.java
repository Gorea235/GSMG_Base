package gsmg.plugin.gsmg_base;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;

import gsmg.plugin.gsmg_base.gsmg_lua.Lua_Base;
import gsmg.plugin.gsmg_base.gsmg_lua.Lua_Minigame.minigames;
import gsmg.plugin.gsmg_base.gsmg_lua.main_lua;
import gsmg.plugin.gsmg_base.gsmg_base_main;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class gsmg_base_lua {
	static Globals globals = JsePlatform.standardGlobals();
	static List<File> LuaFolders = new ArrayList<File>();
	static List<File> QueuedLuaFiles = new ArrayList<File>();
	final static String sep = File.separator;
	static LuaValue classes;

	@SuppressWarnings("unchecked")
	public static void main() {
		// Extra Globals
		globals.set("IncludePath", LuaValue.valueOf(gsmg_base_main.path(
				"plugins", "GSMG_MiniGames", "Includes", "")));
		globals.set("MiniGamePath", LuaValue.valueOf(gsmg_base_main.path(
				"plugins", "GSMG_MiniGames", "MiniGames", "")));
		globals.set("separator", LuaValue.valueOf(sep));
		globals.set("print", new Lua_Base.PrintOut());
		// Class Path Setup
		classes = LuaValue.tableOf();
		addClasses(new main_lua().getClasses());
		// Addon Loader
		ClassLoader loader = null;
		Class<?> mainClass = null;
		File addonFolder = new File(gsmg_base_main.path("plugins", "GSMG_Base",
				"Addons"));
		if (addonFolder.isDirectory()) {
			for (File f : addonFolder.listFiles()) {
				if (f.isFile()
						&& f.getName().substring(f.getName().length() - 4)
								.equalsIgnoreCase(".jar")) {
					try {
						URL[] urls = { f.toURI().toURL() };
						loader = new URLClassLoader(urls);
						mainClass = Class.forName("gsmg.addon.main_lua", true,
								loader);
						addClasses((HashMap<String, String>) mainClass
								.getDeclaredMethod("getClasses").invoke(
										mainClass.newInstance()));
					} catch (Exception ex) {
						gsmg_base_main
								.Log(String
										.format("An exception occured while trying to load addon '%s', message: %s\nStacktrace:",
												f.getName(), ex.toString()));
						ex.printStackTrace();
					}
				}
			}
		} else {
			addonFolder.mkdir();
		}
		globals.set("class", classes);
		// Lua Loading
		LuaFolders.clear();
		QueuedLuaFiles.clear();
		minigames.globals.clear();
		minigames.onCommandEvents.clear();
		minigames.onSignClickEvents.clear();
		LuaFolders.add(new File(gsmg_base_main
				.path("plugins", "GSMG_MiniGames")));
		LuaFolders.add(new File(gsmg_base_main.path("plugins",
				"GSMG_MiniGames", "MiniGames")));
		LuaFolders.add(new File(gsmg_base_main.path("plugins",
				"GSMG_MiniGames", "Includes")));
		for (File f : LuaFolders) {
			if (!f.isDirectory()) {
				f.mkdir();
			}
		}
		// Getting all Lua files in base Lua directory
		String mainLuaDir = "plugins/GSMG_MiniGames";
		for (String f : new File(mainLuaDir).list()) {
			if (f.substring(f.length() - 4).equals(".lua")) {
				QueuedLuaFiles.add(new File(mainLuaDir + "/" + f));
			}
		}
		// Getting all minigame main Lua files in minigame folders (only runs
		// the file that has the same name as the directory it is in, ignoring
		// case of course)
		for (String d : new File("plugins/GSMG_MiniGames/MiniGames").list()) {
			if (new File("plugins/GSMG_MiniGames/MiniGames/" + d).isDirectory()) {
				mainLuaDir = "plugins/GSMG_MiniGames/MiniGames/" + d;
				for (String f : new File(mainLuaDir).list()) {
					if (f.substring(f.length() - 4).equals(".lua")
							&& f.substring(0, f.length() - 4).equalsIgnoreCase(
									d)) {
						QueuedLuaFiles.add(new File(mainLuaDir + "/" + f));
					}
				}
			}
		}
		for (File f : QueuedLuaFiles) {
			try {
				globals.loadfile(f.getAbsolutePath()).call();
			} catch (LuaError err) {
				gsmg_base_main.Log(String.format(
						"Error loading file '%s', reason: %s", f,
						err.getMessage()));
			}
		}
	}

	public static String toString(LuaValue var) {
		return globals.get("tostring").call(var).tojstring();
	}

	public static void addClasses(HashMap<String, String> c) {
		String k;
		String v;
		for (Map.Entry<String, String> entry : c.entrySet()) {
			k = entry.getKey();
			v = entry.getValue();
			if (classes.get(k) == LuaValue.NIL) {
				classes.set(k, v);
				gsmg_base_main
						.Log(String
								.format("Added class path '%s' to the class path table under index '%s'",
										v, k));
			} else {
				gsmg_base_main
						.Log(String
								.format("Could not add class path '%s' to the class path table, as an index with name '%s' already exists",
										v, k));
			}
		}
	}
}