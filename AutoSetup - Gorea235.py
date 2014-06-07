import os
import shutil
import time

launch_dir = os.path.dirname(os.path.realpath(__file__))
originalFileLoc = os.path.join(launch_dir, "build", "gsmg_base.jar")
newFileLoc = os.path.join("I:", "Games", "Minecraft", "Servers", "Bukkit_Server", "plugins", "gsmg_base.jar")

if os.path.getsize(originalFileLoc) < 100000:
    installer = __import__("LuaJ-installer")

os.remove(newFileLoc)
print("Removed " + newFileLoc)
shutil.copyfile(originalFileLoc, newFileLoc)
print("Copied " + originalFileLoc + " to " + newFileLoc)

time.sleep(4)
