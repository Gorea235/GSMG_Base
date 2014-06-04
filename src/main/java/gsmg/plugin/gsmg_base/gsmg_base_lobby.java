package gsmg.plugin.gsmg_base;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class gsmg_base_lobby {
	public static List<Lobby> LobbyList = new ArrayList<Lobby>();
	public static int LobbyCouter = 0;
	
// Lobby create
public static boolean Create(Player player, String[] args) {
	
        Location location = player.getLocation();
        if (args.length == 0){
          player.sendMessage(ChatColor.RED+ "/Lobby Create (Lobby Name)");
          if (!(LobbyCouter > 999)) {
			LobbyList.add(new Lobby(args[0], location));
			LobbyCouter++;
			player.sendMessage(ChatColor.RED+ "Lobby Created As: " + args[0]);
		  }else{
		    player.sendMessage(ChatColor.RED+ "Lobby Limit Exceeded!");
		  }
        }
    return false;
  }


//lobby remove


	//move to lobby
	public static void Move() {
	        for (Player player : LobbyList) {
	        String lobbyname = lobbynames[i];
	        Location lobbyLocation = lobbylocation[i];
	        player.teleport(lobbylocation);
	        player.sendmessage(ChatColor.GREEN+ "You Have Been Moved To the Lobby");
	        }
	}
	// side codings timer
	
	/*this.getServer().getscheduler()
	  .scheduleAsyncRepatingTask(this,new runnable() {
	  
	  plublic void run() {
	        if (number != -1) {
	          if number != 0 {
	              //message here (to put the time in the message add ("" + number));
	              number--;
	          } else {
	              Bukkit.broadcastMessage(" ") // the message if the timer = 0
	              number--;
	              }
	          }
	      }
	  },0L,20L);*/
	
	public static class Lobby {
		public String name = null;
		public Location location = null;
		
		public Lobby(String n, Location l) {
			this.name = n;
			this.location = l;
		}
	}
}
