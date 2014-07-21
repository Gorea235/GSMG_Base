package gsmg.plugin.gsmg_base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import gsmg.plugin.gsmg_base.gsmg_base_lobby;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class gsmg_base_main extends JavaPlugin {
	public static final String sep = File.separator;
	public static Logger logger;
	public static String pluginPrefix = "[GSMG] ";
	public static gsmg_base_main plugin;

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
		logger.info(toLog);
	}

	public static String path(String... strings) {
		String _path = "";
		String _sep = "";
		for (String s : strings) {
			_path = _path + _sep + s;
			_sep = sep;
		}
		return _path;
	}

	public void loadConfigLobby() {
		reloadConfig();
		getConfig().options().copyDefaults(true);
		if (new File(getDataFolder().getAbsolutePath() + sep + "config.yml")
				.isFile()) {
			List<String> lobbys = getConfig().getStringList("lobbylist");
			FileConfiguration config = getConfig();
			for (String l : lobbys) {
				gsmg_base_lobby.LobbyList.put(
						l,
						new gsmg_base_lobby.Lobby(l, new Location(Bukkit
								.getWorld(config.getString("lobbys." + l
										+ ".world")), config
								.getDouble("lobbys." + l + ".x"), config
								.getDouble("lobbys." + l + ".y"), config
								.getDouble("lobbys." + l + ".z"),
								(float) config
										.getDouble("lobbys." + l + ".yaw"),
								(float) config.getDouble("lobbys." + l
										+ ".pitch"))));
			}
		} else {
			saveConfig();
		}
	}

	public void saveConfigLobby() {
		saveConfig();
		try {
			PrintWriter writer = new PrintWriter(getDataFolder()
					.getAbsolutePath() + sep + "config.yml");
			writer.write("");
			writer.close();
		} catch (FileNotFoundException e) {
		}
		List<String> lobbys = new ArrayList<String>();
		FileConfiguration config = getConfig();
		for (gsmg_base_lobby.Lobby l : gsmg_base_lobby.LobbyList.values()) {
			lobbys.add(l.name);
			config.set("lobbys." + l.name + ".world", l.location.getWorld()
					.getName());
			config.set("lobbys." + l.name + ".x", l.location.getX());
			config.set("lobbys." + l.name + ".y", l.location.getY());
			config.set("lobbys." + l.name + ".z", l.location.getZ());
			config.set("lobbys." + l.name + ".yaw", l.location.getYaw());
			config.set("lobbys." + l.name + ".pitch", l.location.getPitch());
		}
		getConfig().set("lobbylist", lobbys);
		saveConfig();
	}

	@Override
	public void onEnable() {
		plugin = this;
		logger = Bukkit.getLogger();
		getServer().getPluginManager().registerEvents(new gsmg_base_events(),
				this);
		getCommand("gsmg").setExecutor(new gsmg_base_executor());
		Log("Luaj Version: " + org.luaj.vm2.Lua._VERSION);
		mainLoad();
	}
	
	public void mainLoad() {
		gsmg_base_lua.main();
		loadConfigLobby();
	}

	@Override
	public void onDisable() {
		gsmg_base_events.CallShutdownEvent();
		plugin = null;
		logger = null;
	}
}
