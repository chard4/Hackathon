package com.scrubby.fishpond;

public class Speed 
{
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_LEFT  = -1;
	public static final int DIRECTION_UP    = -1;
	public static final int DIRECTION_DOWN  = 1;
	
	private double xSpeed = 1;
	private double ySpeed = 1;
	
	private int xDir = DIRECTION_RIGHT;
	private int yDir = DIRECTION_UP;
	
	public Speed(double xSpeed, double ySpeed)
	{
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

	public void setDirectionTowards(Location current, Location to)
	{
		int x0 = current.getX();
		int y0 = current.getY();
		
		int x1 = to.getX();
		int y1 = to.getY();
		
		if (x1 > x0)//have to go right
		{
			xDir = DIRECTION_RIGHT;
		}
		else//have to go left
		{
			xDir = DIRECTION_LEFT;
		}
		if (y1 > y0)//have to go down
		{
			yDir = DIRECTION_DOWN;
		}
		else//have to go up
		{
			yDir = DIRECTION_UP;
		}
	}
	
	/**
	 * @return the xSpeed
	 */
	public double getxSpeed() {
		return xSpeed;
	}

	/**
	 * @param xSpeed the xSpeed to set
	 */
	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	/**
	 * @return the ySpeed
	 */
	public double getySpeed() {
		return ySpeed;
	}

	/**
	 * @param ySpeed the ySpeed to set
	 */
	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}

	/**
	 * @return the xDir
	 */
	public int getxDir() {
		return xDir;
	}

	/**
	 * @param xDir the xDir to set
	 */
	public void setxDir(int xDir) {
		this.xDir = xDir;
	}

	/**
	 * @return the yDir
	 */
	public int getyDir() {
		return yDir;
	}

	/**
	 * @param yDir the yDir to set
	 */
	public void setyDir(int yDir) {
		this.yDir = yDir;
	}
	
	public void switchXDirection()
	{
		this.xDir *= -1;
	}
	
	public void switchYDirection()
	{
		this.yDir *= -1;
	}
}