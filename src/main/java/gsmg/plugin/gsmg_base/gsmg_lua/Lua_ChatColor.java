package gsmg.plugin.gsmg_base.gsmg_lua;

import org.bukkit.ChatColor;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;

public class Lua_ChatColor extends TwoArgFunction {
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
		library.set("COLOR_CHAR", LuaValue.valueOf(ChatColor.COLOR_CHAR));
		library.set("AQUA", LuaValue.valueOf(ChatColor.AQUA.toString()));
		library.set("BLACK", LuaValue.valueOf(ChatColor.BLACK.toString()));
		library.set("BLUE", LuaValue.valueOf(ChatColor.BLUE.toString()));
		library.set("BOLD", LuaValue.valueOf(ChatColor.BOLD.toString()));
		library.set("DARK_AQUA",
				LuaValue.valueOf(ChatColor.DARK_AQUA.toString()));
		library.set("DARK_BLUE",
				LuaValue.valueOf(ChatColor.DARK_BLUE.toString()));
		library.set("DARK_GRAY",
				LuaValue.valueOf(ChatColor.DARK_GRAY.toString()));
		library.set("DARK_GREEN",
				LuaValue.valueOf(ChatColor.DARK_GREEN.toString()));
		library.set("DARK_PURPLE",
				LuaValue.valueOf(ChatColor.DARK_PURPLE.toString()));
		library.set("DARK_RED", LuaValue.valueOf(ChatColor.DARK_RED.toString()));
		library.set("GOLD", LuaValue.valueOf(ChatColor.GOLD.toString()));
		library.set("GRAY", LuaValue.valueOf(ChatColor.GRAY.toString()));
		library.set("GREY", LuaValue.valueOf(ChatColor.GRAY.toString()));
		library.set("GREEN", LuaValue.valueOf(ChatColor.GREEN.toString()));
		library.set("ITALIC", LuaValue.valueOf(ChatColor.ITALIC.toString()));
		library.set("LIGHT_PURPLE",
				LuaValue.valueOf(ChatColor.LIGHT_PURPLE.toString()));
		library.set("MAGIC", LuaValue.valueOf(ChatColor.MAGIC.toString()));
		library.set("RED", LuaValue.valueOf(ChatColor.RED.toString()));
		library.set("RESET", LuaValue.valueOf(ChatColor.RESET.toString()));
		library.set("STRIKETHROUGH",
				LuaValue.valueOf(ChatColor.STRIKETHROUGH.toString()));
		library.set("UNDERLINE",
				LuaValue.valueOf(ChatColor.UNDERLINE.toString()));
		library.set("WHITE", LuaValue.valueOf(ChatColor.WHITE.toString()));
		library.set("YELLOW", LuaValue.valueOf(ChatColor.YELLOW.toString()));
		return library;
	}
}
