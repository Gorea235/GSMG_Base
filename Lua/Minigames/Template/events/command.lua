function onCommand ( ply, args ) -- called whenever the minigame's command is run
  --[[
    ply: the player who sent the command. this is a table of functions relating to that player
    args: a table of the arguments that were sent (generally in order they were written, 
      only includes the aruments after the minigame command
      
    This function can be used from anything, from setting up the minigame to running it
  --]]
  
  -- you can do anything here
  if args[1]:lower() == "help" then -- example 'if' creating a response for the user
    ply.sendMessage( "This is the default help message, it should be changed!\nBy the way, hello world!" ) -- a 'help' response
  else
    ply.sendMessage( "This plugin does not contain that command" ) -- a response to an unknown command
  end
end

return onCommand -- returns the function