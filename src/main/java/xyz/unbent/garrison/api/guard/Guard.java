package xyz.unbent.garrison.api.guard;

import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Location;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.unbent.garrison.api.gear.GearSlot;

import java.util.UUID;

/**
 * A guard a town has hired. This is a read-only view: change guards through Garrison's own
 * commands and the methods on {@link xyz.unbent.garrison.api.Garrison}.
 *
 * A guard exists whether or not its chunk is loaded. {@link #getEntity()} is null while it isn't.
 */
public interface Guard {

	/** The guard's id. Also the UUID of its mob. */
	@NotNull
	UUID getId();

	/** The town that hired it. */
	@NotNull
	UUID getTownId();

	/** The town that hired it, or null if that town no longer exists. */
	@Nullable
	Town getTown();

	/** The id of its guard type, e.g. "specialist". */
	@NotNull
	String getTypeId();

	/** Where it stands when it has nothing else to do. A copy: changing it changes nothing. */
	@NotNull
	Location getPost();

	@NotNull
	GuardOrder getOrder();

	/** The player it follows, while its order is {@link GuardOrder#FOLLOW}; otherwise null. */
	@Nullable
	UUID getLeader();

	/** The guard's mob, or null while its chunk isn't loaded or while it has fallen (see {@link #isFallen}). */
	@Nullable
	Mob getEntity();

	/**
	 * True while the guard is dead but will return to its post (death mode economy-reward). A fallen guard keeps
	 * its gear and its place in the garrison, and comes back with a new mob, so a new {@link #getId()}.
	 */
	boolean isFallen();

	/**
	 * A copy of what the guard carries in a slot, or null if the slot is empty or its type doesn't have it.
	 * Gear is kept by Garrison, not on the mob, so this works while the guard is unloaded too.
	 * Use {@link #isIssued} to tell the town's own kit from gear players put in.
	 */
	@Nullable
	ItemStack getGear(@NotNull GearSlot slot);

	/**
	 * A value a behaviour saved on this guard with {@link #setData}, or null. Kept in Garrison's own data file,
	 * so it survives restarts, unloading and (in death mode economy-reward) the guard falling and returning.
	 */
	@Nullable
	String getData(@NotNull String key);

	/**
	 * Saves a value on this guard (null removes it). The one thing an addon may change on a guard directly.
	 * Keys are 1-64 characters of a-z, 0-9, _, - and : ; prefix yours with your addon's name, e.g.
	 * {@code "elementalist:element"}. Saved straight away, so don't call it every tick.
	 *
	 * @throws IllegalArgumentException if the key isn't valid
	 */
	void setData(@NotNull String key, @Nullable String value);

	/** True if this item is issued kit (given with the guard; it never drops and can't be taken out). */
	boolean isIssued(@Nullable ItemStack item);
}
