package com.example.flappierbird;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;


public class Bird {
    private float x;          // X position on screen
    private float y;          // Y position on screen
    private float radius;     // Size of the bird
    private Bitmap birdBitmap;
    private Bitmap flapBitmap;
    private int flapFrames;



    private float velocity;   // Current vertical speed
    private final float gravity = 2f;  // Pulls bird down

    public Bird(Context context, float startX, float startY, float radius) {
        this.x = startX;
        this.y = startY;
        this.radius = radius;
        this.velocity = 0;

        Bitmap originalBirdBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird);
        Bitmap originalFlapBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.birdflap);

            // Scale the image to match the bird’s radius (so it fits perfectly)
        int size = (int)(radius * 2); // diameter of the circle = width and height of image
        birdBitmap = Bitmap.createScaledBitmap(originalBirdBitmap, size, size, false);
        flapBitmap = Bitmap.createScaledBitmap(originalFlapBitmap, size, size, false);

    }

    public void update() {
        velocity += gravity;
        y += velocity;
    }

    public void flap() {
        velocity = -27; // Jump upward when tapped
        flapFrames=5;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        if (flapFrames>0) drawflap(canvas);
         else canvas.drawBitmap(birdBitmap, x - radius, y - radius, null);
        if(flapFrames>0) flapFrames--;
    }
    private void drawflap(Canvas canvas){
        canvas.drawBitmap(flapBitmap, x-radius,y-radius,null);
    }

    // Getter for Y position (optional, for collision later)
    public float getY() {
        return y;
    }

    // Setter for Y position (optional, for restart)
    public void setY(float y) {
        this.y = y;
    }
}
