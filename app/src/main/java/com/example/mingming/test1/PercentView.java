package com.example.mingming.test1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by mingming on 16-5-30.
 */
public class PercentView extends View {

    private int mFirstColor;
    private int mSecondColor;
    private int mSpeed;
    private Paint mPaint;
    private Paint textPaint;
    private int  mProgress = 80;
    private int mCircleWidth;

    private int mRadius = 300;
    private int mHeight;
    private int mWidth;
    private Rect mBound;

    private int x;
    private int y;
    private String text;

    public PercentView(Context context) {
        this(context,null);
    }

    public PercentView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PercentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.PercentView, defStyleAttr, 0);
        mFirstColor = a.getColor(R.styleable.PercentView_firstColor, Color.GREEN);
        mSecondColor = a.getColor(R.styleable.PercentView_secondColor,Color.RED);
        mSpeed = a.getInt(R.styleable.PercentView_speed,20);
        mCircleWidth = a.getDimensionPixelSize(R.styleable.PercentView_circleWidth, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
        a.recycle();
        mPaint = new Paint();
        mPaint.setTextSize(12);
        textPaint = new Paint();
        mBound = new Rect();
        text = mProgress + "%";
        mPaint.setTextSize(120);
        mPaint.getTextBounds(text, 0, text.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取测量大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
         mRadius = Math.max(widthSize,heightSize) / 2;
         mWidth = widthSize;
         mHeight = heightSize;
         x = widthSize / 2;
         y = heightSize / 2;
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY){
            mRadius = widthSize / 2;
            mWidth = widthSize;
            mHeight = heightSize;
            x = widthSize / 2;
            y = heightSize / 2;
        }

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            mWidth = (int) (mRadius * 2);
            mHeight = (int) (mRadius * 2);
            x= mRadius;
            y = mRadius;
        }

        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        mPaint.setColor(mFirstColor); // 设置圆环的颜色
        canvas.drawCircle(x, y, mRadius - mCircleWidth/2, mPaint); // 画出圆环
        mPaint.setColor(mSecondColor); // 设置圆环的颜色
        int mEndAngle = (int) (mProgress * 3.6);
        RectF rect = new RectF(x- mRadius + mCircleWidth/2, y- mRadius + mCircleWidth/2, x + mRadius -mCircleWidth/2, y+mRadius-mCircleWidth/2);
        canvas.drawArc(rect, 270, mEndAngle, false, mPaint); // 根据进度画圆弧
        mPaint.setColor(Color.BLACK);
        //把文本画在圆心居中
        mPaint.setStrokeWidth(0); // 一定要设置为0,否则看到的，呵呵
        mPaint.setColor(Color.RED);
        canvas.drawText(text, x - mBound.width()/2, y + mBound.height()/2 , mPaint);
    }
}