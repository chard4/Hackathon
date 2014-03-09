package com.scrubby.fishpond;

public class Location 
{
	private int x;
	private int y;
	
	public Location(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean equals(Location loc)
	{
		return (this.getY() == loc.getY() && this.getX() == loc.getX());
	}
}