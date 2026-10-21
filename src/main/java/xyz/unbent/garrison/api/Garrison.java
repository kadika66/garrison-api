package xyz.unbent.garrison.api;

import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.unbent.garrison.api.behaviour.GuardBehaviour;
import xyz.unbent.garrison.api.guard.Guard;
import xyz.unbent.garrison.api.war.War;
import xyz.unbent.garrison.api.war.WarProvider;

import java.util.Collection;
import java.util.List;

/**
 * The Garrison plugin, as addons see it. Get it with {@link GarrisonAPI#get()}.
 *
 * Call it from the server thread only.
 */
public interface Garrison {

	/** The version of this API the running plugin implements, e.g. "1.0.0". */
	@NotNull
	String getApiVersion();

	/* Guards */

	/** Every guard on the server, loaded or not. A snapshot. */
	@NotNull
	Collection<Guard> getGuards();

	/** Every guard of one town, loaded or not. A snapshot. */
	@NotNull
	List<Guard> getGuards(@NotNull Town town);

	/** The guard this entity is, or null if it isn't one. */
	@Nullable
	Guard getGuard(@NotNull Entity entity);

	default boolean isGuard(@NotNull Entity entity) {
		return getGuard(entity) != null;
	}

	/* Wars */

	/**
	 * Adds a source of wars, such as a war plugin's battles or a raid system. Guards rally to the wars
	 * over their own town and fight the other side's players and guards. Several providers can be
	 * registered at once; their wars are combined.
	 *
	 * Providers are dropped when their plugin is disabled, or with {@link #unregisterWarProviders}.
	 */
	void registerWarProvider(@NotNull Plugin owner, @NotNull WarProvider provider);

	void unregisterWarProviders(@NotNull Plugin owner);

	/** Every active war this town is on one side of, from every provider. */
	@NotNull
	List<War> getWarsOf(@NotNull Town town);

	/* Behaviours */

	/**
	 * Adds a guard behaviour that guard types can use with {@code behaviour: <id>} in garrison.yml.
	 * Ids are lower case; registering an id that is already taken replaces it (and logs a warning).
	 * Dropped when the owning plugin is disabled; its guards fall back to {@code fighter}.
	 */
	void registerBehaviour(@NotNull Plugin owner, @NotNull String id, @NotNull GuardBehaviour behaviour);

	/* Friend or foe */

	/** Would this guard fight this entity, as things stand right now (targets, wars, grudges, outlaws)? */
	boolean isEnemy(@NotNull Guard guard, @NotNull LivingEntity other);

	/**
	 * Hurts an enemy on the guard's behalf, for abilities that aren't the mob's own attacks: the damage comes from
	 * the guard's mob, so kills are credited to it and every friendly-fire rule applies. Does nothing (returns
	 * false) if the guard isn't loaded, the target is dead or the guard wouldn't fight it ({@link #isEnemy}).
	 *
	 * @return whether the damage was dealt (another plugin can still cancel it afterwards)
	 */
	boolean damage(@NotNull Guard guard, @NotNull LivingEntity target, double amount);

	/* Training (skill trees in training.yml; a town's guards use its mayor's) */

	/**
	 * Whether this guard's town has trained an unlock for its type, e.g. {@code "elementalist:inferno"}.
	 * Unlocks are whatever ids training.yml names; an addon decides what its own do. Lower case.
	 */
	boolean hasUnlock(@NotNull Guard guard, @NotNull String unlock);

	/**
	 * A number setting for this guard after its town's training: {@code base} (the server's value) with every
	 * {@code set}, {@code add} and {@code multiply} node for that key applied. Use keys with your addon's prefix,
	 * e.g. {@code "elementalist:fire-blast.damage"}, and read them where you use the value.
	 */
	double getTrained(@NotNull Guard guard, @NotNull String setting, double base);

	/** A true/false setting for this guard: {@code base}, unless a training node sets it. */
	boolean getTrained(@NotNull Guard guard, @NotNull String setting, boolean base);

	/**
	 * Is this entity on the guard's side: a member of its town, nation or an allied nation, or a guard of one,
	 * and not someone the guard would fight? Use it for anything that helps (heals, buffs).
	 */
	boolean isFriendly(@NotNull Guard guard, @NotNull LivingEntity other);
}
