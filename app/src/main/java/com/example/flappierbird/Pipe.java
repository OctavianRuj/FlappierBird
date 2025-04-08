package com.example.flappierbird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import java.util.Random;

public class Pipe {
    private float x; // Horizontal position
    private final int width = 340;
    private final int pipeHeight = 1800;
    private final float gapHeight = 350;
    private final float speed = 13;

    private float screenHeight;
    private float yOffset; // shifts both pipes vertically

    private Bitmap topPipe, bottomPipe;
    private Bitmap originalTop, originalBottom;

    public Pipe(float screenWidth, float screenHeight, Context context) {
        this.x = screenWidth;
        this.screenHeight = screenHeight;

        // Load original bitmaps
        originalTop = BitmapFactory.decodeResource(context.getResources(), R.drawable.toppipe);
        originalBottom = BitmapFactory.decodeResource(context.getResources(), R.drawable.bottompipe);

        // Scale them once (same size always)
        topPipe = Bitmap.createScaledBitmap(originalTop, width, pipeHeight, false);
        bottomPipe = Bitmap.createScaledBitmap(originalBottom, width, pipeHeight, false);

        randomizeHeights();
    }

    private void randomizeHeights() {
        Random random = new Random();

        // Choose a random Y position for the center of the gap
        float minGapY = gapHeight / 2 + 200;
        float maxGapY = screenHeight - gapHeight / 2 - 200;
        float gapCenterY = random.nextInt((int)(maxGapY - minGapY)) + minGapY;

        // Now place the top pipe so it ends at the top of the gap
        yOffset = gapCenterY - pipeHeight - gapHeight / 2;
    }


    public void update() {
        x -= speed;

        if (x + width < 0) {
            x = screenHeight + width;
            randomizeHeights();
        }
    }

    public void draw(Canvas canvas) {
        // Top pipe
        canvas.drawBitmap(topPipe, x, yOffset, null);

        // Bottom pipe below the gap
        float bottomY = yOffset + pipeHeight + gapHeight;
        canvas.drawBitmap(bottomPipe, x, bottomY, null);
    }

    // Getters
    public float getX() {
        return x;
    }

    public float getWidth() {
        return width;
    }

    public float getGapHeight() {
        return gapHeight;
    }

    public float getTopY() {
        return yOffset;
    }

    public float getBottomY() {
        return yOffset + pipeHeight + gapHeight;
    }

    public int getPipeHeight() {
        return pipeHeight;
    }
}
