package com.example.stores;

/**
 * Class sets and gets message info
 * 
 *
 */
public class MessagerStore {
	String messager;
	String name;
	String photo;
	
	public void setPhoto(String p)
	{
		photo = p;
	}
	
	public String getPhoto()
	{
		return photo;
	}
	
	public void setMessager(String m)
	{
		System.out.println("messager" + messager);
		System.out.println("m" + m);
		messager = m;
	}
	
	public String getMessager()
	{
		return messager;
	}
	
	public void setName(String n)
	{
		name = n;
	}
	
	public String getName()
	{
		return name;
	}

}
