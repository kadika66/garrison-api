package xyz.unbent.garrison.api.behaviour;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import xyz.unbent.garrison.api.guard.Guard;
import xyz.unbent.garrison.api.menu.MenuButton;

import java.util.List;

/**
 * What makes a kind of guard more than a mob that fights: healing friends, casting, shooting a held weapon.
 *
 * A guard type in garrison.yml names its behaviour with {@code behaviour: <id>}. Register yours with
 * {@link xyz.unbent.garrison.api.Garrison#registerBehaviour}, then server owners can give it to any type.
 * Garrison's own are {@code fighter} (the default) and {@code healer}.
 *
 * Targeting, posts, orders and rallying are Garrison's job and happen whatever the behaviour.
 * Every method runs on the server thread. A behaviour is shared by all guards using it, so keep per-guard
 * state in maps keyed by {@link Guard#getId()}, not in fields.
 */
public interface GuardBehaviour {

	/**
	 * Every time the guard's mob joins the world (when it is hired, and each time its chunk loads), after
	 * Garrison has set it up. Mob goals aren't saved with the entity, so add any here.
	 */
	default void onLoad(@NotNull Guard guard, @NotNull Mob mob) {}

	/** Once a second while the guard is loaded, after it has picked its target and moved. */
	default void tick(@NotNull Guard guard, @NotNull Mob mob) {}

	/**
	 * Whether the guard picks targets and fights. False for support guards: they keep to their post, follow
	 * and hold like any other, but never attack. Default true.
	 */
	default boolean fights(@NotNull Guard guard) {
		return true;
	}

	/**
	 * Extra buttons for this guard's gear menu (bottom row, up to seven). Asked each time the menu is drawn.
	 * A type with buttons gets a menu even if it has no gear slots.
	 */
	@NotNull
	default List<MenuButton> getMenuButtons(@NotNull Guard guard) {
		return List.of();
	}

	/** When the guard dies or leaves (dismissed, deserted, disbanded): clear any per-guard state. */
	default void onRemove(@NotNull Guard guard) {}
}
