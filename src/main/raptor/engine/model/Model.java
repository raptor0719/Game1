package raptor.engine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import raptor.engine.util.IIdProvider;
import raptor.engine.util.IdProvider;

public class Model {
	private final WireModel wireModel;
	private final int hardpointCount;

	private int currentFrame;
	private Direction direction;

	// Maps a given sprite to a direction and hardpoint index
	private final DirectionalObjectManager<Sprite> spriteManager;

	private final IIdProvider attachmentIdProvider;
	private final Map<Long, Attachment> attachments;

	private final List<Sprite> drawList;

	public Model(final WireModel wireModel, Map<Direction, Sprite[]> sprites) {
		this.wireModel = wireModel;
		this.hardpointCount = wireModel.getHardpointCount();
		this.direction = Direction.NORTH;

		this.spriteManager = new DirectionalObjectManager<>(sprites);

		this.attachmentIdProvider = new IdProvider();
		this.attachments = new HashMap<>();

		this.drawList = new ArrayList<>();
	}

	public void setFrame(final int frameId) {
		currentFrame = frameId;
	}

	public void setDirection(final Direction direction) {
		this.direction = direction;
	}

	public void setSprite(final Sprite sprite, final int hardpoint, final Direction direction) {
		spriteManager.set(sprite, hardpoint, direction);
	}

	public WireModelFrame getCurrentFrame() {
		return wireModel.getFrame(currentFrame, direction);
	}

	public int getHardpointCount() {
		return hardpointCount;
	}

	public long attach(final Sprite sprite, final int hardpoint, final int depthModifier) {
		final long id = attachmentIdProvider.get();

		attachments.put(id, new Attachment(id, sprite, hardpoint, depthModifier));

		return id;
	}

	public void unattach(final long id) {
		attachments.remove(id);
		attachmentIdProvider.free(id);
	}

	private void recalculateDrawList() {
		drawList.clear();

		// FIXME: Fill this out
	}

	private static class Attachment {
		private final long id;
		private final Sprite sprite;
		private final int hardpointIndex;
		private final int depthModifier;

		public Attachment(final long id, final Sprite sprite, final int hardpointIndex, final int depthModifier) {
			this.id = id;
			this.sprite = sprite;
			this.hardpointIndex = hardpointIndex;
			this.depthModifier = depthModifier;
		}

		public long getId() {
			return id;
		}

		public Sprite getSprite() {
			return sprite;
		}

		public int getHardpointIndex() {
			return hardpointIndex;
		}

		public int getDepthModifier() {
			return depthModifier;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (id ^ (id >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Attachment other = (Attachment) obj;
			if (id != other.id)
				return false;
			return true;
		}
	}
}
