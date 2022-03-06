package raptor.engine.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import raptor.engine.util.BinaryDataTools;

public class WireModelReadWrite {
	private static final byte[] MAGIC_NUMBER = new byte[] {'w', 'i', 'r', 'e', 'm', 'o', 'd', 'e', 'l'};

	public static WireModel read(final InputStream stream) throws IOException {
		final DataInputStream dis = new DataInputStream(stream);

		final byte[] magicNumber = new byte[9];
		dis.readFully(magicNumber);

		if (!Arrays.equals(magicNumber, MAGIC_NUMBER))
			throw new IOException("Was not a WireModel format.");

		final String name = BinaryDataTools.marshalString(dis);
		final int hardpointCount = dis.readInt();
		final int frameCount = dis.readInt();

		final List<DirectionalWireModelFrame> frameList = readFrameList(frameCount, hardpointCount, dis);

		return new WireModel(name, frameList);
	}

	private static List<DirectionalWireModelFrame> readFrameList(final int frameCount, final int hardpointCount, final DataInputStream dis) throws IOException {
		final List<DirectionalWireModelFrame> frameList = new ArrayList<>();

		for (int i = 0; i < frameCount; i++)
			frameList.add(readDirectionalFrame(hardpointCount, dis));

		return frameList;
	}

	private static DirectionalWireModelFrame readDirectionalFrame(final int hardpointCount, final DataInputStream dis) throws IOException {
		final String name = BinaryDataTools.marshalString(dis);
		final Map<Direction, WireModelFrame> frames = new HashMap<>();

		for (final Direction d : Direction.values())
			frames.put(d, readFrame(hardpointCount, dis));

		return new DirectionalWireModelFrame(name, frames);
	}

	private static WireModelFrame readFrame(final int hardpointCount, final DataInputStream dis) throws IOException {
		final IHardpoint[] hardpoints = new IHardpoint[hardpointCount];

		for (int i = 0; i < hardpointCount; i++) {
			final String hardpointName = BinaryDataTools.marshalString(dis);
			final int x = dis.readInt();
			final int y = dis.readInt();
			final int rotation = dis.readInt();
			final int depth = dis.readInt();

			hardpoints[i] = new Hardpoint(hardpointName, x, y, rotation, depth);
		}

		return new WireModelFrame(hardpoints);
	}

	public static void write(final WireModel toWrite, final OutputStream stream) throws IOException {
		final DataOutputStream dos = new DataOutputStream(stream);

		dos.write(MAGIC_NUMBER);

		dos.write(BinaryDataTools.serializeString(toWrite.getName()));

		dos.writeInt(toWrite.getHardpointCount());

		dos.writeInt(toWrite.getFrameList().size());
		writeFramesList(toWrite.getFrameList(), dos);
	}

	private static void writeFramesList(final List<DirectionalWireModelFrame> frameList, final DataOutputStream dos) throws IOException {
		for (final DirectionalWireModelFrame frame : frameList)
			writeDirectionalFrame(frame, dos);
	}

	private static void writeDirectionalFrame(final DirectionalWireModelFrame frame, final DataOutputStream dos) throws IOException {
		dos.write(BinaryDataTools.serializeString(frame.getName()));

		for (final Direction d : Direction.values())
			writeFrame(frame.getFrameForDirection(d), dos);
	}

	private static void writeFrame(final WireModelFrame frame, final DataOutputStream dos) throws IOException {
		writeHardpoints(frame.getSortedHardpoints(), dos);
	}

	private static void writeHardpoints(final IHardpoint[] hardpoints, final DataOutputStream dos) throws IOException {
		for (final IHardpoint h : hardpoints)
			writeHardpoint(h, dos);
	}

	private static void writeHardpoint(final IHardpoint hardpoint, final DataOutputStream dos) throws IOException {
		dos.write(BinaryDataTools.serializeString(hardpoint.getName()));
		dos.writeInt(hardpoint.getX());
		dos.writeInt(hardpoint.getY());
		dos.writeInt(hardpoint.getRotation());
		dos.writeInt(hardpoint.getDepth());
	}
}
