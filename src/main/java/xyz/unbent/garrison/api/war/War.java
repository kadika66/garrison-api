package xyz.unbent.garrison.api.war;

import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

/**
 * One active war a town's guards may fight in, such as a SiegeWar siege.
 *
 * The garrison only ever sees wars through this interface, so the plugin providing them
 * (see {@link WarProvider}) can be missing without anything else in the plugin noticing.
 */
public interface War {

	/** The town being fought over. */
	Town getTargetTown();

	/** Which side this town is on, directly or through its nation. */
	WarSide getSide(Town town);

	/** Which side this player is fighting for, as the war plugin counts it. */
	WarSide getSide(Player player);

	/** Where guards of the town gather while it is fought over, e.g. the siege banner. Null if there is nowhere. */
	@Nullable
	Location getRallyPoint();

	/** Whether there is fighting right now (e.g. a battle session is running), as opposed to the war merely being on. */
	boolean isFightingNow();
}
