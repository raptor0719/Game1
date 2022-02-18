package raptor.engine.test.main;

import java.awt.Image;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import raptor.engine.model.Hardpoint;
import raptor.engine.model.IHardpoint;
import raptor.engine.model.Model;
import raptor.engine.model.WireModel;
import raptor.engine.model.WireModelFrame;

public class TestModelFactory {
	public static Model getModel() {
		final IHardpoint pos00 = point(0, 0);
		final IHardpoint pos01 = point(0, 20);
		final IHardpoint pos02 = point(20, 0);
		final IHardpoint pos03 = point(20, 20);
		final WireModelFrame frame0 = new WireModelFrame(new IHardpoint[] {pos00, pos01, pos02, pos03});

		final IHardpoint pos10 = point(0, 0);
		final IHardpoint pos11 = point(0, 15);
		final IHardpoint pos12 = point(15, 0);
		final IHardpoint pos13 = point(15, 15);
		final WireModelFrame frame1 = new WireModelFrame(new IHardpoint[] {pos10, pos11, pos12, pos13});

		final IHardpoint pos20 = point(0, 0);
		final IHardpoint pos21 = point(0, 10);
		final IHardpoint pos22 = point(10, 0);
		final IHardpoint pos23 = point(10, 10);
		final WireModelFrame frame2 = new WireModelFrame(new IHardpoint[] {pos20, pos21, pos22, pos23});

		final IHardpoint pos30 = point(0, 0);
		final IHardpoint pos31 = point(0, 5);
		final IHardpoint pos32 = point(5, 0);
		final IHardpoint pos33 = point(5, 5);
		final WireModelFrame frame3 = new WireModelFrame(new IHardpoint[] {pos30, pos31, pos32, pos33});

		final IHardpoint pos40 = point(0, 0);
		final IHardpoint pos41 = point(0, 0);
		final IHardpoint pos42 = point(0, 0);
		final IHardpoint pos43 = point(0, 0);
		final WireModelFrame frame4 = new WireModelFrame(new IHardpoint[] {pos40, pos41, pos42, pos43});

		final List<WireModelFrame> frameList = Arrays.asList(new WireModelFrame[] {frame0, frame1, frame2, frame3, frame4});

		final int[][] directionMapping = new int[][] { {0, 1, 2, 3, 4} };

		final WireModel wireModel = new WireModel(frameList, directionMapping);

		return new Model(wireModel, null);
	}

	public static Image getRectangle(final int width, final int height) {
		Image newImage;
		try {
			newImage = ImageIO.read(new File("box.png"));
			newImage = newImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		return newImage;
	}

	private static IHardpoint point(int x, int y) {
		return new Hardpoint("", x, y, 0, 0);
	}
}
