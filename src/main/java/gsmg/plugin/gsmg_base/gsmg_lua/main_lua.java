package gsmg.plugin.gsmg_base.gsmg_lua;

import java.util.HashMap;

public class main_lua {
	static String LuaClassLoc = "gsmg.plugin.gsmg_base.gsmg_lua.";

	public HashMap<String, String> getClasses() {
		HashMap<String, String> classes = new HashMap<String, String>();
		classes.put("base", LuaClassLoc + "Lua_Base");
		classes.put("block", LuaClassLoc + "Lua_Block");
		classes.put("chatcolors", LuaClassLoc + "Lua_ChatColor");
		classes.put("chatcolours", LuaClassLoc + "Lua_ChatColor");
		classes.put("event", LuaClassLoc + "Lua_Event");
		classes.put("lobby", LuaClassLoc + "Lua_Lobby");
		classes.put("location", LuaClassLoc + "Lua_Location");
		classes.put("minigame", LuaClassLoc + "Lua_Minigame");
		classes.put("player", LuaClassLoc + "Lua_Player");
		classes.put("world", LuaClassLoc + "Lua_World");
		return classes;
	}
}
