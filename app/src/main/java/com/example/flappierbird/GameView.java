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
    private boolean gameStart=false;
    private Pipe[] pipes;



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
       resetPipes();

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
        if(gameStart) {
            bird.update();
            for (Pipe pipe : pipes) {
                pipe.update();
                if(checkCollision(pipe)) resetBird();
            }
            if (gameOver(bird.getY())) {
                resetBird();
            }
        }
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.CYAN); // Clear screen with sky blue
            for (Pipe pipe : pipes) {
                pipe.draw(canvas);
            }
            bird.draw(canvas);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            bird.flap();
            gameStart=true;
        }
        return true;
    }
    private boolean gameOver(float y) {

        return y < 0 || y > getHeight(); // off top or bottom of screen
    }

    private void resetBird(){
        bird.setY(500);
        resetPipes();

        gameStart = false;
    }
    private boolean checkCollision(Pipe pipe) {
        float birdX = bird.getX();
        float birdY = bird.getY();
        float birdRadius = bird.getRadius();

        float pipeX = pipe.getX();
        float pipeWidth = pipe.getWidth();

        // Pipe gap bounds
        float gapTop = pipe.getTopY() + pipe.getPipeHeight();
        float gapBottom = gapTop + pipe.getGapHeight();

        float buffer = 100; // pixels of forgiveness

        // Check horizontal overlap
        boolean inXRange = birdX + birdRadius > pipeX +buffer&& birdX - birdRadius < pipeX + pipeWidth;

        // Check vertical collision with buffer (wider gap)
        boolean outOfGap = birdY - birdRadius < (gapTop - buffer) || birdY + birdRadius > (gapBottom + buffer);

        return inXRange && outOfGap;
    }
    private void resetPipes(){
        pipes = new Pipe[3];
        int spacing = 900; // distance between pipes

        for (int i = 0; i < pipes.length; i++) {
            float startX = getWidth() + i * spacing;
            pipes[i] = new Pipe(startX, getHeight(),getContext());
        }
    }





}

