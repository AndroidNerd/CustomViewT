package com.example.nerd.verview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

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
        mTxt = a.getString(R.styleable.VerCode_txt);
        mTxtColor = a.getColor(R.styleable.VerCode_txtColor, Color.BLACK);
        mTxtSize = a.getDimensionPixelSize(R.styleable.VerCode_txtSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        a.recycle();
        mPaint = new Paint();
        mPaint.setTextSize(mTxtSize);

        mBound = new Rect();
        mPaint.getTextBounds(mTxt, 0, mTxt.length(), mBound);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTxtColor);
        canvas.drawText(mTxt, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 - mBound.height() / 2, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
