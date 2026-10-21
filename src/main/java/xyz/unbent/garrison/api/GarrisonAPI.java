package xyz.unbent.garrison.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Entry point for addons.
 *
 * <pre>{@code
 * if (GarrisonAPI.isAvailable()) {
 *     Garrison garrison = GarrisonAPI.get();
 *     garrison.registerWarProvider(myPlugin, new MyRaids());
 * }
 * }</pre>
 *
 * An addon that is a normal plugin should list Garrison under {@code depend} (or {@code softdepend})
 * in its plugin.yml, so Garrison is enabled first.
 */
public final class GarrisonAPI {

	private static Garrison instance;

	private GarrisonAPI() {}

	/**
	 * The running plugin.
	 *
	 * @throws IllegalStateException if Garrison isn't enabled
	 */
	@NotNull
	public static Garrison get() {
		Garrison garrison = instance;
		if (garrison == null)
			throw new IllegalStateException("Garrison is not enabled.");
		return garrison;
	}

	public static boolean isAvailable() {
		return instance != null;
	}

	/** Set by the Garrison plugin itself. Not for addons. */
	@ApiStatus.Internal
	public static void setInstance(@Nullable Garrison garrison) {
		instance = garrison;
	}
}
