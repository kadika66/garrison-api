package xyz.unbent.garrison.api.addon;

import org.jetbrains.annotations.NotNull;

/**
 * An addon loaded from {@code plugins/Garrison/addons/}: a plain jar with a {@code garrison-addon.yml} at its root,
 * no plugin.yml needed.
 *
 * <pre>
 * # garrison-addon.yml
 * name: Elementalist
 * version: 1.0.0
 * author: Kad
 * main: xyz.unbent.elementalist.ElementalistAddon
 * # Optional: plugins that must be enabled, or the addon isn't loaded.
 * depend: []
 * </pre>
 *
 * The main class needs a public no-argument constructor. Addons load once, when Garrison enables, after its API
 * is ready, and are disabled before it shuts down; there is no reload. An addon jar can use Bukkit/Paper, Towny,
 * Garrison's API and its own classes. To use another plugin's code, make the addon a normal plugin with
 * {@code depend: [Garrison]} instead: Paper only shares a plugin's classes with plugins that depend on it.
 *
 * An addon runs with the same power as any plugin. Only install addons you trust.
 */
public interface GarrisonAddon {

	/** Register behaviours, war providers and listeners here. */
	void onEnable(@NotNull AddonContext context);

	/** Garrison is shutting down. Save anything you need to. */
	default void onDisable() {}
}
