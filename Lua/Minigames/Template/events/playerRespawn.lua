function onPlayerRespawn ( ply ) -- called whenever any player respawns
  --[[
    ply: the player who respawned. this is a table of functions relating to that player
    
    This function could be used to set a player to a location when they respawn after
    dying in-game
  --]]

  -- you can put anything here
  this.print( "Player "..ply.getName().." has respawned. Huzzar!" ) -- example usage, very basic
end

return onPlayerRespawn -- returns the function