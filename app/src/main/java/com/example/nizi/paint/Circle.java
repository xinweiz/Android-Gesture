package com.example.nizi.paint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class Circle {
    public float x;
    public float y;
    public int r = 100;
    public int pointId;
    private Paint textPaint;

    int red;
    int green;
    int blue;
    Random random = new Random();

    public Circle(float x, float y, int pointId) {
        this.x = x;
        this.y = y;
        this.pointId = pointId;
        red = random.nextInt(255);
        green = random.nextInt(255);
        blue = random.nextInt(255);
    }

    public void drawSelf(Canvas canvas, Paint paint) {
        textPaint = new Paint();
        textPaint.setTextSize(50);
        textPaint.setColor(Color.BLACK);
        paint.setColor(Color.rgb(red, green, blue));
        canvas.drawCircle(x, y, r, paint);
        canvas.drawText("pointerID: "+pointId+"  x: "+x+",  y: "+y,0,50*(pointId+1),textPaint);
        Log.i("x", x+"");
    }
}
