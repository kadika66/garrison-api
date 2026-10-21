package xyz.unbent.garrison.api.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.unbent.garrison.api.guard.Guard;

/**
 * A button a behaviour adds to its guards' gear menu (see
 * {@link xyz.unbent.garrison.api.behaviour.GuardBehaviour#getMenuButtons}), e.g. the Elementalist's element picker.
 *
 * Buttons sit on the menu's bottom row, up to seven, in the order given. The icon is only ever a picture: every
 * click in a Garrison menu is cancelled, so it can't be taken, and Garrison has already checked that the viewer
 * manages the guard's town and stands close enough before {@link #onClick} runs. The menu is redrawn after each click.
 */
public interface MenuButton {

	/** What the button looks like right now, for this viewer. A new item each time; Garrison doesn't keep it. */
	@NotNull
	ItemStack getIcon(@NotNull Guard guard, @NotNull Player viewer);

	/**
	 * The viewer clicked the button.
	 *
	 * @param rightClick true for a right-click, false for a left-click (shift-clicks count as their plain click)
	 */
	void onClick(@NotNull Guard guard, @NotNull Player viewer, boolean rightClick);
}
