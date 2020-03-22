package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Controller {

	public <T> void toFile(T object, String fileDir)
	{
		try {
			FileOutputStream fos = new FileOutputStream(fileDir);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(object); // write object to ObjectOutputStream

			oos.close();
			fos.close();

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T fromFile(String fileDir)
	{
		T object = null;
		try {
			FileInputStream fis = new FileInputStream(fileDir);
			ObjectInputStream ois = new ObjectInputStream(fis);

			object = (T) ois.readObject();
			
			ois.close();
			fis.close();

		} catch(Exception ex) {
			//commented to prevent exception from printing
			//ex.printStackTrace();
		}
		return object;
	}
}
