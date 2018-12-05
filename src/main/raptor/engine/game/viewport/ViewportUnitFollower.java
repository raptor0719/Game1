package raptor.engine.game.viewport;

import raptor.engine.game.Viewport;
import raptor.engine.game.entity.unit.Unit;

public class ViewportUnitFollower {
	private final Viewport viewport;
	private final Unit unit;

	public ViewportUnitFollower(final Viewport viewport, final Unit unit) {
		this.viewport = viewport;
		this.unit = unit;
	}

	public void follow() {
		viewport.setXpos(unit.position.getX() - (viewport.getWidth() / 2));
		viewport.setYpos(unit.position.getY() - (viewport.getHeight() / 2));
	}
}
