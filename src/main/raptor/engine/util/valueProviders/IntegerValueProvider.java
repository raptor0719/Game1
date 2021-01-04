package raptor.engine.util.valueProviders;

public abstract class IntegerValueProvider<T> {
	private final T source;

	public IntegerValueProvider(final T source) {
		this.source = source;
	}

	public int getValue() {
		return sourceValue(source);
	}

	protected abstract int sourceValue(T source);
}
