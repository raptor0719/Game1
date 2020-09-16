package raptor.engine.game.ui.input;

import java.util.HashMap;
import java.util.Map;

import raptor.engine.util.transformer.ITransformer;

public class JavaAwtMouseTranslator implements ITransformer<Integer, MouseInput> {
	private final Map<Integer, MouseInput> map;

	public JavaAwtMouseTranslator() {
		map = new HashMap<Integer, MouseInput>();

		map.put(1, MouseInput.LEFTMOUSE);
		map.put(2, MouseInput.RIGHTMOUSE);
		map.put(3, MouseInput.MIDDLEMOUSE);
	}

	@Override
	public MouseInput transform(final Integer in) {
		return map.get(in);
	}

}
