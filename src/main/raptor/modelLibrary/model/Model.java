package raptor.modelLibrary.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import raptor.engine.display.api.IDrawable;
import raptor.modelLibrary.model.point.IPointReader;

public class Model {
	private final ModelData base;

	private final Map<Integer, Animation> animations;
	private final List<Hardpoint> hardpoints;
	private final List<Sprite> attachedSprites;

	private IDrawable currentSprite;
	private IPointReader position;
	private int direction;

	// Animation Timing Stuff
	private final Map<Integer, List<Integer>> animationTimingMap;

	private List<List<Frame>> currentFrames;
	private List<Integer> currentTimings;
	private int totalFramesRemaining = 0;
	private int countRemainingForCurrentFrame = 0;
	private int currentFrameIndex = 0;

	public Model(final ModelData base, final IPointReader position, final int initialDirection) {
		this.base = base;

		animations = base.getAnimations();
		hardpoints = new ArrayList<>(base.getHardpointCount());
		attachedSprites = new ArrayList<>(hardpoints.size());
		this.position = position;

		for (int i = 0; i < base.getHardpointCount(); i++) {
			hardpoints.add(new Hardpoint(0, 0, 0, this.position));
		}

		setHardpointPositions(base.getDefaultFrame(), hardpoints);
		currentSprite = base.getDefaultFrame().getImage();

		for (int i = 0; i < base.getHardpointCount(); i++) {
			final Sprite current = base.getDefaultSprites().get(i);
			current.setPosition(hardpoints.get(i));
			attachedSprites.add(current);
		}

		direction = initialDirection;

		animationTimingMap = buildTimingMap(animations);
	}

	public List<IDrawable> getCurrentSprites() {
		final List<IDrawable> drawables = new ArrayList<>(attachedSprites.size() + 1);

		if (currentSprite != null)
			drawables.add(currentSprite);

		for (int i = 0; i < attachedSprites.size(); i++) {
			final Sprite s = attachedSprites.get(i);
			if (s != null)
				drawables.add(s);
		}

		return drawables;
	}

	public void setSpriteAtHardpoint(final int hardpointIndex, final Sprite sprite) {
		if (hardpointIndex >= attachedSprites.size() || hardpointIndex < 0)
			return;

		sprite.setPosition(position);

		attachedSprites.set(hardpointIndex, sprite);
	}

	public void removeSpriteAtHardpoint(final int hardpointIndex) {
		if (hardpointIndex >= attachedSprites.size() || hardpointIndex < 0)
			return;

		attachedSprites.set(hardpointIndex, base.getDefaultSprites().get(hardpointIndex));
	}

	public IPointReader getPosition() {
		return position;
	}

	public void setDirection(final int newDirection) {
		direction = newDirection;
	}

	public int setAnimation(int id) {
		currentFrames = animations.get(id).getFrames();
		currentTimings = animationTimingMap.get(id);
		totalFramesRemaining = sumCounts(currentTimings);
		currentFrameIndex = 0;
		countRemainingForCurrentFrame = currentTimings.get(currentFrameIndex);

		return totalFramesRemaining;
	}

	// TODO: Consider making this auto-reset on the current animation if the number of frames is exceeded
	public int advanceFrame() {
		countRemainingForCurrentFrame--;
		totalFramesRemaining--;

		if (countRemainingForCurrentFrame == 0) {
			currentFrameIndex++;
			countRemainingForCurrentFrame = currentTimings.get(currentFrameIndex);
		}

		final Frame currentFrame = currentFrames.get(direction).get(currentFrameIndex);

		currentSprite = currentFrame.getImage();
		setHardpointPositions(currentFrame, hardpoints);

		return totalFramesRemaining;
	}

	// TODO: This can probably be optimized but this is fine for now
	public int advanceFrames(final int count) {
		for (int i = 0; i < count-1; i++)
			advanceFrame();
		return advanceFrame();
	}

	public IHardpointPosition getHardpointPosition(int id) {
		return hardpoints.get(id);
	}

	/* Helper Methods */
	private void setHardpointPositions(final Frame frame, List<Hardpoint> hardpoints) {
		for (int i = 0; i < hardpoints.size(); i++) {
			final IHardpointPosition newPos = frame.getHardpointPositions().get(i);
			final Hardpoint current = hardpoints.get(i);

			current.setX(newPos.getX());
			current.setY(newPos.getY());
			current.setRotation(newPos.getRotation());
		}
	}

	// FIXME: Most of the logic for building these frame timing lists should be abstracted
	// FIXME: Initial timing lists can be done per ModelData (instead of per model)
	private Map<Integer, List<Integer>> buildTimingMap(final Map<Integer, Animation> animations) {
		final Map<Integer, List<Integer>> timingMap = new HashMap<>();

		for (final Entry<Integer, Animation> entry : animations.entrySet())
			timingMap.put(entry.getKey(), buildTimingList(entry.getValue()));

		return timingMap;
	}

	// FIXME: The math for distributing the leftover frames is all sorts of wrong
	private List<Integer> buildTimingList(final Animation animation) {
		final int totalFrames = animation.getDefaultFrameCount();
		final int totalFrameScale = getTotalFrameScale(animation.getTimings());

		final int framesPerUnit = totalFrames / totalFrameScale;
		final int leftoverFrames = totalFrames % totalFrameScale;

		final int highestPriorityFrame = getHighestPriority(animation.getTimings());

		final List<Integer> timingList = new ArrayList<>();

		for (int i = 0; i < animation.getTimings().size(); i++) {
			final int adjust = (i == highestPriorityFrame) ? leftoverFrames : 0;
			final int frameCount = (framesPerUnit * animation.getTimings().get(0).getScale()) + adjust;

			timingList.add(frameCount);
		}

		return timingList;
	}

	private int getTotalFrameScale(final List<FrameTiming> timings) {
		int total = 0;

		for (final FrameTiming f : timings)
			total += f.getScale();

		return total;
	}

	private int getHighestPriority(final List<FrameTiming> timings) {
		int frame = 0;
		int maxPriority = Integer.MIN_VALUE;

		for (int i = 0; i < timings.size(); i++) {
			final FrameTiming f = timings.get(i);
			if (f.getPriority() > maxPriority) {
				frame = i;
				maxPriority = f.getPriority();
			}
		}

		return frame;
	}

	private int sumCounts(final List<Integer> list) {
		int total = 0;

		for (final Integer i : list)
			total += i;

		return total;
	}
}
