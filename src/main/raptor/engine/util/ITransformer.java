package raptor.engine.util;

public interface ITransformer<IN,OUT> {
	public OUT transform(final IN in);
}
