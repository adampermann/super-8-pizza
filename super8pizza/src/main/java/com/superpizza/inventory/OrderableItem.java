package com.superpizza.inventory;

/**
 * Created by Kevin on 10/29/2016.
 */
public abstract class OrderableItem
{
	protected String name;
	private Double price;
	protected String id;

	public OrderableItem() {}
	public OrderableItem(String id, String name, Double price)
	{
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Double getPrice()
	{
		return price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
