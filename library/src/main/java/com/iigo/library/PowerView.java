package com.iigo.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author SamLeung
 * @e-mail 729717222@qq.com
 * @github https://github.com/samlss
 * @csdn https://blog.csdn.net/Samlss
 * @description A pretty charging view
 */
public class PowerView extends View {
    private final static int ORIENTATION_VERTICAL = 0;
    private final static int ORIENTATION_HORIZONTAL = 1;

    private final static int DEFAULT_WIDTH = 150;
    private final static int DEFAULT_HEIGHT = 50;

    private final static int DEFAULT_POWER_COLOR = Color.parseColor("#4ED966");
    private final static int DEFAULT_BG_COLOR = Color.parseColor("#6B6B77");

    private int mOrientation = ORIENTATION_HORIZONTAL;
    private int mPowerColor  = DEFAULT_POWER_COLOR;
    private int mBgColor     = DEFAULT_BG_COLOR;

    private Paint mPowerPaint;
    private RectF mHeadRectF;
    private RectF mHeadCalculateRectF;
    private RectF mPowerRectF;

    private Paint mBgPaint;
    private Path mPowerBoxPath;
    private Path mHeadPath;

    private int mProgress;
    private float mBoxWidth;
    private float mBoxHeight;
    private float mBoxMarginTopUnderVertical;

    public PowerView(Context context) {
        this(context, null);
    }

    public PowerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PowerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttrs(attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PowerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        parseAttrs(attrs);
        init();
    }

    private void parseAttrs(AttributeSet attrs){
        if (attrs == null){
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PowerView);
        mPowerColor  = typedArray.getColor(R.styleable.PowerView_powerColor, DEFAULT_POWER_COLOR);
        mBgColor     = typedArray.getInteger(R.styleable.PowerView_bgColor, DEFAULT_BG_COLOR);
        mOrientation = typedArray.getInteger(R.styleable.PowerView_orientation, ORIENTATION_HORIZONTAL);
        mProgress    = typedArray.getInteger(R.styleable.PowerView_powerProgress, 0);
        checkProgress();
        typedArray.recycle();
    }

    private void init(){
        mPowerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPowerPaint.setColor(mPowerColor);
        mPowerPaint.setStyle(Paint.Style.FILL);

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStyle(Paint.Style.FILL);

        mPowerBoxPath = new Path();
        mHeadPath = new Path();
        mPowerRectF = new RectF();
        mHeadRectF = new RectF();
        mHeadCalculateRectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize  = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int mWidth;
        int mHeight;

        if (mOrientation == ORIENTATION_HORIZONTAL){
            mWidth  = DEFAULT_WIDTH;
            mHeight = DEFAULT_HEIGHT;
        }else{
            mWidth  = DEFAULT_HEIGHT;
            mHeight = DEFAULT_WIDTH;
        }

        boolean isWidthWrap  = getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean isHeightWrap = getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT;

        if (!isWidthWrap && !isHeightWrap){
            return;
        }

        setMeasuredDimension(isWidthWrap ? mWidth : widthSize, isHeightWrap ? mHeight : heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPowerBoxPath.reset();
        mHeadPath.reset();

        if (mOrientation == ORIENTATION_HORIZONTAL) {
            mBoxWidth  = w * 19 / 20;
            mBoxHeight = h;

            float boxAndHeadGap = (w - mBoxWidth) * 0.2f;
            float headWidth     = (w - mBoxWidth) * 0.8f;
            float headHeight    = h / 6f;

            float boxCornerRadius = Math.min(w, h) / 10f;
            mPowerBoxPath.addRoundRect(new RectF(0, 0, mBoxWidth, h), boxCornerRadius, boxCornerRadius, Path.Direction.CW);

            float headCornerRadius = headWidth * 2 / 3f;

            mHeadRectF.set(mBoxWidth + boxAndHeadGap, h / 2f - headHeight, w, h / 2f + headHeight);
            mHeadPath.addRoundRect(mHeadRectF,
                    new float[]{0, 0, headCornerRadius, headCornerRadius, headCornerRadius, headCornerRadius, 0, 0},
                    Path.Direction.CW);
        }else{
            mBoxWidth  = w;
            mBoxHeight = h * 19 / 20;

            float boxAndHeadGap = (h - mBoxHeight) * 0.2f;
            float headHeight     = (h - mBoxHeight) * 0.8f;
            float headWidth    = w / 6f;

            mBoxMarginTopUnderVertical = boxAndHeadGap+headHeight;
            float boxCornerRadius = Math.min(w, h) / 10f;
            mPowerBoxPath.addRoundRect(new RectF(0, mBoxMarginTopUnderVertical, mBoxWidth, h), boxCornerRadius, boxCornerRadius, Path.Direction.CW);

            float headCornerRadius = headHeight * 2 / 3f;

            mHeadRectF.set(w / 2f - headWidth, 0, w / 2f + headWidth, headHeight);
            mHeadPath.addRoundRect(mHeadRectF,
                    new float[]{headCornerRadius, headCornerRadius, headCornerRadius, headCornerRadius, 0, 0, 0, 0},
                    Path.Direction.CW);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mOrientation == ORIENTATION_HORIZONTAL){
            drawHorizontal(canvas);
        } else if (mOrientation == ORIENTATION_VERTICAL){
            drawVertical(canvas);
        }
    }

    /**
     * Draw the horizontal power.
     * */
    private void drawHorizontal(Canvas canvas){
        //draw head
        canvas.drawPath(mHeadPath, mBgPaint);

        if (mProgress > 95) {
            canvas.save();
            canvas.clipPath(mHeadPath);

            mHeadCalculateRectF.set(mHeadRectF.left, mHeadRectF.top, mHeadRectF.left + (mProgress - 95) / 5f * mHeadRectF.width(), mHeadRectF.bottom);
            canvas.drawRect(mHeadCalculateRectF, mPowerPaint);
            canvas.restore();
        }

        canvas.save();
        canvas.clipPath(mPowerBoxPath);
        canvas.drawPath(mPowerBoxPath, mBgPaint);
        mPowerRectF.set(0, 0, mProgress / 95f * mBoxWidth, mBoxHeight);
        canvas.drawRect(mPowerRectF, mPowerPaint);
        canvas.restore();
    }

    /**
     * Draw the vertical power.
     * */
    private void drawVertical(Canvas canvas){
        //draw head
        canvas.drawPath(mHeadPath, mBgPaint);

        if (mProgress > 95) {
            canvas.save();
            canvas.clipPath(mHeadPath);

            mHeadCalculateRectF.set(mHeadRectF.left,  mHeadRectF.bottom - (mProgress - 95) / 5f * mHeadRectF.height(), mHeadRectF.right,
                    mHeadRectF.bottom);
            canvas.drawRect(mHeadCalculateRectF, mPowerPaint);
            canvas.restore();
        }

        canvas.save();
        canvas.clipPath(mPowerBoxPath);
        canvas.drawPath(mPowerBoxPath, mBgPaint);
        mPowerRectF.set(0, mBoxMarginTopUnderVertical + mBoxHeight - mProgress / 95f * mBoxHeight,
                mBoxWidth, mBoxHeight + mBoxMarginTopUnderVertical);
        canvas.drawRect(mPowerRectF, mPowerPaint);
        canvas.restore();
    }

    /**
     * Check if the progress is between 0-100.
     * */
    private void checkProgress(){
        if (mProgress < 0){
            mProgress = 0;
        }else if (mProgress > 100){
            mProgress = 100;
        }
    }

    /**
     * Set the progress,the range is 0-100.
     * */
    public void setProgress(int progress) {
        this.mProgress = progress;
        checkProgress();
        invalidate();
    }

    /**
     * Get current progress.
     * */
    public int getProgress() {
        return mProgress;
    }

    /**
     * Set the color of battery background
     * */
    public void setBgColor(int bgColor) {
        this.mBgColor = bgColor;
        mBgPaint.setColor(mBgColor);
        postInvalidate();
    }

    /**
     * Set the color of charging power.
     * */
    public void setPowerColor(int powerColor) {
        this.mPowerColor = powerColor;
        mPowerPaint.setColor(mPowerColor);
    }

    public int getBgColor() {
        return mBgColor;
    }

    public int getPowerColor() {
        return mPowerColor;
    }
}
