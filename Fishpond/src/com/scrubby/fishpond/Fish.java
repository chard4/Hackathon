package com.scrubby.fishpond;

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
	
	private Bitmap bitmap;
	
	private int speed = 2;
	
	public static final int ERROR = 1;
	
	public Fish(Bitmap bitmap, int x, int y)
	{
		this.setBitmap(bitmap);
		this.setLoc(new Location(x, y));
		this.setDest(new Location(x, y));
		directed = false;
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
		if (directed)
		{
			int deltaX = dest.getX() - loc.getX();
			int deltaY = dest.getY() - loc.getY();
			
			//first see if you are "close enough" to the destination
			if (Math.abs(deltaX) <= ERROR && Math.abs(deltaY) <= ERROR)
			{
				canvas.drawBitmap(bitmap, loc.getX() - (bitmap.getWidth() / 2), loc.getY() - (bitmap.getHeight() / 2), null);
				return;
			}
			if (deltaX == 0)
			{
				if (deltaY > 0)
					loc.setY(loc.getY() + speed);
				else if (deltaY < 0)
					loc.setY(loc.getY() - speed);
			}
			else
			{
				double angle = Math.atan((deltaY/deltaX));
				if (deltaX < 0)
					angle += Math.PI;
				//System.out.println(angle);
				int dy = (int)(speed * Math.sin(angle));
				int dx = (int)(speed * Math.cos(angle));
				loc.setX(loc.getX()+dx);
				loc.setY(loc.getY()+dy);
			}
		}	
		canvas.drawBitmap(bitmap, loc.getX() - (bitmap.getWidth() / 2), loc.getY() - (bitmap.getHeight() / 2), null);
	}
}