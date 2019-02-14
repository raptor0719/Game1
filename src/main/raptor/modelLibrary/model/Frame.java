package raptor.modelLibrary.model;

import java.awt.Image;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Frame {
	private final Image image;
	private final List<IHardpointPosition> pointPositions;

	public Frame(final Image image, final IHardpointPosition[] pointPositions) {
		this.image = image;
		this.pointPositions = Collections.unmodifiableList(Arrays.asList(pointPositions));
	}

	public Image getImage() {
		return image;
	}

	public List<IHardpointPosition> getHardpointPositions() {
		return pointPositions;
	}
}
