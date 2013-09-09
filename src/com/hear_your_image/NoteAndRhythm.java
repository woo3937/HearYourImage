package com.hear_your_image;

public class NoteAndRhythm {
	public static final int MAX_NOTE = 53;
	public static final int DO = 24;

	// midi���ָ��ٰ׼���ֵ����52������0��ʾ��ֹ����
	public static final int[] note = new int[] { 0, 21, 23, 24, 26, 28, 29, 31,
			33, 35, 36, 38, 40, 41, 43, 45, 47, 48, 50, 52, 53, 55, 57, 59, 60,
			62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84, 86, 88, 89,
			91, 93, 95, 96, 98, 100, 101, 103, 105, 107, 108 };

	// midi����ʱֵ����λΪ1/4��
	public static final int[] rhythm = new int[] { 0, 1, 2, 3, 4, 6, 8, 12, 16 };

	// midi��ͬ���Ķ�Ӧ������ʱ��
	public static final int[] playtime = new int[] { 0, 108, 216, 324, 432,
			648, 864, 1296, 1728 };

	// midi��ͬ���Ķ�Ӧ����Ϣʱ��
	public static final int[] pausetime = new int[] { 0, 12, 24, 36, 48, 72,
			96, 144, 192 };
}
