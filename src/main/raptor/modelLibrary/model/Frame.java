package raptor.modelLibrary.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import raptor.engine.display.api.IDrawable;

public class Frame {
	private final IDrawable image;
	private final List<IHardpointPosition> pointPositions;

	public Frame(final IDrawable image, final IHardpointPosition[] pointPositions) {
		this.image = image;
		this.pointPositions = Collections.unmodifiableList(Arrays.asList(pointPositions));
	}

	public IDrawable getImage() {
		return image;
	}

	public List<IHardpointPosition> getHardpointPositions() {
		return pointPositions;
	}
}
