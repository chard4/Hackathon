package com.scrubby.fishpond;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.RotateDrawable;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Fish
{
	private Location loc;
	private Location dest;
	
	private boolean directed;
	private boolean idling;
	
	private Bitmap bitmap;
	
	private int speed = 4;
	
	public static final int ERROR = 3;
	
	private Pond pond;
	
	private ArrayList<Location> path;
	
	public Fish(Bitmap bitmap, int x, int y, Pond pond)
	{
		this.setBitmap(bitmap);
		this.setLoc(new Location(x, y));
		this.setDest(new Location(x, y));
		directed = false;
		idling = true;
		this.pond = pond;
		path = new ArrayList<Location>();
	}

	/**
	 * @return the idling
	 */
	public boolean isIdling() {
		return idling;
	}

	/**
	 * @param idling the idling to set
	 */
	public void setIdling(boolean idling) {
		this.idling = idling;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public Location getDest() {
		return dest;
	}

	public void setDest(Location dest) {
		this.dest = dest;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	/**
	 * @return the path
	 */
	public ArrayList<Location> getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(ArrayList<Location> path) {
		this.path = path;
	}

	/**
	 * @return the directed
	 */
	public boolean isDirected() {
		return directed;
	}

	/**
	 * @param directed the directed to set
	 */
	public void setDirected(boolean directed) {
		this.directed = directed;
	}
	
	public void draw(Canvas canvas)
	{	
		if (path.isEmpty())
		{
			if (directed)
			{
				directed = setLocationTowardsDestination();
				if (directed == false)
					idling = true;
			}
			else//going to perform idle behavior
			{
				if (dest == null)
				{
					dest.setX((int)(Math.random()*pond.getWidth()));
					dest.setY((int)(Math.random()*pond.getHeight()));
				}
				if (idling)
				{
					idling = setLocationTowardsDestination();
				}
				else
				{
					dest.setX((int)(Math.random()*pond.getWidth()));
					dest.setY((int)(Math.random()*pond.getHeight()));
					idling = true;
					System.out.println("New destination was generated");
					setLocationTowardsDestination();
				}
			}
		}
		else//you have to follow a path
		{
			if (directed)
			{
				directed = setLocationTowardsDestination();
			}
			else
			{
				dest = path.get(0);
				path.remove(0);
				directed = true;
			}
			
		}
		canvas.drawBitmap(bitmap, loc.getX() - (bitmap.getWidth() / 2), loc.getY() - (bitmap.getHeight() / 2), null);
	}
	
	public boolean setLocationTowardsDestination()
	{
		int deltaX = dest.getX() - loc.getX();
		int deltaY = dest.getY() - loc.getY();
		
		if (Math.abs(deltaX) <= ERROR && Math.abs(deltaY) <= ERROR)
		{
			return false;
		}
		else
		{
			if (deltaX == 0)
			{
				if (deltaY > 0)
				{
					loc.setY(loc.getY() + speed);
				}
				else if (deltaY < 0)
				{
					loc.setY(loc.getY() - speed);
				}
				if (deltaY < 0)
				{
					setBitmap(BitmapFactory.decodeResource(pond.getResources(), R.drawable.fish));
					System.out.println("facing up");
				}
				else
				{
					setBitmap(BitmapFactory.decodeResource(pond.getResources(), R.drawable.fish4));
					System.out.println("facing down");
				}
			}
			else
			{
				double angle = Math.atan(((1.0*deltaY)/deltaX));
				if (deltaX < 0)
					angle += Math.PI;
				int dy = (int)(speed * Math.sin(angle));
				int dx = (int)(speed * Math.cos(angle));
				System.out.println("angle: "+angle+" dx: "+dx+" dy: "+dy);
				loc.setX(loc.getX()+dx);
				loc.setY(loc.getY()+dy);
				
				if (dx == 0)
				{
					if (dy < 0)
						setBitmap(BitmapFactory.decodeResource(pond.getResources(), R.drawable.fish));
					else
						setBitmap(BitmapFactory.decodeResource(pond.getResources(), R.drawable.fish4));
				}
				
				if (deltaX < 0)
					angle -= Math.PI;
				if (deltaX > 0)
				{
					if (Math.PI*(-3.0/8) < angle && angle < Math.PI*(-1.0/8))
					{
						setBitmap(BitmapFactory.decodeResource(pond.getResources(), R.drawable.fish2));
						System.out.println("facing upper right");
					}
					else if (Math.PI*(-1.0/8) < angle && angle <= Math.PI*(1.0/8))
					{
						setBitmap(BitmapFactory.decodeResource(pond.getResources(), R.drawable.fish3));
						System.out.println("facing right");
					}
					else if (Math.PI*(1.0/8) < angle && angle <= Math.PI*(3.0/8))
					{
						setBitmap(BitmapFactory.decodeResource(pond.getResources(), R.drawable.fish8));
						System.out.println("facing lower right");
					}
					else if (Math.PI*(-1.0/2) < angle && angle <= Math.PI*(-3.0/8))
					{
						setBitmap(BitmapFactory.decodeResource(pond.getResources(), R.drawable.fish));
						System.out.println("facing up");
					}
					else if (Math.PI*(3.0/8) < angle && angle <= Math.PI*(1.0/8))
					{
						setBitmap(BitmapFactory.decodeResource(pond.getResources(), R.drawable.fish4));
						System.out.println("facing down");
					}
					else
					{
						System.out.println("some angle is not being accounted for: "+angle);
					}
				}
				else if (deltaX < 0)
				{
					if (Math.PI*(-3.0/8) < angle && angle <= Math.PI*(-1.0/8))
					{
						setBitmap(BitmapFactory.decodeResource(pond.getResources(), R.drawable.fish7));
						System.out.println("facing lower left");
					}
					else if (Math.PI*(-1.0/8) < angle && angle <= Math.PI*(1.0/8))
					{
						setBitmap(BitmapFactory.decodeResource(pond.getResources(), R.drawable.fish5));
						System.out.println("facing left");
					}
					else if (Math.PI*(1.0/8) < angle && angle <= Math.PI*(3.0/8))
					{
						setBitmap(BitmapFactory.decodeResource(pond.getResources(), R.drawable.fish6));
						System.out.println("facing upper left");
					}
					else if (Math.PI*(-1.0/2) < angle && angle <= Math.PI*(-3.0/8))
					{
						System.out.println("facing down");
						setBitmap(BitmapFactory.decodeResource(pond.getResources(), R.drawable.fish4));
					}
					else if (Math.PI*(3.0/8) < angle && angle <= Math.PI*(1.0/2))
					{
						System.out.println("facing up");
						setBitmap(BitmapFactory.decodeResource(pond.getResources(), R.drawable.fish));
					}
					else
					{
						System.out.println("some angle is not being accounted for: "+angle);
					}
				}
			}
		}
		return true;
	}
}