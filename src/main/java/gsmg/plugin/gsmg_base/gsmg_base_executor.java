package gsmg.plugin.gsmg_base;

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
				sender.sendMessage("");
			} else if (arg1.equals("close")) {
				
			} else if (arg1 == ("Start")) {
				
			} else if (arg1 == ("lobby") && args[1].toLowerCase().equals("create")) {
				if (args.length == 0) {
					player.sendmessage(ChatColor.RED+ "/Lobby Ceate (Lobby Name)")
				} else {
				gsmg_base_lobby.Create();
			} else if (arg1 == ("lobby") && args[1].toLowerCase().equals("remove")) {
				
			} else if (arg1.equals("reloadlua")) {
				gsmg_base_main.Log("Reloading Lua files...");
				gsmg_base_lua.main();
				gsmg_base_main.Log("Reloading complete!");
				if (isPlayer) {sender.sendMessage("Reloaded Lua files...");}
			} else {
				return false;
			}
			return true;
		}
		return false;
	}
}
