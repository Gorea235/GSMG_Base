package gsmg.plugin.gsmg_base;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class gsmg_base_main extends JavaPlugin {
	public static String pluginPrefix = "[GSMG] ";
	
	public void broadcastMessage(String msg) {
		Bukkit.broadcastMessage(pluginPrefix + msg);
	}
	
	public static void Log(String toLog) {
		Log(toLog, false);
	}

	public static void Log(String toLog, Boolean noPrefix) {
		if (!noPrefix) {
			toLog = pluginPrefix + toLog;
		}
		Bukkit.getLogger().info(toLog);
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new gsmg_base_events(), this);
		getCommand("gsmg").setExecutor(new gsmg_base_executor());
		Log("Luaj Version: " + org.luaj.vm2.Lua._VERSION);
		gsmg_base_lua.main();
	}
}

public void loadConfigLobby(){
     string path = "GSMG_BASE.Lobby"
     getConfig().addDefualt(path);
     getConfig().options().copyDefaults(true);
     saveConfig();
     
public void onEnable(){
     loadConfigLobby();
