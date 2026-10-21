package xyz.unbent.garrison.api.war;

/**
 * Which side of a war someone is on.
 */
public enum WarSide {
	ATTACKERS,
	DEFENDERS,
	NOBODY;

	public WarSide opposite() {
		return switch (this) {
			case ATTACKERS -> DEFENDERS;
			case DEFENDERS -> ATTACKERS;
			case NOBODY -> NOBODY;
		};
	}
}
