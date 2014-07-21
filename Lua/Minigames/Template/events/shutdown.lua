function onShutdown () -- called whenever there is a reload or shutdown
  --[[
    This function could be used to clean up when the server needs it to,
    e.g. if there is a game going on and a reload is called, the players can be
    sent back to the lobby and progress saved, so it could continue after the
    reload.
  --]]

  -- you can put anything here
  this.print( "This minigame is shutting down, :(" )
end

return onShutdown -- returns the function