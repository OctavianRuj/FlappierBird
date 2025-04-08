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
    private Pipe[] pipes= new Pipe[3];
    private int score;
    private int bestScore;
    private boolean pipesInitialized=false;




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
        if (!pipesInitialized) {
            resetPipes();
            pipesInitialized = true;
        }
        // Called when the surface is ready — start the game loop thread
        if (thread == null || !thread.isAlive()) {
            thread = new GameThread(getHolder(), this);
            thread.setRunning(true);
            thread.start();
        }

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
        //not used
    }
    public void update() {
        if(gameStart) {
            bird.update();
            for (Pipe pipe : pipes) {
                pipe.update();
                scoreIncrement(pipe);
                if(checkCollision(pipe)) resetBird();
            }
            if (gameOver(bird.getY())) {
                resetBird();
            }
        }
        System.out.println("score is"+score);
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
        drawScore(canvas);
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
        score=0;

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
        boolean inXRange = birdX + birdRadius > pipeX +buffer&& birdX - birdRadius < pipeX + pipeWidth-buffer;

        // Check vertical collision with buffer (wider gap)
        boolean outOfGap = birdY - birdRadius < (gapTop - buffer) || birdY + birdRadius > (gapBottom + buffer);

        return inXRange && outOfGap;
    }
    private void resetPipes(){

        int spacing = 900; // distance between pipes

        for (int i = 0; i < pipes.length; i++) {
            float startX = getWidth() + i * spacing;
            pipes[i] = new Pipe(startX, getHeight(),getContext());
        }
    }
    private void scoreIncrement(Pipe pipe){
        if (!pipe.getScored() && bird.getX() > pipe.getX() + pipe.getWidth()) {
            score++;
            pipe.setScored(true);  // prevent double counting
        }
        if (score>=bestScore) bestScore=score;
    }
    private void drawScore(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(80);
        paint.setFakeBoldText(true);

        // Draw current score
        canvas.drawText("Score: " + score, 50, 100, paint);

        // Draw best score
        canvas.drawText("Best: " + bestScore, 50, 200, paint);
    }






}

