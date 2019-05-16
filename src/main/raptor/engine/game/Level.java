package raptor.engine.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Level {
	public int width;
	public int height;

	Image img;

	public Level(final int width, final int height) {
		this.width = width;
		this.height = height;

		final BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		final Graphics2D g = (Graphics2D) newImage.getGraphics();
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width-1, height-1);
		newImage.flush();
		img = newImage;
	}

	public Image getLevel() {
		return img;
	}
}
