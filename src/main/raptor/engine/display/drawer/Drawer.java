package raptor.engine.display.drawer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import raptor.engine.game.Level;
import raptor.engine.game.entity.unit.Unit;
import raptor.engine.model.ModelComponent;

public class Drawer {

	private final LocationToViewportTransformer locToVp;
	private final Graphics2D actualG;

	private final BufferedImage buffer;
	private final Graphics2D g;

	private final int width;
	private final int height;

	private final Level temp;

	public Drawer(final int width, final int height, final Graphics2D g, final LocationToViewportTransformer locToVp) {
		this.locToVp = locToVp;
		actualG = g;

		buffer = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		this.g = buffer.createGraphics();

		this.width = width;
		this.height = height;

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		actualG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		temp = new Level(500, 500);
	}

	public void draw(final Unit unit) {
		clear();

		final Iterator<ModelComponent> components = unit.getModel().getModelVisuals();
		while(components.hasNext()) {
			final ModelComponent c = components.next();
			final int x = locToVp.transformX(unit.position.getX() + c.getX());
			final int y = locToVp.transformY(unit.position.getY() + c.getY());
			g.drawImage(c.getSprite().getImage(), x, y, null);
		}
		g.drawImage(temp.getLevel(), locToVp.transformX(0), locToVp.transformY(0), null);

		actualG.drawImage(buffer, 0, 0, null);
	}

	private void clear() {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
	}
}
