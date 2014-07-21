function onPlayerJoin ( ply ) -- called whenever any player joins the server
  --[[
    ply: the player who joined. this is a table of functions relating to that player
    
    This function is one of the more useless ones. It could be used to advertise the
    minigame, but this is discouraged.
  --]]
  
  -- you can put anything here
  ply.sendMessage( "Welcome to the server! Would you like to play a game?" ) -- example usage, very basic
end

return onPlayerJoin -- returns the function