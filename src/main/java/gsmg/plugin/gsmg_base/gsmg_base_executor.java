package gsmg.plugin.gsmg_base;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gsmg_base_executor implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// if (sender instanceof Player) {
		if (args.length > 0) {
			String arg1 = args[0].toLowerCase();
			if (arg1 == "help") {
				sender.sendMessage("----GSMG Command List----");
				sender.sendMessage("/GSMG Close (Minigame)");
				sender.sendMessage("/GSMG (Minigame) (Command)");
				sender.sendMessage("/GSMG Start (Minigame)");
				sender.sendMessage("/GSMG reload");
				sender.sendMessage("/GSMG reloadlua");
				sender.sendMessage("");
			} else if (arg1.equals("close")) {

			} else if (arg1.equals("reloadlua")) {
				gsmg_base_main.Log("Reloading Lua files...");
				gsmg_base_lua.main();
				gsmg_base_main.Log("Reloading complete!");
			} else {
				return false;
			}
			return true;
		}
		return false;
		// } else {
		// sender.sendMessage("The console cannot use these commands.");
		// return true;
		// }
	}
}
