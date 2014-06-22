package gsmg.plugin.gsmg_base;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class gsmg_base_world {
	public static void create(String name, World.Environment env, Long seed) {
		WorldCreator world_creator = new WorldCreator(name);
		world_creator.environment(env);
		if (seed != null)
			world_creator.seed(seed);
		Bukkit.createWorld(world_creator);
	}

	public static boolean unload(String name) {
		return unload(name, true);
	}

	public static boolean unload(String name, Boolean save) {
		if (Bukkit.getWorld(name) != null) {
			removePlayersFromWorld(name);
			Bukkit.unloadWorld(name, save);
			return true;
		}
		return false;
	}

	public static boolean isWorld(String folder) {
		File level = new File(folder + "/level.dat");
		File session = new File(folder + "/session.lock");
		return level.exists() || session.exists();
	}
	
	public static void removePlayersFromWorld(String world) {
		World _default = Bukkit.getWorlds().get(0);
		for (Player p : Bukkit.getWorld(world).getPlayers()) {
			p.teleport(_default.getSpawnLocation());
			p.sendMessage("You were teleported to the default world due to world unload");
		}
	}

	public static boolean copy(String source, String dest) {
		if (!isWorld(source)) {
			return false;
		}
		// Not all my code, source:
		// http://stackoverflow.com/questions/6214703/copy-entire-directory-contents-to-another-directory
		final Path targetPath = new File(dest).toPath();
		final Path sourcePath = new File(source).toPath();
		try {
			Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(final Path dir,
						final BasicFileAttributes attrs) throws IOException {
					Files.createDirectories(targetPath.resolve(sourcePath
							.relativize(dir)));
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(final Path file,
						final BasicFileAttributes attrs) throws IOException {
					Files.copy(file,
							targetPath.resolve(sourcePath.relativize(file)));
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException ex) {
			return false;
		}
		return true;
	}

	public static boolean delete(String name) {
		if (!isWorld(name)) {
			return false;
		}
		World _world = Bukkit.getWorld(name);
		if (_world != null) {
			unload(name);
		}
		// Not all my code, source:
		// http://stackoverflow.com/questions/779519/delete-files-recursively-in-java
		final Path targetPath = new File(name).toPath();
		try {
			Files.walkFileTree(targetPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file,
						IOException exc) throws IOException {
					// try to delete the file anyway, even if its attributes
					// could not be read, since delete-only access is
					// theoretically possible
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir,
						IOException exc) throws IOException {
					if (exc == null) {
						Files.delete(dir);
						return FileVisitResult.CONTINUE;
					} else {
						// directory iteration failed; propagate exception
						throw exc;
					}
				}
			});
		} catch (IOException ex) {
			return false;
		}
		return true;
	}

	public static boolean copyAndReplace(String source, String dest) {
		if (!delete(dest))
			return false;
		if (!copy(source, dest))
			return false;
		return true;
	}
	
	public static void teleportPlayerTo(Player player, World world) {
		player.teleport(world.getSpawnLocation());
	}
}
