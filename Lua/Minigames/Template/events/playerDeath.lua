function onPlayerDeath ( ply ) -- called whenever any player dies
  --[[
    ply: the player who died. this is a table of functions relating to that player
    
    This function could be used to set a players position after they die in-game,
    allowing for a 'death' room.
  --]]

  -- you can do anything here
  this.print( "Player "..ply.getName().." died. Silly bugger!" ) -- example usage, very basic
end

return onPlayerDeath -- returns the function