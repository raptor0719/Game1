package raptor.engine.util.transformer;

public interface ITransformer<IN,OUT> {
	public OUT transform(final IN in);
}
