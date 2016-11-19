package com.superpizza.ordering;

public abstract class OrderableItem
{
	public String name;
	public Double price;
	public String id;
	public String imageURL;
	public Integer quantity = 1;
	public boolean enabled = true; //todo   //on order, if stock drops below threshhold may need to disable. If backend inventory change happens, may enable/disable

	public OrderableItem() {}
	public OrderableItem(String id, String name, Double price, String imageURL)
	{
		this.id = id;
		this.name = name;
		this.price = price;
		this.imageURL = imageURL;
	}
}
