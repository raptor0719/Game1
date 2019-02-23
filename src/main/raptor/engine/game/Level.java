package raptor.engine.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import raptor.engine.display.api.IDrawable;
import raptor.modelLibrary.model.util.point.IRotatedPoint;

public class Level implements IDrawable {
	public int width;
	public int height;

	Image img;

	public Level(final int width, final int height) {
		this.width = width;
		this.height = height;

		final BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		final Graphics2D g = (Graphics2D) newImage.getGraphics();
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width, height);
		newImage.flush();
		img = newImage;
	}

	@Override
	public boolean doDraw() {
		return false;
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public IRotatedPoint getPosition() {
		return null;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}
}
