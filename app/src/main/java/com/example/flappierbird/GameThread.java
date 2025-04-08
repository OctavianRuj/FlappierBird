package com.example.flappierbird;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private final GameView gameView;
    private boolean running;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = null;
            try {
                // Lock the canvas so we can draw on it
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    // Update game state
                    gameView.update();

                    // Draw everything to the screen
                    gameView.draw(canvas);
                }
            } finally {
                // Always unlock the canvas after we're done drawing
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            // Pause to control the frame rate (~60 FPS)
            try {
                sleep(17); // 1000 ms / 60 ≈ 16.67 ms per frame
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
