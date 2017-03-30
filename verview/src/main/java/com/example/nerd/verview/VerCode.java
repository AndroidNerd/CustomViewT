package com.example.nerd.verview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.Random;

/**
 * Created by nerd on 2017/3/28.
 * <p>
 * 1、自定义View的属性
 * 2、在View的构造方法中获得我们自定义的属性
 * 3、重写onMesure
 * 4、重写onDraw
 */

/**
 * 验证码
 */
public class VerCode extends View {
    private String mTxt;
    private int mTxtColor;
    private int mTxtSize;
    private float mRadius;

    private int mLeng;

    private Rect mBound;
    private Paint mPaint;

    public VerCode(Context context) {
        this(context, null);
    }

    public VerCode(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerCode(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.VerCode, defStyleAttr, 0);
        mTxtColor = a.getColor(R.styleable.VerCode_txtColor, Color.BLACK);
        mTxtSize = a.getDimensionPixelSize(R.styleable.VerCode_txtSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mLeng = a.getInt(R.styleable.VerCode_verLen, 4);
        mRadius=a.getFloat(R.styleable.VerCode_radius,0f);
        a.recycle();
        mTxt = randomText();

        mPaint = new Paint();
        mPaint.setTextSize(mTxtSize);

        mBound = new Rect();
        mPaint.getTextBounds(mTxt, 0, mTxt.length(), mBound);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mTxt = randomText();
                requestLayout();//reMeasure
                postInvalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.GRAY);
        canvas.drawRoundRect(0, 0, getMeasuredWidth(), getMeasuredHeight(),mRadius,mRadius, mPaint);

        mPaint.setColor(mTxtColor);
        Log.e("getWidth", getWidth() + "");
        Log.e("bound.width", mBound.width() + "");
        Log.e("getHeight", getHeight() + "");
        Log.e("bound.height", mBound.height() + "");
        Log.e("wid", getWidth() / 2 - mBound.width() / 2 + "");
        Log.e("hei", getHeight() / 2 + mBound.height() / 2 + "");
        //x:字符串开始的x坐标 y:baseLine的位置
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int baseLine = (getMeasuredHeight() - fontMetricsInt.bottom + fontMetricsInt.top) / 2 - fontMetricsInt.top;
        Log.e("baseLine", baseLine + "");
        canvas.drawText(mTxt, getWidth() / 2 - mBound.width() / 2, baseLine, mPaint);
        addLines(canvas, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("onMeasure", "---------");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(mTxtSize);
            mPaint.getTextBounds(mTxt, 0, mTxt.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(mTxtSize);
            mPaint.getTextBounds(mTxt, 0, mTxt.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }
        setMeasuredDimension(width, height);
    }

    private String randomText() {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < mLeng; i++) {
            //这样做只是小写abc
            //stringBuilder.append((char)(int)(Math.random()*26+97));

            //Math.random()产生0-1的伪随机数
            stringBuilder.append(chars.charAt((int) (Math.random() * 62)));
        }
        return stringBuilder.toString();
    }

    private void addLines(Canvas canvas, Paint paint) {
        Random random=new Random();
        for (int i = 0; i < 80; i++) {
            paint.setColor(Color.parseColor(getRandomColor()));
            canvas.drawLine(random.nextInt(getMeasuredWidth()),random.nextInt(getMeasuredHeight()),random.nextInt(getMeasuredWidth()),random.nextInt(getMeasuredHeight()),mPaint);
        }
    }

    private String getRandomColor() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;
        return "#"+r+g+b;
    }
}
