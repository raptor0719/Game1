package raptor.engine.test.main;

import java.awt.Image;
import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import raptor.modelLibrary.model.AttachableWiredModel;
import raptor.modelLibrary.model.FrameTiming;
import raptor.modelLibrary.model.WiredModelData;
import raptor.modelLibrary.model.animation.Animation;
import raptor.modelLibrary.model.animation.frame.Sprite;
import raptor.modelLibrary.model.animation.frame.WiredFrame;
import raptor.modelLibrary.model.util.point.IRotatedPoint;
import raptor.modelLibrary.model.util.point.Point;

public class TestModelFactory {
	public static AttachableWiredModel getModel1(final IRotatedPoint position) {
		final Point pos00 = new Point(0, 0, 0);
		final Point pos01 = new Point(0, 2000, 0);
		final Point pos02 = new Point(2000, 0, 0);
		final Point pos03 = new Point(2000, 2000, 0);

		final Point pos10 = new Point(0, 0, 0);
		final Point pos11 = new Point(0, 1500, 0);
		final Point pos12 = new Point(1500, 0, 0);
		final Point pos13 = new Point(1500, 1500, 0);

		final Point pos20 = new Point(0, 0, 0);
		final Point pos21 = new Point(0, 1000, 0);
		final Point pos22 = new Point(1000, 0, 0);
		final Point pos23 = new Point(1000, 1000, 0);

		final Point pos30 = new Point(0, 0, 0);
		final Point pos31 = new Point(0, 500, 0);
		final Point pos32 = new Point(500, 0, 0);
		final Point pos33 = new Point(500, 500, 0);

		final Point pos40 = new Point(0, 0, 0);
		final Point pos41 = new Point(0, 0, 0);
		final Point pos42 = new Point(0, 0, 0);
		final Point pos43 = new Point(0, 0, 0);

		final WiredFrame frame00 = new WiredFrame(emptySprite(), new IRotatedPoint[]{pos00, pos01, pos02, pos03});
		final WiredFrame frame01 = new WiredFrame(emptySprite(), new IRotatedPoint[]{pos10, pos11, pos12, pos13});
		final WiredFrame frame02 = new WiredFrame(emptySprite(), new IRotatedPoint[]{pos20, pos21, pos22, pos23});
		final WiredFrame frame03 = new WiredFrame(emptySprite(), new IRotatedPoint[]{pos30, pos31, pos32, pos33});
		final WiredFrame frame04 = new WiredFrame(emptySprite(), new IRotatedPoint[]{pos40, pos41, pos42, pos43});

		final FrameTiming frame00Timing = new FrameTiming(2, 0);
		final FrameTiming frame01Timing = new FrameTiming(1, 1);
		final FrameTiming frame02Timing = new FrameTiming(1, 2);
		final FrameTiming frame03Timing = new FrameTiming(1, 3);
		final FrameTiming frame04Timing = new FrameTiming(2, 4);

		final List<WiredFrame> direction0anim0 = new ArrayList<>();
		direction0anim0.add(frame00);
		direction0anim0.add(frame01);
		direction0anim0.add(frame02);
		direction0anim0.add(frame03);
		direction0anim0.add(frame04);

		final List<AbstractList<WiredFrame>> anim0Frames = new ArrayList<>();
		anim0Frames.add((AbstractList<WiredFrame>)direction0anim0);

		final List<FrameTiming> anim0Timings = new ArrayList<>();
		anim0Timings.add(frame00Timing);
		anim0Timings.add(frame01Timing);
		anim0Timings.add(frame02Timing);
		anim0Timings.add(frame03Timing);
		anim0Timings.add(frame04Timing);

		final Animation<WiredFrame> anim0 = new Animation<WiredFrame>((AbstractList<AbstractList<WiredFrame>>)anim0Frames, (AbstractList<FrameTiming>)anim0Timings, 100);

		final AbstractList<Animation<WiredFrame>> animations = new ArrayList<>(1);
		animations.add(anim0);

		final WiredModelData data = new WiredModelData(animations, 0, 1, 1000, 1000, 4);

		final AttachableWiredModel model = new AttachableWiredModel(data, position);
		model.attachSprite(0, getRectangle(10, 10));
		model.attachSprite(1, getRectangle(10, 10));
		model.attachSprite(2, getRectangle(10, 10));
		model.attachSprite(3, getRectangle(10, 10));

		return model;
	}

//	public static Model getModel2(final IPointReader position) {
//		final Hardpoint pos0_head = new Hardpoint(2400, 0, 0);
//		final Hardpoint pos0_neck = new Hardpoint(2500, 2000, 0);
//		final Hardpoint pos0_shoulderLeft = new Hardpoint(1100, 2000, 0);
//		final Hardpoint pos0_elbowLeft = new Hardpoint(1200, 4900, 0);
//		final Hardpoint pos0_handLeft = new Hardpoint(1800, 7500, 0);
//		final Hardpoint pos0_shoulderRight = new Hardpoint(3900, 2000, 0);
//		final Hardpoint pos0_elbowRight = new Hardpoint(3500, 4400, 0);
//		final Hardpoint pos0_handRight = new Hardpoint(4800, 7000, 0);
//		final Hardpoint pos0_chest = new Hardpoint(2500, 5000, 0);
//		final Hardpoint pos0_waist = new Hardpoint(2500, 8000, 0);
//		final Hardpoint pos0_thighLeft = new Hardpoint(1700, 10000, 0);
//		final Hardpoint pos0_kneeLeft = new Hardpoint(1600, 12500, 0);
//		final Hardpoint pos0_footLeft = new Hardpoint(1600, 15000, 0);
//		final Hardpoint pos0_thighRight = new Hardpoint(4400, 10000, 0);
//		final Hardpoint pos0_kneeRight = new Hardpoint(5300, 11000, 0);
//		final Hardpoint pos0_footRight = new Hardpoint(5500, 13000, 0);
//
//		final Hardpoint pos1_head = new Hardpoint(2600, 0, 0);
//		final Hardpoint pos1_neck = new Hardpoint(2500, 2000, 0);
//		final Hardpoint pos1_shoulderLeft = new Hardpoint(1000, 2000, 0);
//		final Hardpoint pos1_elbowLeft = new Hardpoint(500, 5000, 0);
//		final Hardpoint pos1_handLeft = new Hardpoint(800, 8000, 0);
//		final Hardpoint pos1_shoulderRight = new Hardpoint(4000, 2000, 0);
//		final Hardpoint pos1_elbowRight = new Hardpoint(4500, 4500, 0);
//		final Hardpoint pos1_handRight = new Hardpoint(5000, 7500, 0);
//		final Hardpoint pos1_chest = new Hardpoint(2500, 5000, 0);
//		final Hardpoint pos1_waist = new Hardpoint(2500, 8000, 0);
//		final Hardpoint pos1_thighLeft = new Hardpoint(1800, 10000, 0);
//		final Hardpoint pos1_kneeLeft = new Hardpoint(2200, 11000, 0);
//		final Hardpoint pos1_footLeft = new Hardpoint(2200, 13000, 0);
//		final Hardpoint pos1_thighRight = new Hardpoint(4300, 10000, 0);
//		final Hardpoint pos1_kneeRight = new Hardpoint(4500, 12500, 0);
//		final Hardpoint pos1_footRight = new Hardpoint(4700, 15000, 0);
//
//		final Frame frame00 = new Frame(null, new IHardpointPosition[]{
//				pos0_head, pos0_neck,
//				pos0_shoulderLeft, pos0_elbowLeft, pos0_handLeft,
//				pos0_shoulderRight, pos0_elbowRight, pos0_handRight,
//				pos0_chest, pos0_waist,
//				pos0_thighLeft, pos0_kneeLeft, pos0_footLeft,
//				pos0_thighRight, pos0_kneeRight, pos0_footRight});
//
//		final Frame frame01 = new Frame(null, new IHardpointPosition[]{
//				pos1_head, pos1_neck,
//				pos1_shoulderLeft, pos1_elbowLeft, pos1_handLeft,
//				pos1_shoulderRight, pos1_elbowRight, pos1_handRight,
//				pos1_chest, pos1_waist,
//				pos1_thighLeft, pos1_kneeLeft, pos1_footLeft,
//				pos1_thighRight, pos1_kneeRight, pos1_footRight});
//
//		final FrameTiming frame00Timing = new FrameTiming(1, 0);
//		final FrameTiming frame01Timing = new FrameTiming(1, 1);
//
//		final List<Frame> direction0anim0 = new ArrayList<>();
//		direction0anim0.add(frame00);
//		direction0anim0.add(frame01);
//
//		final List<List<Frame>> anim0Frames = new ArrayList<>();
//		anim0Frames.add(direction0anim0);
//
//		final List<FrameTiming> anim0Timings = new ArrayList<>();
//		anim0Timings.add(frame00Timing);
//		anim0Timings.add(frame01Timing);
//
//		final Animation anim0 = new Animation(anim0Frames, anim0Timings, 20);
//
//		final Map<Integer, Animation> animations = new HashMap<Integer, Animation>();
//		animations.put(0, anim0);
//
//		final Sprite sprite0 = new Sprite(10, 10);
//		final Sprite sprite1 = new Sprite(10, 10);
//		final Sprite sprite2 = new Sprite(10, 10);
//		final Sprite sprite3 = new Sprite(10, 10);
//		final Sprite sprite4 = new Sprite(10, 10);
//		final Sprite sprite5 = new Sprite(10, 10);
//		final Sprite sprite6 = new Sprite(10, 10);
//		final Sprite sprite7 = new Sprite(10, 10);
//		final Sprite sprite8 = new Sprite(10, 10);
//		final Sprite sprite9 = new Sprite(10, 10);
//		final Sprite sprite10 = new Sprite(10, 10);
//		final Sprite sprite11 = new Sprite(10, 10);
//		final Sprite sprite12 = new Sprite(10, 10);
//		final Sprite sprite13 = new Sprite(10, 10);
//		final Sprite sprite14 = new Sprite(10, 10);
//		final Sprite sprite15 = new Sprite(10, 10);
//
//		final List<Sprite> sprites = new ArrayList<>(4);
//		sprites.add(sprite0);
//		sprites.add(sprite1);
//		sprites.add(sprite2);
//		sprites.add(sprite3);
//		sprites.add(sprite4);
//		sprites.add(sprite5);
//		sprites.add(sprite6);
//		sprites.add(sprite7);
//		sprites.add(sprite8);
//		sprites.add(sprite9);
//		sprites.add(sprite10);
//		sprites.add(sprite11);
//		sprites.add(sprite12);
//		sprites.add(sprite13);
//		sprites.add(sprite14);
//		sprites.add(sprite15);
//
//		final ModelData data = new ModelData(animations, sprites, frame00);
//
//		return new Model(data, position, 0);
//	}

	private static Sprite emptySprite() {
		return new Sprite();
	}

	private static Sprite getRectangle(final int width, final int height) {
		final Image newImage;
		try {
			newImage = ImageIO.read(new File("box.png"));
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		return new Sprite(newImage);
	}
}
