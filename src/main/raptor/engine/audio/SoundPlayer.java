package raptor.engine.audio;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import raptor.engine.util.IIdProvider;
import raptor.engine.util.IdProvider;

public class SoundPlayer {
	private final IIdProvider soundIdProvider;
	private final Map<Long, BlockingLineListener> idedSounds;

	public SoundPlayer() {
		this.soundIdProvider = new IdProvider();
		this.idedSounds = new HashMap<>();
	}

	public void stopSound(final long id) {
		final BlockingLineListener listener = idedSounds.get(id);

		if (listener == null)
			return;

		listener.stop();
		soundIdProvider.free(id);
	}

	public void stopAll() {
		for (final Map.Entry<Long, BlockingLineListener> e : idedSounds.entrySet())
			e.getValue().stop();
		soundIdProvider.freeAll();
	}

	public void playSound(final InputStream audio) {
		final BlockingLineListener listener = new BlockingLineListener();
		final Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					play(audio, listener);
				} catch (Exception e) {
					System.err.println("Encountered error when attempting to play sound...");
					e.printStackTrace();
				}
			}
		};

		thread.start();
	}

	public long playSoundWithId(final InputStream audio) {
		final long id = soundIdProvider.get();
		final BlockingLineListener listener = new BlockingLineListener();
		final Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					play(audio, listener);
				} catch (Exception e) {
					System.err.println("Encountered error when attempting to play sound...");
					e.printStackTrace();
				} finally {
					soundIdProvider.free(id);
				}
			}
		};

		thread.start();

		idedSounds.put(id, listener);
		return id;
	}

	public void playSound(final AudioFormat format, final byte[] data) {
		final BlockingLineListener listener = new BlockingLineListener();
		final Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					play(format, data, listener);
				} catch (Exception e) {
					System.err.println("Encountered error when attempting to play sound...");
					e.printStackTrace();
				}
			}
		};

		thread.start();
	}

	public long playSoundWithId(final AudioFormat format, final byte[] data) {
		final long id = soundIdProvider.get();
		final BlockingLineListener listener = new BlockingLineListener();
		final Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					play(format, data, listener);
				} catch (Exception e) {
					System.err.println("Encountered error when attempting to play sound...");
					e.printStackTrace();
				} finally {
					soundIdProvider.free(id);
				}
			}
		};

		thread.start();

		idedSounds.put(id, listener);
		return id;
	}

	private void play(final InputStream audio, final BlockingLineListener listener) throws UnsupportedAudioFileException,IOException,LineUnavailableException {
		final AudioInputStream input = AudioSystem.getAudioInputStream(audio);
		final Clip clip = AudioSystem.getClip();
		clip.open(input);

		playClip(clip, listener);
	}

	private void play(AudioFormat format, byte[] data, final BlockingLineListener listener) throws LineUnavailableException {
		final Clip clip = AudioSystem.getClip();
		clip.open(format, data, 0, data.length);

		playClip(clip, listener);
	}

	private void playClip(final Clip clip, final BlockingLineListener listener) {
		clip.start();

		clip.addLineListener(listener);
		while(!listener.stopped()) {
			// FIXME: This seems really dumb to have to do this but without it execution will not continue past this loop.
			// Presumably this is due to optimizations in the runtime or the while loop was blocking the sentinel value
			// from being updated. I don't know which but this works good enough for my purposes.
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
		}

		clip.close();
	}

	private class BlockingLineListener implements LineListener {
		boolean stopped = false;

		@Override
		public void update(final LineEvent event) {
			if (event.getType().equals(LineEvent.Type.STOP)) {
				stopped = true;
			}
		}

		public boolean stopped() {
			return stopped;
		}

		public void stop() {
			stopped = true;
		}
	}
}
