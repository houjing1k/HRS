package com.company;

import java.util.ArrayList;

public class DisplayRooms
{
	private static final int NUM_OF_FLOOR = 6;
	private static final int ROOMS_PER_FLOOR = 8;

	public DisplayRooms()
	{
	}

	public static void showAll()
	{
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

		ArrayList<String> r1 = new ArrayList<String>();

		for (int i = 0; i < NUM_OF_FLOOR; i++)
		{
			list.add(new ArrayList<String>());
		}

		int j = 2;
		for (ArrayList<String> rows : list)
		{
			for (int i = 0; i < ROOMS_PER_FLOOR; i++)
			{
				rows.add("╔══ 0" + j + "-0" + (i + 1) + " ══╗");
			}
			j++;
		}
		for (ArrayList<String> rows : list)
		{
			for (int i = 0; i < 8; i++)
			{
				System.out.print(rows.get(i));
			}
			System.out.println();
		}
	}
}
