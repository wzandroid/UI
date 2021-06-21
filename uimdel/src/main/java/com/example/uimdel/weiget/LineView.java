package com.example.uimdel.weiget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.uimdel.R;

public class LineView extends View {
    boolean isClean;
    private Paint linePaint = new Paint();
    private int corner;
    private int lineWith;
    private Point startPoint, endPoint;
    private Path path = new Path();

    public LineView(@NonNull Context context) {
        super(context);
    }

    public LineView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LineView);
        int color = ta.getColor(R.styleable.LineView_lineColor, Color.BLUE);
        corner = ta.getDimensionPixelSize(R.styleable.LineView_lineCorner, 80);
        lineWith = ta.getDimensionPixelSize(R.styleable.LineView_lineWith, 5);
        ta.recycle();
        init(color);
    }

    private void init(int color){
        linePaint.setColor(color);
        linePaint.setStrokeWidth(lineWith);
        startPoint = new Point();
        endPoint = new Point();
    }

    public void drawLine(View startView, View endView, int color){
        if (startView == null || endView == null) return;
        linePaint.setColor(color);
        isClean = false;
        startPoint.x = 0;
        endPoint.x = getMeasuredWidth();
        startPoint.y = (startView.getTop() + startView.getBottom()) / 2;
        endPoint.y = (endView.getTop() + endView.getBottom()) / 2;
        path.moveTo(startPoint.x, startPoint.y);
        linePaint.setAntiAlias(true);
        path.lineTo(startPoint.x + corner, startPoint.y);
        linePaint.setAntiAlias(false);
        path.lineTo(endPoint.x, endPoint.y);
        invalidate();
    }

    public void cleanLine(){
        isClean = true;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isClean) return;
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x - corner, endPoint.y, linePaint);
        canvas.drawLine(endPoint.x - corner, endPoint.y, endPoint.x, endPoint.y, linePaint);
    }
}
