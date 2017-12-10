package util.transformer;

public interface ITransformer<IN,OUT> {
	public OUT transform(final IN in);
}
