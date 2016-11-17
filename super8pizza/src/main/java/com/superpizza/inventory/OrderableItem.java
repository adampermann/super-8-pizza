package com.superpizza.inventory;

public abstract class OrderableItem
{
	public String name;
	public Double price;
	public String id;
	public String imageURL;
	public Integer quantity = 1;

	public OrderableItem() {}
	public OrderableItem(String id, String name, Double price, String imageURL)
	{
		this.id = id;
		this.name = name;
		this.price = price;
		this.imageURL = imageURL;
	}
}
