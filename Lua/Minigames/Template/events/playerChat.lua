function onPlayerChat ( ply, msg, fmt ) -- called whenever any player sends a chat message
  --[[
    ply: the player who sent the message. this is a table of functions relating to that player
    msg: the message that was sent
    fmt: the format that is going to be used. a java String.format compatible string, by default '<%1$s> %2$s'
      -- you have to return the format, even if you don't change it! --
      
    This function could be used to prefix a players message if they are playing this minigame,
    e.g. you could put '[In-Game]' in front of their name to state that they are currently
    playing a minigame, or you could put '[Queued]' in front of their name to state that
    they are waiting for the game to start. It would be recommended that you use the minigame
    name in the prefix if you are going to do this.
  --]]
  
  -- you can do anthing here (don't change the format unless you have to, as it could interfere with other minigames and/or be annoying
  this.print( "Player "..ply.getName().." said "..msg..", current format: "..fmt ) -- example usage, very basic
  return fmt -- you have to return the format, if you don't then the function will not be used.
               -- this allows you to change the format, as the string that is sent back will be used as the format
end

return onPlayerChat -- returns the function