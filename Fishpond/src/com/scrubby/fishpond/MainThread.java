package com.scrubby.fishpond;

import android.view.SurfaceHolder;
import android.graphics.Canvas;

public class MainThread extends Thread 
{
	private SurfaceHolder surfaceHolder;
	private Pond pond;
	private boolean isRunning;
	
	public void setRunning(boolean isRunning)
	{
		this.isRunning = isRunning;
	}
	
	public void run()
	{
		Canvas canvas;
		while (isRunning)
		{
			canvas = null;
			try
			{
				canvas = this.surfaceHolder.lockCanvas();
				synchronized(surfaceHolder)
				{
					pond.onDraw(canvas);
				}
			}
			finally
			{
				if (canvas != null)
					surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}
}
