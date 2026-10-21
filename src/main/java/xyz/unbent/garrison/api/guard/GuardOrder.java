package xyz.unbent.garrison.api.guard;

/**
 * A guard's standing order.
 */
public enum GuardOrder {
	/** Stand its post, and rally to its town's wars. */
	POST,
	/** Follow a member of its town, anywhere. */
	FOLLOW,
	/** Stand exactly where it is. It can't walk, so it can be carried (by an airship, say). */
	HOLD
}
