package gsmg.plugin.gsmg_base;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gsmg_base_executor implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		boolean isPlayer = false;
		if (sender instanceof Player) {
			isPlayer = true;
		}
		if (args.length > 0) {
			String arg1 = args[0].toLowerCase();
			if (arg1.equals("help")) {
				sender.sendMessage("----GSMG Command List----");
				sender.sendMessage("/GSMG Close (Minigame)");
				sender.sendMessage("/GSMG (Minigame) (Command)");
				sender.sendMessage("/GSMG Start (Minigame)");
				sender.sendMessage("/GSMG reload");
				sender.sendMessage("/GSMG reloadlua");
				sender.sendMessage("/GSMG lobby (command) [name]");
			} else if (arg1.equals("close")) {

			} else if (arg1.equals("Start")) {

			} else if (arg1.equals("lobby")) {
				if (isPlayer) {
					if (args.length == 3) {
						if (args[1].toLowerCase().equals("create")) {
							gsmg_base_lobby.Create((Player) sender,
									args[2].toLowerCase());
						} else if (args[1].toLowerCase().equals("remove")) {
							gsmg_base_lobby.Remove((Player) sender,
									args[2].toLowerCase());
						} else if (args[1].toLowerCase().equals("teleport")) {
							gsmg_base_lobby.TeleportPlayerToLobby(
									(Player) sender, args[2].toLowerCase());
						} else if (args[1].toLowerCase().equals("relocate")) {
							gsmg_base_lobby.Relocate((Player) sender,
									args[2].toLowerCase());
						} else {
							sender.sendMessage(ChatColor.RED
									+ "/GSMG lobby (create/remove/teleport) (name)");
						}
					} else if (args.length == 2) {
						if (args[1].toLowerCase().equals("list")) {
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

			} else if (arg1.equals("reloadlua")) {
				gsmg_base_main.Log("Reloading Lua files...");
				gsmg_base_lua.main();
				gsmg_base_main.Log("Reloading complete!");
				if (isPlayer) {
					sender.sendMessage("Reloaded Lua files...");
				}
			} else {
				sender.sendMessage(ChatColor.RED
						+ "Command: /GSMG (command) [option]");
				sender.sendMessage(ChatColor.RED
						+ "Try '/GSMG help' for more information");
			}
		}
		return true;
	}
}
