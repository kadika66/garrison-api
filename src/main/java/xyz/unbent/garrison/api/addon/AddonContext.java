package xyz.unbent.garrison.api.addon;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.unbent.garrison.api.Garrison;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * What an addon from the addons folder gets to work with.
 */
public interface AddonContext {

	/** The Garrison API. */
	@NotNull
	Garrison getGarrison();

	/**
	 * The Garrison plugin. Use it wherever Bukkit or Garrison asks for a plugin: as the owner in
	 * {@link Garrison#registerBehaviour}, for scheduling tasks, for NamespacedKeys.
	 */
	@NotNull
	Plugin getPlugin();

	/** The name from garrison-addon.yml. */
	@NotNull
	String getName();

	@NotNull
	String getVersion();

	/** Logs through Garrison's logger, each line starting with the addon's name. */
	@NotNull
	Logger getLogger();

	/** {@code plugins/Garrison/addons/<name>/}, created on first call. For the addon's config and data. */
	@NotNull
	File getDataFolder();

	/** A file packed in the addon's jar, e.g. a default config, or null if there is none. Close it after use. */
	@Nullable
	InputStream getResource(@NotNull String path);

	/** Registers Bukkit event handlers. They stay registered until Garrison disables. */
	void registerEvents(@NotNull Listener listener);
}
