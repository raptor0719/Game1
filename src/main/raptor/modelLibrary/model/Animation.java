package raptor.modelLibrary.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Animation {
	private final List<List<Frame>> frames;
	private final List<FrameTiming> timings;
	private final int defaultFrameCount;

	public Animation(final List<List<Frame>> frames, final List<FrameTiming> timings, final int defaultFrameCount) {
		this.frames = createImmutableFrames(frames);
		this.timings = createImmutableTimings(timings);
		this.defaultFrameCount = defaultFrameCount;
	}

	public List<List<Frame>> getFrames() {
		return frames;
	}

	public List<FrameTiming> getTimings() {
		return timings;
	}

	public int getDefaultFrameCount() {
		return defaultFrameCount;
	}

	/* Helper Methods */
	private List<List<Frame>> createImmutableFrames(final List<List<Frame>> frames) {
		final List<List<Frame>> bufferList = new ArrayList<>();

		for (final List<Frame> l : frames)
			bufferList.add(createImmutableList(l));

		return createImmutableList(bufferList);
	}

	private List<FrameTiming> createImmutableTimings(final List<FrameTiming> timings) {
		return createImmutableList(timings);
	}

	private <T> List<T> createImmutableList(final List<T> l) {
		return Collections.unmodifiableList(l);
	}
}
