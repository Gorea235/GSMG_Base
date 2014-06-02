package gsmg.plugin.gsmg_base;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gsmg_base_executor implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			String arg1 = args[0].toLowerCase();
			// TODO
			return true;
		} else {
			sender.sendMessage("The console cannot use these commands.");
		}
		return false;
	}

}
