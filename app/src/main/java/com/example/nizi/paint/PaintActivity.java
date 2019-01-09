package com.example.nizi.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static android.media.CamcorderProfile.get;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class PaintActivity extends View {
    private Context mContext;
    private float x;
    private float y;
    private float leftmost_x = 0;
    private float rightmost_x = 0;
    private int pointId;
    private Paint circlePaint;
    private Paint linePaint;
    List<Circle> circles=new ArrayList<>();
    List<Point> points = new ArrayList<>();

    public PaintActivity(Context context) {
        super(context);
    }
    public PaintActivity(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public PaintActivity(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circlePaint=new Paint();
        for (Circle circle : circles) {
            if(circle.x<=leftmost_x)
                leftmost_x =circle.x;
            else
                rightmost_x = circle.x;
            circle.drawSelf(canvas,circlePaint);
        }
//        Toast.makeText(mContext, leftmost_x+" "+rightmost_x, Toast.LENGTH_SHORT).show();
        linePaint = new Paint();
        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(10);
        if (PaintActivity.this.points.size() > 1) {
            Iterator<Point> iter = PaintActivity.this.points.iterator();
            Point first = null;
            Point last = null;
            while (iter.hasNext()) {
                if (first == null) {
                    first = (Point) iter.next();
                } else {
                    if (last != null) {
                        first = last;
                    }
                    last = (Point) iter.next();
                    canvas.drawLine(first.x, first.y, last.x, last.y, linePaint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mContext = getContext();
        int action=event.getAction();
        int action_code=action&0xff;
        int pointIndex=action>>8;
        x=event.getX(pointIndex);
        y=event.getY(pointIndex);
        pointId=event.getPointerId(pointIndex);
        if (action_code>=5){
            action_code-=5;
        }
        Point p=new Point((int)event.getX(),(int)event.getY());
        switch (action_code){
            case MotionEvent.ACTION_DOWN:
                Circle circle=new Circle(x,y,pointId);
                circles.add(circle);
                PaintActivity.this.points=new ArrayList<Point>();
                PaintActivity.this.points.add(p);
                break;
            case MotionEvent.ACTION_UP:
                circles.remove(get(pointId));
                PaintActivity.this.points.add(p);
                PaintActivity.this.postInvalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int id=event.getPointerId(i);
                    get(id).x=event.getX(i);
                    get(id).y=event.getY(i);
//                    if(i == 0)
//                        Log.i("x", get(id).x+"");
//                    if(i == event.getPointerCount()-1)
//                        Log.i("x", get(id).x+"");
                }
//                circles.add(new Circle(x,y,pointId));
                PaintActivity.this.points.add(p);
                PaintActivity.this.postInvalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                circles.add(new Circle(x,y,pointId));
                break;
            case MotionEvent.ACTION_POINTER_UP:
                circles.remove(get(pointId));
                break;
        }
        invalidate();
        return true;
    }

    public Circle get(int pointId){
        for (Circle circle : circles) {
            if (circle.pointId==pointId){
                return circle;
            }
        }
        return null;
    }
}
