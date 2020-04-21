package com.company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class RoomIOBoundary extends Boundary {

    Scanner scan = new Scanner(System.in);

	public String selectRoom(ArrayList<RoomEntity> rooms)
    {
        ArrayList<String> roomIds = new ArrayList<>();
        String selectedRoomId;
        for (RoomEntity roomEntity: rooms)
        {
            roomIds.add(roomEntity.getRoomId());
        }
        do {
            RoomVisualiser.showList(rooms);
            selectedRoomId = scan.next();
            boolean invalidId = true;
            for(String filteredRoomID:roomIds)
            {
                if (filteredRoomID.equals(selectedRoomId)) {
                    invalidId = false;
                    break;
                }
            }
            if(invalidId)
            {
                selectedRoomId = "";
                System.out.println("Sorry the ID you keyed in is not valid");
            }
        }while (selectedRoomId.equals(""));
        return selectedRoomId;
    }
	
	 public int selectNumAdult() {
	        System.out.println("Maximun number of guest per room is 4");
	        System.out.println("Enter Number of Adult: ");
	        return(getInput(1, 4));
	    }

	 public int selectNumChild(int numAdult) {
	        int i = 4 - numAdult;
	        if(i!=0) {
	            System.out.println(String.format("Enter Number of Child: (Max %d)", i));
	            return(getInput(0, i));
	        }else {
	            return 0;
	        }
	    }
	
}
