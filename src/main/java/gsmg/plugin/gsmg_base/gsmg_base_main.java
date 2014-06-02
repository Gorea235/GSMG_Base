package gsmg.plugin.gsmg_base;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class gsmg_base_main extends JavaPlugin {
	public void broadcastMessage(String msg) {
		Bukkit.broadcastMessage("[GSMG - Hunger Games] " + msg);
	}

	public static void Log(String toLog) {
		Bukkit.getLogger().info(toLog);
	}

	@Override
	public void onEnable() {
		getCommand("gsmg").setExecutor(new gsmg_base_executor());
		Log("Luaj Version: " + org.luaj.vm2.Lua._VERSION);
		gsmg_base_lua.main();
	}
}
