package com.company;


public class DeluxeRoom extends RoomEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeluxeRoom(String roomId, RoomStatus status, boolean smoking, boolean wifi) {
		
		super(roomId, status, smoking, wifi);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomType getRoomType() {
		return RoomType.DELUXE;
	}
	
	@Override
	public BedType getBedType() {
		return BedType.KING;
	}

	
	

}
