package com.hear_your_image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MidiGenerator {
	private final static byte[] FileHeader = (new String("MThd")).getBytes();
	private final static byte[] TrackHeader = (new String("MTrk")).getBytes();
	public static OutputStream out = null;
	public static File file = new File(
			Global.SoundRoot+"play.mid");
	private final static byte[] EndTag = new byte[] { (byte) 0x00, (byte) 0xff,
			(byte) 0x2f, (byte) 0x00 };

	public static void WriteMidi(ArrayList<MelodyCell> arrm) throws Exception {
		MelodyCell[] am = new MelodyCell[arrm.size()];
		out = new FileOutputStream(file);
		
		for(int i=0;i<am.length;i++){
			am[i]=arrm.get(i);
		}
			
		WriteFileHeader();
		WriteInfoTrack();
		WriteAccompaniment(am);
		WriteMainMidi(am);
	}

	private static void WriteFileHeader() throws Exception {
		byte[] header = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x06, (byte) 0x00, (byte) 0x01, (byte) 0x00,
				(byte) 0x03, (byte) 0x01, (byte) 0xe0 };
		for (int i = 0; i < 4; i++)
			header[i] = FileHeader[i];
		out.write(header);
	}

	private static void WriteInfoTrack() throws Exception {
		out.write(TrackHeader);
		out.write(new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x1B, (byte) 0x00 });
		out.write(new byte[] { (byte) 0xff, (byte) 0x03, (byte) 0x04 });
		out.write((new String("info")).getBytes());
		out.write(new byte[] { (byte) 0x00, (byte) 0xff, (byte) 0x58,
				(byte) 0x04, (byte) 0x04, (byte) 0x02, (byte) 0x18,
				(byte) 0x08, (byte) 0x00, (byte) 0xff, (byte) 0x51,
				(byte) 0x03, (byte) 0x07, (byte) 0xA1, (byte) 0x20 });
		out.write(EndTag);
	}

	private static void WriteMainMidi(MelodyCell[] am) throws Exception {
		WriteMelody(am, "main");
	}

	private static void WriteAccompaniment(MelodyCell[] am) throws Exception {
		int[] accMelody = new int[] { 24, 26, 28, 26, 31, 28, 26, 28 };
		MelodyCell[] acc = new MelodyCell[am.length];
		for (int i = 0; i < acc.length; i++) {
			acc[i] = new MelodyCell();
			acc[i].Melody = new int[8];
			acc[i].Rhythm = new int[8];
			for (int j = 0; j < 8; j++) {
				acc[i].Rhythm[j] = 2;
				acc[i].Melody[j] = accMelody[j];
			}
		}
		WriteMelody(acc, "Accopaniment");
	}

	private static void WriteMelody(MelodyCell[] am, String trackname)
			throws Exception {
		ArrayList<byte[]> aplay = new ArrayList<byte[]>();
		ArrayList<Integer> amusic = new ArrayList<Integer>();
		ArrayList<Integer> atime = new ArrayList<Integer>();
		for (int i = 0; i < am.length; i++) {
			for (int j = 0; j < am[i].Melody.length; j++) {
				amusic.add(NoteAndRhythm.note[am[i].Melody[j]]);
				atime.add(am[i].Rhythm[j]);
			}
		}
		out.write(TrackHeader);
		int length = 0;
		aplay.add(generateByte((byte) (amusic.get(0) & 0xff), true, 0,
				atime.get(0)));
		length += aplay.get(0).length;
		for (int i = 1; i < amusic.size(); i++) {
			aplay.add(generateByte((byte) (amusic.get(i) & 0xff), true,
					atime.get(i - 1), atime.get(i)));
			length += aplay.get(i).length;
		}
		length += 19 + trackname.length();
		out.write(((length & (0xff000000)) >> 24));
		out.write(((length & (0x00ff0000)) >> 16));
		out.write(((length & (0x0000ff00)) >> 8));
		out.write((length & (0x000000ff)));
		out.write(new byte[] { (byte) 0x00, (byte) 0xff, (byte) 0x03,
				(byte) (trackname.length() & 0xff) });
		out.write(trackname.getBytes());
		out.write(new byte[] { (byte) 0x00, (byte) 0xC0, (byte) 0x00 });// 乐器为钢琴
		out.write(new byte[] { (byte) 0x00, (byte) 0xB0, (byte) 0x0A,
				(byte) 0x40 });// 左声道右声道相同
		out.write(new byte[] { (byte) 0x00, (byte) 0xB0, (byte) 0x40,
				(byte) 0x7E });
		for (int i = 0; i < aplay.size(); i++) {
			out.write(aplay.get(i));
		}
		out.write(EndTag);
	}

	private static byte[] delta_time(int time) {
		int size = 0;
		byte[] temp = new byte[10];
		while (true) {
			temp[size++] = (byte) (0x80 | (time & 0x7f));
			if (time > 127)
				time >>= 7;
			else
				break;
		}
		temp[0] &= 0x7f;
		byte[] rslt = new byte[size];
		for (int i = 0; i < size; i++)
			rslt[i] = temp[size - 1 - i];
		return rslt;
	}

	private static byte[] generateByte(byte x, boolean first, int last, int cur) {
		byte[] playtime = delta_time(NoteAndRhythm.playtime[cur]);
		byte[] pausetime = delta_time(NoteAndRhythm.pausetime[last]);
		byte[] rslt = new byte[6 + playtime.length + pausetime.length];
		int count = 0;
		for (int i = 0; i < pausetime.length; i++)
			rslt[count++] = pausetime[i];
		rslt[count++] = (byte) 0x90;
		rslt[count++] = x;
		rslt[count++] = (byte) 0x50;

		for (int i = 0; i < playtime.length; i++)
			rslt[count++] = playtime[i];

		rslt[count++] = (byte) 0x80;
		rslt[count++] = x;
		rslt[count++] = (byte) 0x40;
		return rslt;
	}
}
