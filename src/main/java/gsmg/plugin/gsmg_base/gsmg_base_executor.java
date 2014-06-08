package gsmg.plugin.gsmg_base;

import gsmg.plugin.gsmg_base.gsmg_lua.Lua_Minigame;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;

public class gsmg_base_executor implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		boolean isPlayer = false;
		if (sender instanceof Player) {
			isPlayer = true;
		}
		if (args.length > 0) {
			String arg1 = args[0].toLowerCase();
			if (arg1.equalsIgnoreCase("help")) {
				sender.sendMessage("----GSMG Command List----");
				sender.sendMessage("/GSMG Close (Minigame)");
				sender.sendMessage("/GSMG (Minigame) (Command)");
				sender.sendMessage("/GSMG Start (Minigame)");
				sender.sendMessage("/GSMG reload");
				sender.sendMessage("/GSMG reloadlua");
				sender.sendMessage("/GSMG lobby (command) [name]");
			} else if (arg1.equalsIgnoreCase("close")) {

			} else if (arg1.equalsIgnoreCase("start")) {

			} else if (arg1.equalsIgnoreCase("lobby")) {
				if (isPlayer) {
					if (args.length == 3) {
						if (args[1].toLowerCase().equalsIgnoreCase("create")) {
							gsmg_base_lobby.Create((Player) sender,
									args[2].toLowerCase());
						} else if (args[1].toLowerCase().equalsIgnoreCase(
								"remove")) {
							gsmg_base_lobby.Remove((Player) sender,
									args[2].toLowerCase());
						} else if (args[1].toLowerCase().equalsIgnoreCase(
								"teleport")) {
							gsmg_base_lobby.TeleportPlayerToLobby(
									(Player) sender, args[2].toLowerCase());
						} else if (args[1].toLowerCase().equalsIgnoreCase(
								"relocate")) {
							gsmg_base_lobby.Relocate((Player) sender,
									args[2].toLowerCase());
						} else {
							sender.sendMessage(ChatColor.RED
									+ "/GSMG lobby (create/remove/teleport) (name)");
						}
					} else if (args.length == 2) {
						if (args[1].toLowerCase().equalsIgnoreCase("list")) {
							gsmg_base_lobby.ListLobbys((Player) sender);
						} else {
							sender.sendMessage(ChatColor.RED
									+ "/GSMG lobby (create/remove/teleport/list) [name]");
						}
					} else {
						sender.sendMessage(ChatColor.RED
								+ "/GSMG lobby (create/remove/teleport/list) [name]");
					}
				} else {
					sender.sendMessage("This command is only available to players.");
				}

			} else if (arg1.equalsIgnoreCase("reloadlua")) {
				gsmg_base_main.Log("Reloading Lua files...");
				gsmg_base_lua.main();
				gsmg_base_main.Log("Reloading complete!");
				if (isPlayer) {
					sender.sendMessage("Reloaded Lua files...");
				}
			} else {
				if (arg1.equalsIgnoreCase("minigame")
						|| arg1.equalsIgnoreCase("mg")) {
					if (args.length > 2) {
						LuaValue hookedFunc = Lua_Minigame.minigames.onCommandEvents
								.get(args[1].toLowerCase());
						if (hookedFunc != null) {
							LuaValue varargs = LuaValue.tableOf();
							for (int i = 2; i < args.length; i++) {
								varargs.set(i - 1, LuaValue.valueOf(args[i]));
							}
							hookedFunc.call(varargs);
						}
					} else if (args.length == 1) {
						sender.sendMessage(ChatColor.RED
								+ "Command: /GSMG minigame (name) [args]");
					} else {
						sender.sendMessage(String.format(ChatColor.RED
								+ "Command: /GSMG minigame %s [args]",
								args[1].toLowerCase()));
					}
				} else {
					sender.sendMessage(ChatColor.RED
							+ "Command: /GSMG (command) [option]");
					sender.sendMessage(ChatColor.RED
							+ "Try '/GSMG help' for more information");
				}
			}
		}
		return true;
	}
}
