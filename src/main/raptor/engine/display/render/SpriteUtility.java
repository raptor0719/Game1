package raptor.engine.display.render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import raptor.engine.model.Sprite;
import raptor.engine.util.geometry.Point;

public class SpriteUtility {
	public static Sprite translateSprite(final Sprite sprite, final int rotation) {
		final BufferedImage originalImage = sprite.getImage();
		final Point originalAttachmentPoint = sprite.getAttachPoint();

		final BufferedImage imageWithRotationBuffer = addRotationBuffer(originalImage);
		final Point attachmentPointWithRotationBuffer = accountForRotationBuffer(originalAttachmentPoint, originalImage, imageWithRotationBuffer);

		final BufferedImage rotatedImage = rotateImage(imageWithRotationBuffer, rotation);
		final Point rotatedAttachemntPoint = _rotatePoint(attachmentPointWithRotationBuffer, new Point(rotatedImage.getWidth()/2, rotatedImage.getHeight()/2), rotation);

		return new Sprite(rotatedAttachemntPoint.getX(), rotatedAttachemntPoint.getY(), rotatedImage);
	}

	private static BufferedImage addRotationBuffer(final BufferedImage image) {
		final double coefficient = 1.2;

		final int largest = (image.getWidth() > image.getHeight()) ? image.getWidth() : image.getHeight();

		final int newWidth = (int)(largest * coefficient);
		final int newHeight = (int)(largest * coefficient);

		final BufferedImage enlarged = new BufferedImage(newWidth, newHeight, image.getType());

		final Graphics2D g = enlarged.createGraphics();
		g.translate((newWidth - image.getWidth())/2, (newHeight - image.getHeight())/2);

		g.drawImage(image, null, 0, 0);

		g.dispose();

		return enlarged;
	}

	private static Point accountForRotationBuffer(final Point point, final BufferedImage startImage, final BufferedImage enlargedImage) {
		final int widthCoefficient = (enlargedImage.getWidth() - startImage.getWidth())/2;
		final int heightCoefficient = (enlargedImage.getHeight() - startImage.getHeight())/2;

		return new Point(point.getX() + widthCoefficient, point.getY() + heightCoefficient);
	}

	private static BufferedImage rotateImage(final BufferedImage image, final int degrees) {
		final int width = image.getWidth();
		final int height = image.getHeight();

		final double radians = Math.toRadians(degrees);

		final BufferedImage rotated = new BufferedImage(width, height, image.getType());

		final Graphics2D g = rotated.createGraphics();

		g.rotate(radians, width/2, height/2);
		g.drawImage(image, null, 0, 0);

		return rotated;
	}

	private static Point _rotatePoint(final Point point, final Point pivot, final int degrees) {
		final double sin = Math.sin(Math.toRadians(degrees));
		final double cos = Math.cos(Math.toRadians(degrees));

		final int tX = point.getX() - pivot.getX();
		final int tY = point.getY() - pivot.getY();

		final double newX = tX * cos - tY * sin;
		final double newY = tX * sin + tY * cos;

		final double x = newX + pivot.getX();
		final double y = newY + pivot.getY();

		return new Point((int)Math.round(x), (int)Math.round(y));
	}
}
