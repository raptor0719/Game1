package raptor.engine.model;

public enum Direction {
	LEFT(0, 180, 359, "L"),
	RIGHT(1, 0, 179, "R");

	private final int value;
	private final int min;
	private final int max;
	private final String abbreviation;

	private Direction(final int value, final int min, final int max, final String abbreviation) {
		this.value = value;

		if (min < 0 || min > 360 || max < 0 || max > 360)
			throw new IllegalArgumentException("Invalid degree given for Direction.");

		this.min = min;
		this.max = max;
		this.abbreviation = abbreviation;
	}

	public int getValue() {
		return value;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public boolean isDirection(final int degrees) {
		final int cleaned = cleanDegrees(degrees);

		return cleaned >= min && cleaned <= max;
	}

	public static Direction calculateDirection(final int degrees) {
		final int cleaned = cleanDegrees(degrees);

		for (final Direction d : Direction.values())
			if (d.isDirection(cleaned))
				return d;
		throw new RuntimeException("Unable to find direction for degrees: " + degrees);
	}

	public static int cleanDegrees(final int degrees) {
		if (degrees >= 0 && degrees < 360)
			return degrees;

		int cleaned = Math.abs(degrees);

		final boolean negative = cleaned != degrees;

		while (cleaned >= 360)
			cleaned -= 360;

		return (negative) ? 360 - cleaned : cleaned;
	}
}
