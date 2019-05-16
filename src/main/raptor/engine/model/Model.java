package raptor.engine.model;

import java.util.AbstractList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Model {
	private final WireModel wireModel;
	private final Map<Integer, SpriteModel> visuals;

	private int direction;

	private AbstractList<Integer> currentAnimation;
	private int currentFrame;

	public Model(final WireModel wireModel, final List<SpriteModelDescriptor> initialVisuals) {
		this.wireModel = wireModel;
		this.visuals = buildVisualsMap(initialVisuals);
		this.direction = 0;
		this.currentAnimation = null;
		this.currentFrame = -1;
	}

	public void setHardpointVisual(final int targetHardpoint, final SpriteModel newVisual) {
		visuals.put(targetHardpoint, newVisual);
	}

	public void setAnimation(final int animId, final int totalFrames) {
		currentAnimation = wireModel.getAnimationFrames(animId, totalFrames);
		currentFrame = 0;
	}

	public void setDirection(final int direction) {
		this.direction = direction;
	}

	public int advanceFrame() {
		if (currentFrame < 0)
			return 0;
		currentFrame++;
		return currentAnimation.size() - currentFrame - 1;
	}

	public Iterator<ModelComponent> getModelVisuals() {
		final WireModelFrame frame = wireModel.getFrame(currentAnimation.get(currentFrame), direction);
		final HardpointPosition[] positions = frame.getHardpointPositions();

		final List<ModelComponent> components = new LinkedList<>();
		for (int i = 0; i < positions.length; i++) {
			components.add(new ConcreteModelComponent(visuals.get(i).getSprite(direction), positions[i]));
		}
		return components.iterator();
	}

	public HardpointPosition getHardpointPosition(final int hardpoint) {
		return wireModel.getFrame(currentAnimation.get(currentFrame), direction).getHardpointPositions()[hardpoint];
	}

	private Map<Integer, SpriteModel> buildVisualsMap(final List<SpriteModelDescriptor> initialVisuals) {
		final Map<Integer, SpriteModel> visualsMap = new HashMap<>();
		for (final SpriteModelDescriptor s : initialVisuals)
			visualsMap.put(s.getTargetHardpoint(), s.getSpriteModel());
		return visualsMap;
	}

	private class ConcreteModelComponent implements ModelComponent {
		private final Sprite sprite;
		private final HardpointPosition pos;

		public ConcreteModelComponent(final Sprite sprite, final HardpointPosition pos) {
			this.sprite = sprite;
			this.pos = pos;
		}

		@Override
		public Sprite getSprite() {
			return sprite;
		}

		@Override
		public int getX() {
			return pos.getX();
		}

		@Override
		public int getY() {
			return pos.getY();
		}

		@Override
		public int getZ() {
			return pos.getZ();
		}

		@Override
		public int getRotation() {
			return pos.getRotation();
		}
	}
}
