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
		
	}
	
	protected void onDraw(Canvas canvas)
	{
		canvas.drawColor(Color.CYAN);
		fish.draw(canvas);
	}
}
