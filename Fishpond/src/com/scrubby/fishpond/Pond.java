package com.scrubby.fishpond;

import java.util.ArrayList;

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
		fish = new Fish(BitmapFactory.decodeResource(getResources(), R.drawable.fish), 30, 50, this);
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
			fish.setDirected(true);
			fish.setIdling(false);
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE)//finger is being dragged across the screen
		{
			ArrayList<Location> locs = new ArrayList<Location>();
			//the fish should try to follow the path drawn somehow
			final int historySize = event.getHistorySize();
			final int pointerCount = event.getPointerCount();
		     for (int h = 0; h < historySize; h++) 
		     {
		         for (int p = 0; p < pointerCount; p++) 
		         {
		             int x = (int)event.getHistoricalX(p, h);
		             int y = (int)event.getHistoricalY(p, h);
		             locs.add(new Location(x, y));
		         }
		     }
		     for (int p = 0; p < pointerCount; p++) 
		     {
		         int x = (int)event.getX(p);
		         int y = (int)event.getY(p);
		         locs.add(new Location(x, y));
		     }
		     fish.setPath(locs);
		     fish.setIdling(false);
		}
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawColor(Color.CYAN);
		fish.draw(canvas);
	}
}