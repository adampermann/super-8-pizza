package com.superpizza.inventory;

public abstract class OrderableItem
{
	protected String name;
	private Double price;
	protected String id;
	protected String imageURL;

	public OrderableItem() {}
	public OrderableItem(String id, String name, Double price, String imageURL)
	{
		this.id = id;
		this.name = name;
		this.price = price;
		this.imageURL = imageURL;
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

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
}
