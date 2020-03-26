package com.company;

import java.io.Serializable;

public class GuestEntity implements Serializable
{
	private Integer guestID;
	private String name;
	private String creditCardNum;
	private String address;
	private String country;
	private Character gender;
	private String identityNo;
	private String nationality;
	private String contactNo;

	public GuestEntity()
	{
	}

	public GuestEntity(Integer guestID, String name, String address, String country, Character gender, String identityNo, String nationality, String contactNo, String creditCardNum)
	{
		this.guestID = guestID;
		this.name = name;
		this.creditCardNum = creditCardNum;
		this.address = address;
		this.country = country;
		this.gender = gender;
		this.identityNo = identityNo;
		this.nationality = nationality;
		this.contactNo = contactNo;
	}

	@Override
	public String toString()
	{
		return "[Guest ID]   : " + guestID +
				"\nFull Name    : " + name +
				"\nAddress      : " + address +
				"\nCountry      : " + country +
				"\nGender       : " + gender +
				"\nIdentity No. : " + identityNo +
				"\nNationality  : " + nationality +
				"\nContact No.  : " + contactNo +
				"\nCredit Card  : " + creditCardNum +
				"\n";
	}

	public static GuestEntity copyGuest(GuestEntity guest)
	{
		return new GuestEntity(
				guest.getGuestID(),
				guest.getName(),
				guest.getAddress(),
				guest.getCountry(),
				guest.getGender(),
				guest.getIdentityNo(),
				guest.getNationality(),
				guest.getContactNo(),
				guest.getCreditCardNum());
	}


	public Integer getGuestID()
	{
		return guestID;
	}

	public void setGuestID(Integer guestID)
	{
		this.guestID = guestID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCreditCardNum()
	{
		return creditCardNum;
	}

	public void setCreditCardNum(String creditCardNum)
	{
		this.creditCardNum = creditCardNum;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public Character getGender()
	{
		return gender;
	}

	public void setGender(Character gender)
	{
		this.gender = gender;
	}

	public String getIdentityNo()
	{
		return identityNo;
	}

	public void setIdentityNo(String identityNo)
	{
		this.identityNo = identityNo;
	}

	public String getNationality()
	{
		return nationality;
	}

	public void setNationality(String nationality)
	{
		this.nationality = nationality;
	}

	public String getContactNo()
	{
		return contactNo;
	}

	public void setContactNo(String contactNo)
	{
		this.contactNo = contactNo;
	}
}
