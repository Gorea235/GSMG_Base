function onPlayerLeave ( ply ) -- called whenever any player quits the server
  --[[
    ply: the player who quit. this is a table of functions relating to that player
    
    This function could be used to remove the player from the game if they quit mid-game.
  --]]
  
  -- you can put anything here
  this.print( "Player "..ply.getName().." has quit the game. They don't know what they are missing out on!" ) -- example usage, very basic
end

return onPlayerLeave -- returns the function