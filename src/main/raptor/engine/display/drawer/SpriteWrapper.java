package raptor.engine.display.drawer;

import java.awt.Image;

import raptor.engine.display.api.IDrawable;
import raptor.modelLibrary.model.util.point.IRotatedPoint;

//TODO: THIS IS TEMPORARY
public class SpriteWrapper implements IDrawable {
	private Image image;
	private IRotatedPoint pos;

	public SpriteWrapper(final Image image, final IRotatedPoint pos) {
		this.image = image;
		this.pos = pos;
	}

	@Override
	public boolean doDraw() {
		return true;
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public IRotatedPoint getPosition() {
		return pos;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
