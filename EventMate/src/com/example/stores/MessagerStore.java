package com.example.stores;

public class MessagerStore {
	String messager;
	String name;
	
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
