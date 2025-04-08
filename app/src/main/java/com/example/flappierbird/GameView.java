package com.example.flappierbird;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread thread;
    private Bird bird;


    public GameView(Context context) {
        super(context);

        // Register our class as a listener for surface events
        getHolder().addCallback(this);

        // Create the game loop thread and pass it the SurfaceHolder + GameView
        thread = new GameThread(getHolder(), this);

        setFocusable(true); // Makes GameView focusable so it can receive touch events
        bird= new Bird(getContext(),200, 500, 100);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Called when the surface is ready — start the game loop thread
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Called when the surface is destroyed — stop the thread
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join(); // wait for the thread to stop
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Not used for now
    }
    public void update() {
       bird.update();
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.CYAN); // Clear screen with sky blue

            bird.draw(canvas);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            bird.flap();
        }
        return true;
    }



}

