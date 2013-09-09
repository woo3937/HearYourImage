package com.hear_your_image;

import java.util.Arrays;

public class MelodyCell {
	public int[] Melody;
	public int[] Rhythm;
	public MelodyCell(){
		Melody = null;
		Rhythm = null;
	}
	public MelodyCell(int size){
		Melody = new int[size];
		Rhythm = new int[size];
	}
	public boolean Validating(){
		int total = 0;
		for(int i=0;i<Melody.length;i++)
			total+=NoteAndRhythm.rhythm[Rhythm[i]];
		if(total!=16)
			return false;
		else
			return true;
	}
	
	public String toString() {
		return Arrays.toString(Melody) + "\n" + Arrays.toString(Rhythm);
	}
}
