package com.scrubby.fishpond;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Pond extends SurfaceView implements SurfaceHolder.Callback
{
	private Fish fish;
	private MainThread thread;
	
	public Pond(Context context)
	{
		super(context);
		getHolder().addCallback(this);
		fish = new Fish(BitmapFactory.decodeResource(getResources(), R.drawable.fish), 30, 50);
		thread = new MainThread(getHolder(), this);
		setFocusable(true);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) 
	{
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		boolean trying = true;
		while (trying)
		{
			try
			{
				thread.join();
				trying = false;
			}
			catch (InterruptedException e)
			{
			}
		}
	}
	
	/**
	 * This is where all the big shit happens :)
	 * 
	 * @param event
	 * @return
	 */
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)//screen has been touched
		{
			//the fish should try to move to the point in whatever way it sees fit
			//here we're just setting the location of the fish; the drawing is done in MainThread's run
			int x = (int) event.getX();
			int y = (int) event.getY();
			fish.setDest(new Location(x, y));
			/*
			 * calculate angle of path from fish's initial point to dest
			 * then use angle to set specific bitmap
			 */
			int deltaX = x-fish.getLoc().getX();
			System.out.println("deltaX "+deltaX);
			int deltaY = y-fish.getLoc().getY();
			System.out.println("deltaY "+deltaY);
			if (deltaX == 0)
			{
				if (deltaY < 0)
					fish.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fish));
				else
					fish.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fish4));
			}
			else
			{
				double angle = Math.atan((1.0*deltaY)/deltaX);
				System.out.println("angle "+angle);
				if (deltaX > 0)
				{
					if (Math.PI*(-3.0/8) < angle && angle < Math.PI*(-1.0/8))
						fish.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fish2));
					else if (Math.PI*(-1.0/8) < angle && angle <= Math.PI*(1.0/8))
						fish.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fish3));
					else if (Math.PI*(1.0/8) < angle && angle <= Math.PI*(3.0/8))
						fish.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fish8));
					else if (Math.PI*(-1.0/2) < angle && angle <= Math.PI*(-3.0/8))
						fish.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fish));
					else if (Math.PI*(3.0/8) < angle && angle <= Math.PI*(1.0/8))
						fish.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fish4));
				}
				else if (deltaX < 0)
				{
					if (Math.PI*(-3.0/8) < angle && angle <= Math.PI*(-1.0/8))
						fish.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fish7));
					else if (Math.PI*(-1.0/8) < angle && angle <= Math.PI*(1.0/8))
						fish.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fish5));
					else if (Math.PI*(1.0/8) < angle && angle <= Math.PI*(3.0/8))
						fish.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fish6));
					else if (Math.PI*(-1.0/2) < angle && angle <= Math.PI*(-3.0/8))
						fish.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fish4));
					else if (Math.PI*(3.0/8) < angle && angle <= Math.PI*(1.0/2))
						fish.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fish));
				}
				
			}
			fish.setDirected(true);
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE)//finger is being dragged across the screen
		{
			//the fish should try to follow the path drawn somehow
			
		}
		return true;
	}
	/*
	//collision to prevent the fish from going off-screen
	public void update()
	{
		if (fish.getSpeed().getxDir() == Speed.DIRECTION_RIGHT && fish.getLoc().getX() + fish.getBitmap().getWidth() / 2 >= getWidth())
			fish.getSpeed().switchXDirection();
		if (fish.getSpeed().getxDir() == Speed.DIRECTION_LEFT && fish.getLoc().getX() - fish.getBitmap().getWidth() / 2 <= 0)
			fish.getSpeed().switchXDirection();
		if (fish.getSpeed().getyDir() == Speed.DIRECTION_UP && fish.getLoc().getY() + fish.getBitmap().getWidth() / 2 >= getWidth())
			fish.getSpeed().switchXDirection();
		if (fish.getSpeed().getyDir() == Speed.DIRECTION_DOWN && fish.getLoc().getY() - fish.getBitmap().getHeight() / 2 <= 0)
			fish.getSpeed().switchXDirection();
	}*/
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawColor(Color.CYAN);
		fish.draw(canvas);
	}
}