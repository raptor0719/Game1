package raptor.engine.test.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import raptor.modelLibrary.model.Animation;
import raptor.modelLibrary.model.Frame;
import raptor.modelLibrary.model.FrameTiming;
import raptor.modelLibrary.model.Hardpoint;
import raptor.modelLibrary.model.IHardpointPosition;
import raptor.modelLibrary.model.Model;
import raptor.modelLibrary.model.ModelData;
import raptor.modelLibrary.model.point.IPointReader;

public class TestModelFactory {
	public static Model getModel1(final IPointReader position) {
		final Hardpoint pos00 = new Hardpoint(0, 0, 0);
		final Hardpoint pos01 = new Hardpoint(0, 20, 0);
		final Hardpoint pos02 = new Hardpoint(20, 0, 0);
		final Hardpoint pos03 = new Hardpoint(20, 20, 0);

		final Hardpoint pos10 = new Hardpoint(0, 0, 0);
		final Hardpoint pos11 = new Hardpoint(0, 15, 0);
		final Hardpoint pos12 = new Hardpoint(15, 0, 0);
		final Hardpoint pos13 = new Hardpoint(15, 15, 0);

		final Hardpoint pos20 = new Hardpoint(0, 0, 0);
		final Hardpoint pos21 = new Hardpoint(0, 10, 0);
		final Hardpoint pos22 = new Hardpoint(10, 0, 0);
		final Hardpoint pos23 = new Hardpoint(10, 10, 0);

		final Hardpoint pos30 = new Hardpoint(0, 0, 0);
		final Hardpoint pos31 = new Hardpoint(0, 5, 0);
		final Hardpoint pos32 = new Hardpoint(5, 0, 0);
		final Hardpoint pos33 = new Hardpoint(5, 5, 0);

		final Hardpoint pos40 = new Hardpoint(0, 0, 0);
		final Hardpoint pos41 = new Hardpoint(0, 0, 0);
		final Hardpoint pos42 = new Hardpoint(0, 0, 0);
		final Hardpoint pos43 = new Hardpoint(0, 0, 0);

		final Frame frame00 = new Frame(null, new IHardpointPosition[]{pos00, pos01, pos02, pos03});
		final Frame frame01 = new Frame(null, new IHardpointPosition[]{pos10, pos11, pos12, pos13});
		final Frame frame02 = new Frame(null, new IHardpointPosition[]{pos20, pos21, pos22, pos23});
		final Frame frame03 = new Frame(null, new IHardpointPosition[]{pos30, pos31, pos32, pos33});
		final Frame frame04 = new Frame(null, new IHardpointPosition[]{pos40, pos41, pos42, pos43});

		final FrameTiming frame00Timing = new FrameTiming(2, 0);
		final FrameTiming frame01Timing = new FrameTiming(1, 1);
		final FrameTiming frame02Timing = new FrameTiming(1, 2);
		final FrameTiming frame03Timing = new FrameTiming(1, 3);
		final FrameTiming frame04Timing = new FrameTiming(2, 4);

		final List<Frame> direction0anim0 = new ArrayList<>();
		direction0anim0.add(frame00);
		direction0anim0.add(frame01);
		direction0anim0.add(frame02);
		direction0anim0.add(frame03);
		direction0anim0.add(frame04);

		final List<List<Frame>> anim0Frames = new ArrayList<>();
		anim0Frames.add(direction0anim0);

		final List<FrameTiming> anim0Timings = new ArrayList<>();
		anim0Timings.add(frame00Timing);
		anim0Timings.add(frame01Timing);
		anim0Timings.add(frame02Timing);
		anim0Timings.add(frame03Timing);
		anim0Timings.add(frame04Timing);

		final Animation anim0 = new Animation(anim0Frames, anim0Timings, 100);

		final Map<Integer, Animation> animations = new HashMap<Integer, Animation>();
		animations.put(0, anim0);

		final ModelData data = new ModelData(animations, frame00);

		return new Model(data, position, 0);
	}

	public static Model getModel2(final IPointReader position) {
		final Hardpoint pos0_head = new Hardpoint(25, 0, 0);
		final Hardpoint pos0_neck = new Hardpoint(25, 20, 0);
		final Hardpoint pos0_shoulderLeft = new Hardpoint(10, 20, 0);
		final Hardpoint pos0_elbowLeft = new Hardpoint(5, 50, 0);
		final Hardpoint pos0_handLeft = new Hardpoint(8, 80, 0);
		final Hardpoint pos0_shoulderRight = new Hardpoint(40, 20, 0);
		final Hardpoint pos0_elbowRight = new Hardpoint(45, 45, 0);
		final Hardpoint pos0_handRight = new Hardpoint(50, 75, 0);
		final Hardpoint pos0_chest = new Hardpoint(25, 50, 0);
		final Hardpoint pos0_waist = new Hardpoint(25, 80, 0);
		final Hardpoint pos0_thighLeft = new Hardpoint(15, 100, 0);
		final Hardpoint pos0_kneeLeft = new Hardpoint(12, 125, 0);
		final Hardpoint pos0_footLeft = new Hardpoint(12, 150, 0);
		final Hardpoint pos0_thighRight = new Hardpoint(35, 100, 0);
		final Hardpoint pos0_kneeRight = new Hardpoint(40, 125, 0);
		final Hardpoint pos0_footRight = new Hardpoint(50, 150, 0);

		final Frame frame00 = new Frame(null, new IHardpointPosition[]{
				pos0_head, pos0_neck,
				pos0_shoulderLeft, pos0_elbowLeft, pos0_handLeft,
				pos0_shoulderRight, pos0_elbowRight, pos0_handRight,
				pos0_chest, pos0_waist,
				pos0_thighLeft, pos0_kneeLeft, pos0_footLeft,
				pos0_thighRight, pos0_kneeRight, pos0_footRight});

		final FrameTiming frame00Timing = new FrameTiming(1, 0);

		final List<Frame> direction0anim0 = new ArrayList<>();
		direction0anim0.add(frame00);

		final List<List<Frame>> anim0Frames = new ArrayList<>();
		anim0Frames.add(direction0anim0);

		final List<FrameTiming> anim0Timings = new ArrayList<>();
		anim0Timings.add(frame00Timing);

		final Animation anim0 = new Animation(anim0Frames, anim0Timings, 100);

		final Map<Integer, Animation> animations = new HashMap<Integer, Animation>();
		animations.put(0, anim0);

		final ModelData data = new ModelData(animations, frame00);

		return new Model(data, position, 0);
	}
}
