package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.File;

public abstract class Controller {

	public <T> void toFile(T object, String fileName)
	{
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(object); // write object to ObjectOutputStream

			oos.close();
			fos.close();

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T fromFile(String fileName)
	{
		T object = null;
		try {
			FileInputStream fis = new FileInputStream(fileName);
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
	
	public <T> void replaceFile(T object,String fileName) {
		File file = new File(fileName);
		try {
			file.delete();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		this.toFile(object, fileName);
	}
	
	public abstract void processMain();
}