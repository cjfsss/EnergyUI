package hos.ui.water;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import hos.ui.R;


/**
 * Created by allen on 2016/12/13.
 * <p>
 * 波浪动画
 */

public class WaveViewByBezier extends View {

    /**
     * 屏幕高度
     */
    private int mScreenHeight;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    /**
     * 波浪的画笔
     */
    private Paint mWavePaint;
    /**
     * 一个周期波浪的长度
     */
    private int mWaveLength;

    /**
     * 波浪的路径
     */
    Path mWavePath;

    /**
     * 平移偏移量
     */
    private int mOffset;

    private float mOffsetY;
    /**
     * 一个屏幕内显示几个周期
     */
    private int mWaveCount;

    /**
     * 振幅
     */
    private int mWaveAmplitude;

    /**
     * 波形的颜色
     */
    private int waveColor = 0xaaFF7E37;

    /**
     * 波形移动的速度
     */
    private float waveSpeed = 3f;

    private ValueAnimator valueAnimator;

    private int mWaveFillType;

    /**
     * 是否直接开启波形
     */
    private boolean waveStart;

    private boolean isReset = false;

    public WaveViewByBezier(Context context) {
        this(context, null);

    }

    public WaveViewByBezier(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttr(context, attrs);
    }

    private void getAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveViewByBezier);

        mWaveFillType = typedArray.getInt(R.styleable.WaveViewByBezier_waveBezierFillType, 1);
        mWaveAmplitude = typedArray.getDimensionPixelOffset(R.styleable.WaveViewByBezier_waveBezierAmplitude, dp2px(20));
        waveColor = typedArray.getColor(R.styleable.WaveViewByBezier_waveBezierColor, waveColor);
        waveSpeed = typedArray.getFloat(R.styleable.WaveViewByBezier_waveBezierSpeed, waveSpeed);
        waveStart = typedArray.getBoolean(R.styleable.WaveViewByBezier_waveBezierStart, false);

        typedArray.recycle();
        initPaint();
        if (waveStart) {
            post(new Runnable() {
                @Override
                public void run() {
                    startAnimation();
                }
            });
        }

    }


    /**
     * 初始化画笔
     */
    private void initPaint() {
        mWavePath = new Path();
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setColor(waveColor);
        mWavePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mWavePaint.setAntiAlias(true);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mScreenHeight = h;
        mWaveLength = mScreenWidth = w;

        /**
         * 加上1.5是为了保证至少有两个波形（屏幕外边一个完整的波形，屏幕里边一个完整的波形）
         */
        mWaveCount = (int) Math.round(mScreenWidth / mWaveLength + 0.5);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPath(canvas);
    }

    /**
     * sin函数图像的波形
     *
     * @param canvas
     */
    private void drawPath(Canvas canvas) {
        mWavePath.reset();

        mWavePath.moveTo(-mWaveLength + mOffset, mOffsetY);
        // TODO: 2017/6/19   //相信很多人会疑惑为什么控制点的纵坐标是以下值,是根据公式计算出来的,具体计算方法情况文章内容
        for (int i = 0; i < mWaveCount; i++) {

            //第一个控制点的坐标为(-mWaveLength * 3 / 4,-mWaveAmplitude)
            mWavePath.quadTo(-mWaveLength * 3 / 4 + mOffset + i * mWaveLength,
                    -mOffsetY,
                    -mWaveLength / 2 + mOffset + i * mWaveLength,
                    mOffsetY);

            //第二个控制点的坐标为(-mWaveLength / 4,3 * mWaveAmplitude)
            mWavePath.quadTo(-mWaveLength / 4 + mOffset + i * mWaveLength,
                    3 * mOffsetY,
                    mOffset + i * mWaveLength,
                    mOffsetY);
        }
//        mWavePath.moveTo(-mWaveLength + mOffset, mWaveAmplitude);
//        // TODO: 2017/6/19   //相信很多人会疑惑为什么控制点的纵坐标是以下值,是根据公式计算出来的,具体计算方法情况文章内容
//        for (int i = 0; i < mWaveCount; i++) {
//
//            //第一个控制点的坐标为(-mWaveLength * 3 / 4,-mWaveAmplitude)
//            mWavePath.quadTo(-mWaveLength * 3 / 4 + mOffset + i * mWaveLength,
//                    -mWaveAmplitude,
//                    -mWaveLength / 2 + mOffset + i * mWaveLength,
//                    mWaveAmplitude);
//
//            //第二个控制点的坐标为(-mWaveLength / 4,3 * mWaveAmplitude)
//            mWavePath.quadTo(-mWaveLength / 4 + mOffset + i * mWaveLength,
//                    3 * mWaveAmplitude,
//                    mOffset + i * mWaveLength,
//                    mWaveAmplitude);
//        }
        if (mWaveFillType == 0) {
            // 闭合上面
            mWavePath.lineTo(getWidth(), 0);
            mWavePath.lineTo(0, 0);
        } else {
            // 闭合下边
            mWavePath.lineTo(getWidth(), getHeight());
            mWavePath.lineTo(0, getHeight());
        }
        mWavePath.close();

        canvas.drawPath(mWavePath, mWavePaint);

    }


    /**
     * 波形动画
     */
    private void initAnimation() {
        int startDelay = (int) (100 + Math.random() * 100 * 2);
        valueAnimator = ValueAnimator.ofInt(0, mWaveLength);
        valueAnimator.setDuration((long) (waveSpeed * 10000));
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setStartDelay(startDelay);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                isReset = !isReset;
            }
        });
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                if (isReset) {
                    mOffsetY = mWaveAmplitude - animatedFraction * mWaveAmplitude;
                    mOffset = mWaveLength - (int) animation.getAnimatedValue();
                } else {
                    mOffsetY = animatedFraction * mWaveAmplitude;
                    mOffset = (int) animation.getAnimatedValue();
                }
//                Log.i("TAG", "mWaveAmplitude: " + mWaveAmplitude);
//                Log.i("TAG", "mOffsetY: " + mOffsetY);
//                Log.i("TAG", "mOffset: " + mOffset);
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    public void startAnimation() {
        initAnimation();
    }

    public void stopAnimation() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    public void pauseAnimation() {
        if (valueAnimator != null) {
            valueAnimator.pause();
        }
    }

    public void resumeAnimation() {
        if (valueAnimator != null) {
            valueAnimator.resume();
        }
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }
}
