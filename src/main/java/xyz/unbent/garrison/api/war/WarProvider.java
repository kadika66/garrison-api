package xyz.unbent.garrison.api.war;

import com.palmergames.bukkit.towny.object.Town;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A source of wars between towns: a war plugin, a raid system, an event. Register one with
 * {@link xyz.unbent.garrison.api.Garrison#registerWarProvider}. Garrison's own SiegeWar support
 * is one of these.
 *
 * Called from the server thread, once a second while guards think, so keep it cheap.
 */
public interface WarProvider {

	/** Every active war (not ended, not just declared and waiting) this provider knows of. */
	@NotNull
	List<War> getActiveWars();

	/** Whether the town is under attack right now, e.g. has an active siege on it. Stops it hiring guards by default. */
	boolean isUnderAttack(@NotNull Town town);
}
