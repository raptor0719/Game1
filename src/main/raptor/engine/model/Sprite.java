package raptor.engine.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

import javax.imageio.ImageIO;

import raptor.engine.util.geometry.Point;

public class Sprite {
	private final Point attachPoint;
	private final BufferedImage image;
	private Long hash;

	public Sprite(final int x, final int y, final BufferedImage image) {
		this.attachPoint = new Point(x, y);
		this.image = image;
		this.hash = null;
	}

	public Point getAttachPoint() {
		return attachPoint;
	}

	public BufferedImage getImage() {
		return image;
	}

	public long getHash() {
		if (hash == null)
			hash = calculateHash(image);
		return hash;
	}

	private static long calculateHash(final BufferedImage image) {
		try {
			final ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			ImageIO.write(image, "png", ostream);

			final MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(ostream.toByteArray());

			long value = 0;
			for (final byte b : digest.digest())
				value = (value << 8) + (b & 0xFF);

			return value;
		} catch (final Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
