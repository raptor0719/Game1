package raptor.engine.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WireModelReadWrite {
	private static final byte[] MAGIC_NUMBER = new byte[] {'m', 'o', 'd', 'e', 'l'};

	public static WireModel read(final InputStream stream) throws IOException {
		final DataInputStream dis = new DataInputStream(stream);

		final byte[] magicNumber = new byte[5];
		dis.read(magicNumber);

		if (!Arrays.equals(magicNumber, MAGIC_NUMBER))
			throw new IOException("Was not a WireModel format.");

		final int hardpointCount = dis.readInt();
		final int trueFrameCount = dis.readInt();

		final List<WireModelFrame> frameList = readFrameList(trueFrameCount, hardpointCount, dis);

		final WireModelFrame defaultFrame = readFrame(hardpointCount, dis);

		final int logicalFrameCount = dis.readInt();
		final int directionCount = dis.readInt();

		final int[][] directionMappings = readDirectionMappings(directionCount, logicalFrameCount, dis);

		final int animationCount = dis.readInt();

		final List<WireModelAnimationDescriptor> animations = readAnimations(animationCount, dis);

		return new WireModel(frameList, directionMappings, animations, defaultFrame);
	}

	private static List<WireModelFrame> readFrameList(final int trueFrameCount, final int hardpointCount, final DataInputStream dis) throws IOException {
		final List<WireModelFrame> frameList = new ArrayList<>();

		for (int i = 0; i < trueFrameCount; i++)
			frameList.add(readFrame(hardpointCount, dis));

		return frameList;
	}

	private static WireModelFrame readFrame(final int hardpointCount, final DataInputStream dis) throws IOException {
		final HardpointPosition[] hardpoints = new HardpointPosition[hardpointCount];

		for (int i = 0; i < hardpointCount; i++) {
			final int x = dis.readInt();
			final int y = dis.readInt();
			final int rotation = dis.readInt();
			final int depth = dis.readInt();

			hardpoints[i] = new Hardpoint(x, y, rotation, depth);
		}

		return new WireModelFrame(hardpoints);
	}

	private static int[][] readDirectionMappings(final int directionCount, final int logicalFrameCount, final DataInputStream dis) throws IOException {
		final int[][] directionMappings = new int[directionCount][logicalFrameCount];

		for (int i = 0; i < directionCount; i++)
			for (int j = 0; j < logicalFrameCount; j++)
				directionMappings[i][j] = dis.readInt();

		return directionMappings;
	}

	private static List<WireModelAnimationDescriptor> readAnimations(final int animationCount, final DataInputStream dis) throws IOException {
		final List<WireModelAnimationDescriptor> animations = new ArrayList<>();

		for (int i = 0; i < animationCount; i++)
			animations.add(readAnimation(dis));

		return animations;
	}

	private static WireModelAnimationDescriptor readAnimation(final DataInputStream dis) throws IOException {
		final int id = dis.readInt();
		final int frameCount = dis.readInt();

		final int[] logicalFrameIndices = new int[frameCount];
		for (int i = 0; i < frameCount; i++)
			logicalFrameIndices[i] = dis.readInt();

		final int[] portions = new int[frameCount];
		for (int i = 0; i < frameCount; i++)
			portions[i] = dis.readInt();

		return new WireModelAnimationDescriptor(id, logicalFrameIndices, portions);
	}

	public static void write(final WireModel toWrite, final OutputStream stream) throws IOException {
		final DataOutputStream dos = new DataOutputStream(stream);

		dos.write(MAGIC_NUMBER);

		dos.writeInt(toWrite.hardpointCount);

		dos.writeInt(toWrite.frameList.size());
		writeFramesList(toWrite.frameList, dos);

		writeFrame(toWrite.defaultFrame, dos);

		dos.writeInt(toWrite.logicalFrameCount);
		dos.writeInt(toWrite.directionCount);
		writeDirectionMappings(toWrite.mappings, dos);

		dos.writeInt(toWrite.animations.values().size());

		writeAnimations(new ArrayList<>(toWrite.animations.values()), dos);
	}

	private static void writeFramesList(final List<WireModelFrame> frameList, final DataOutputStream dos) throws IOException {
		for (final WireModelFrame frame : frameList)
			writeFrame(frame, dos);
	}

	private static void writeFrame(final WireModelFrame model, final DataOutputStream dos) throws IOException {
		writeHardpoints(model.getHardpointPositions(), dos);
	}

	private static void writeHardpoints(final HardpointPosition[] hardpoints, final DataOutputStream dos) throws IOException {
		for (final HardpointPosition h : hardpoints)
			writeHardpoint(h, dos);
	}

	private static void writeHardpoint(final HardpointPosition hardpoint, final DataOutputStream dos) throws IOException {
		dos.writeInt(hardpoint.getX());
		dos.writeInt(hardpoint.getY());
		dos.writeInt(hardpoint.getRotation());
		dos.writeInt(hardpoint.getDepth());
	}

	private static void writeDirectionMappings(final int[][] directionMappings, final DataOutputStream dos) throws IOException {
		for (final int[] direction : directionMappings)
			for (final int index : direction)
				dos.writeInt(index);
	}

	private static void writeAnimations(final List<WireModelAnimationDescriptor> animations, final DataOutputStream dos) throws IOException {
		for (final WireModelAnimationDescriptor animation : animations)
			writeAnimation(animation, dos);
	}

	private static void writeAnimation(final WireModelAnimationDescriptor animation, final DataOutputStream dos) throws IOException {
		dos.writeInt(animation.getId());

		dos.writeInt(animation.getFrames().length);

		for (final int frameIndex : animation.getFrames())
			dos.writeInt(frameIndex);

		for (final int portion : animation.getFrameProportions())
			dos.writeInt(portion);
	}
}
