package hos.ui.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;


import java.util.ArrayList;
import java.util.List;

import hos.ui.R;

/**
 * <p>Title </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/8/16 16:40
 */
public class ArcView extends View {

    public final static int STROKE = 0;
    public final static int FILL = 1;

    private int backgroundColor;

    //渐变色
    private final List<Integer> defaultProgressColor = new ArrayList<>();

    //渐变色
    private List<Integer> progressColor = defaultProgressColor;

    //Canvas绘制开始位置
    private float startPointAngle = 150.0f;

    //结束位置
    private float endPointAngle = 240.0f;

    // 圆环的宽度
    private float roundWidth = 26f;

    //进度条的value
    private float currentProgress = 0.0f;

    //进度条的value
    private float maxProgress = 100.0f;

    // 绘制的样式
    private int style = STROKE;

    private int textColor = ContextCompat.getColor(getContext(), R.color.design_txt);
    private float textSize = sp2px(12f);
    private boolean textShow = false;

    // 是否显示小圆
    private boolean smallCircleShow = true;

    private int width = 200;
    private int height = 200;

    // 背景
    private Paint _paint;

    private Paint getPaint() {
        if (_paint == null) {
            _paint = new Paint();
            // 锯齿
            _paint.setAntiAlias(true);
            // 圆头
            _paint.setStrokeCap(Paint.Cap.ROUND);
            // 描边
            _paint.setStyle(Paint.Style.STROKE);
        }
        return _paint;
    }

    // 进度
    private Paint _progressPaint;

    private Paint getProgressPaint() {
        if (_progressPaint == null) {
            _progressPaint = new Paint();
            // 锯齿
            _progressPaint.setAntiAlias(true);
            // 圆头
            _progressPaint.setStrokeCap(Paint.Cap.ROUND);
            // 描边
            _progressPaint.setStyle(Paint.Style.STROKE);
        }
        return _progressPaint;
    }

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray typeArray =
                    context.obtainStyledAttributes(attrs, R.styleable.ArcView, defStyleAttr, 0);
            if (typeArray.hasValue(R.styleable.ArcView_backgroundColor)) {
                int color = ContextCompat.getColor(context, R.color.design_grey200);
                backgroundColor = typeArray.getColor(R.styleable.ArcView_backgroundColor, color);
            }
            startPointAngle = typeArray.getFloat(R.styleable.ArcView_startPointAngle, 150f);
            endPointAngle = typeArray.getFloat(R.styleable.ArcView_endPointAngle, 240f);
            maxProgress = typeArray.getFloat(R.styleable.ArcView_maxProgress, 100f);
            typeArray.recycle();
        }
        defaultProgressColor.add(ContextCompat.getColor(getContext(), R.color.design_greena400));
        defaultProgressColor.add(ContextCompat.getColor(getContext(), R.color.design_green300));
        defaultProgressColor.add(ContextCompat.getColor(getContext(), R.color.design_yellowa400));
        defaultProgressColor.add(ContextCompat.getColor(getContext(), R.color.design_orange400));
        defaultProgressColor.add(ContextCompat.getColor(getContext(), R.color.design_deeporangea400));
        defaultProgressColor.add(ContextCompat.getColor(getContext(), R.color.design_red400));
        defaultProgressColor.add(ContextCompat.getColor(getContext(), R.color.design_pink800));
        defaultProgressColor.add(ContextCompat.getColor(getContext(), R.color.design_deeporange800));
        progressColor = defaultProgressColor;
    }

    /**
     * 初始化
     */
    public void init() {
        init(ContextCompat.getColor(getContext(), R.color.design_grey200), defaultProgressColor, 150.0f, 240.0f);
    }

    /**
     * 初始化
     */
    public void init(List<Integer> progressColor,
                     float startPointAngle, float endPointAngle) {
        init(ContextCompat.getColor(getContext(), R.color.design_grey200), progressColor, startPointAngle, endPointAngle);
    }

    /**
     * 初始化
     */
    public void init(float startPointAngle, float endPointAngle) {
        init(ContextCompat.getColor(getContext(), R.color.design_grey200), defaultProgressColor, startPointAngle, endPointAngle);
    }

    /**
     * 初始化
     */
    public void init(List<Integer> progressColor) {
        init(ContextCompat.getColor(getContext(), R.color.design_grey200), progressColor, 150.0f, 240.0f);
    }

    /**
     * 初始化
     */
    public void init(int backgroundColor, List<Integer> progressColor,
                     float startPointAngle, float endPointAngle) {
        this.backgroundColor = backgroundColor;
        this.progressColor.clear();
        this.progressColor.addAll(progressColor);
        this.startPointAngle = startPointAngle;
        this.endPointAngle = endPointAngle;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
    }

    /**
     * 圆环
     */
    public ArcView arc() {
        this.startPointAngle = 150f;
        this.endPointAngle = 240f;
        _paint.setStrokeCap(Paint.Cap.ROUND);
        _progressPaint.setStrokeCap(Paint.Cap.ROUND);
        return this;
    }

    /**
     * 圆环
     */
    public ArcView circle() {
        this.startPointAngle = 0f;
        this.endPointAngle = 360f;
        _paint.setStrokeCap(Paint.Cap.BUTT);
        _progressPaint.setStrokeCap(Paint.Cap.BUTT);
        return this;
    }

    /**
     * 圆填充
     */
    public ArcView circleFill() {
        circle().style(FILL);
        _paint.setStrokeCap(Paint.Cap.SQUARE);
        _progressPaint.setStrokeCap(Paint.Cap.SQUARE);
        return this;
    }

    /**
     * 圆弧填充
     */
    public ArcView arcFill() {
        circle().style(FILL);
        _paint.setStrokeCap(Paint.Cap.SQUARE);
        _progressPaint.setStrokeCap(Paint.Cap.SQUARE);
        return this;
    }

    /**
     * 样式
     */
    public ArcView style(int style) {
        this.style = style;
        if (style == FILL) {
            textShow = false;
            smallCircleShow = false;
        }
        return this;
    }

    /**
     * 设置文字的样式
     */
    public ArcView text(int textColor, int textSize, boolean textShow) {
        this.textColor = textColor;
        this.textSize = textSize;
        this.textShow = textShow;
        return this;
    }

    /**
     * 获取当前的进度
     */
    public float getCurrentProgress() {
        return currentProgress;
    }

    /**
     * 启动进度
     */
    public void startUnProgress(@NonNull ValueAnimator.AnimatorUpdateListener listener) {
        startUnProgress(100f, 800L, listener);
    }

    /**
     * 启动进度
     */
    public void startUnProgress(float maxProgress) {
        startUnProgress(maxProgress, 800L, null);
    }

    /**
     * 启动进度
     */
    public void startUnProgress(float maxProgress, long duration,final ValueAnimator.AnimatorUpdateListener listener
    ) {
        this.currentProgress = 0f;
        this.maxProgress = maxProgress;
        ValueAnimator animator = ValueAnimator.ofFloat(currentProgress, maxProgress);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (listener != null) {
                    listener.onAnimationUpdate(animation);
                }
                currentProgress = Float.parseFloat(animation.getAnimatedValue().toString());
                postInvalidate();
            }
        });
        animator.start();
    }

    /**
     * 启动进度
     */
    public void startProgress(float progress) {
        startProgress(progress, 100, 800L, null);
    }

    /**
     * 启动进度
     */
    public void startProgress(
            float progress,
            float maxProgress, long duration,final ValueAnimator.AnimatorUpdateListener listener

    ) {
        if (currentProgress == progress) {
            // 进度没有变化，不进行动画
            return;
        }
        if (currentProgress > maxProgress) {
            // 进度大于最大进度，不进行变换
            return;
        }
        this.maxProgress = maxProgress;
        ValueAnimator animator = ValueAnimator.ofFloat(currentProgress, maxProgress);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (listener != null) {
                    listener.onAnimationUpdate(animation);
                }
                currentProgress = Float.parseFloat(animation.getAnimatedValue().toString());
                postInvalidate();
            }
        });
        animator.start();
    }

    /**
     * 演示动画
     */
    public void startUnDemon() {
        startUnProgress(500f);
    }

    /**
     * 演示动画
     */
    public void startDemon() {
        if (getCurrentProgress() == 0f) {
            startProgress(500f);
        } else {
            startProgress(0f);
        }
    }

    /**
     * 演示动画
     */
    public void startCircleUnDemon() {
        circle().startDemon();
    }

    /**
     * 演示动画
     */
    public void startCircleDemon() {
        circle().startDemon();
    }

    /**
     * 演示动画
     */
    public void startCircleFillUnDemon() {
        circleFill().startDemon();
    }

    /**
     * 演示动画
     */
    public void startCircleFillDemon() {
        circleFill().startDemon();
    }

    // 获取圆弧或圆上面的点的 X
    private float getXForCircle(double centerX, double radius, double radian) {
        // angle * 180.0 / pi
        return (float) (centerX + radius * Math.cos(radian));
    }

    // 获取圆弧或圆上面的点的 Y
    private float getYForCircle(double centerY, double radius, double radian) {
        return (float) (centerY + radius * Math.sin(radian));
    }

    /**
     * Value of sp to value of px.
     *
     * @param spValue The value of sp.
     * @return value of px
     */
    public static int sp2px(final float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("AirQualityView", "onDraw: width:${width} height:${height}");
        Log.i(
                "AirQualityView",
                "onDraw: startPointAngle:$startPointAngle endPointAngle:$endPointAngle"
        );
        // 配置背景颜色和线宽
        _paint.setStrokeWidth(roundWidth);
        _paint.setStyle(Paint.Style.STROKE);
        if (backgroundColor == 0) {
            _paint.setColor(ColorUtils.setAlphaComponent(
                    ContextCompat.getColor(
                            getContext(),
                            R.color.design_grey200
                    ), 200
            ));
        } else {
            _paint.setColor(backgroundColor);
        }
        // 计算小院的半径
        float smallCircleRadius = (float) (roundWidth * 0.75);
        // 计算半径

        float left = smallCircleRadius;
        float top = smallCircleRadius;
        float right = width - smallCircleRadius;
        float bottom = height - smallCircleRadius;
        // 计算半径
        double radius = (right - left) / 2.0;
        // 计算中心点
        float centerX = (float) (left + radius);
        float centerY = (float) (top + radius);

        // 计算圆的矩形
        RectF rect = new RectF(left, top, right, bottom);
        if (style == FILL) {
            _paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        // 绘制背景  startPointAngle 开始弧度  endPointAngle 结束弧度
        canvas.drawArc(rect, startPointAngle, endPointAngle, false, _paint);
//        // 绘制圆的矩形
//        canvas.drawRect(rect, _bgPaint)
//        // 绘制圆上办部分的矩形
//        canvas.drawRect(RectF(left.toFloat(), top.toFloat(), centerX.toFloat(), centerY.toFloat()), _bgPaint)
//        canvas.drawRect(RectF(centerX.toFloat(), top.toFloat(), right.toFloat(), centerY.toFloat()), _bgPaint)

        /**
         * static final Shader.TileMode CLAMP: 边缘拉伸. static final
         * Shader.TileMode MIRROR：在水平方向和垂直方向交替景象, 两个相邻图像间没有缝隙. Static final
         * Shader.TillMode REPETA：在水平方向和垂直方向重复摆放,两个相邻图像间有缝隙缝隙.
         */
        // 创建渐变颜色
        int progressSize = progressColor.size();
        float[] positions = new float[progressSize];
        double oneAngle = endPointAngle / progressSize;
        for (int i = 0; i < progressSize; i++) {
            double angle = (startPointAngle + oneAngle * (i));
            positions[i] = (float) (angle / (startPointAngle + endPointAngle));
        }
        int[] progressColorArrays = new int[progressColor.size()];
        for (int i = 0; i < progressColor.size(); i++) {
            progressColorArrays[i] = progressColor.get(i);
        }
        SweepGradient sweepGradient = new SweepGradient(
                centerX, centerY, progressColorArrays,
                positions
        );
        Matrix matrix = new Matrix();
        matrix.setRotate(startPointAngle + endPointAngle, centerX, centerX);
        sweepGradient.setLocalMatrix(matrix);
//        val gradient = LinearGradient(
//            0f, 0f, 100f, 100f, progressColor,
//            FloatArray(progressColor.size), Shader.TileMode.MIRROR
//        ) // 渐变颜色
        // 配置进度线宽和渐变颜色
        _progressPaint.setStrokeWidth(roundWidth);
        _progressPaint.setShader(sweepGradient);

        // 计算进度
        float endPointAngleTarget = (currentProgress / maxProgress) * endPointAngle;
        // 绘制进度
        if (style == FILL) {
            // 绘制圆环
            sweepGradient.setLocalMatrix(null);
            _progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            _progressPaint.setColor(ContextCompat.getColor(getContext(), R.color.green));
            if (currentProgress != 0f) {
                canvas.drawArc(
                        rect, startPointAngle, endPointAngleTarget, true, _progressPaint
                );
            }
            return;
        }
        // 绘制圆弧
        _progressPaint.setStyle(Paint.Style.STROKE);
        // 圆环
        canvas.drawArc(
                rect, startPointAngle, endPointAngleTarget, false, _progressPaint
        );
        if (smallCircleShow) {
            // 下来绘制小圆
            _paint.setShader(null);
            _paint.setColor(Color.GREEN);
            _paint.setStyle(Paint.Style.FILL);
            // 获取当前进度的角度
            double circleAngle = endPointAngleTarget + startPointAngle;
            // 计算当前进度的弧度
            double circleRadian = Math.toRadians(circleAngle);
            // 获取圆弧上点的中心点
            // 计算公式 x=半径+cos(弧度)  y=半径+sin(弧度) 或者 x=半径+cos(角度)  y=半径+sin(角度)
            // 弧度 = 角度*3.14/180  角度 = 弧度*180/3.14
            // 获取当前进度的圆心
            float smallCenterX = getXForCircle(centerX, radius, circleRadian);
            float smallCenterY = getYForCircle(centerY, radius, circleRadian);
//            print("circleAngle：$circleAngle circleRadian：$circleRadian smallCenterX:$smallCenterX smallCenterY:$smallCenterY")
            canvas.drawCircle(
                    smallCenterX, smallCenterY, (float) smallCircleRadius,
                    _paint
            );
        }
        //画进度百分比
        double percent = (currentProgress / maxProgress * 100);
        if (textShow && percent != 0f && style == STROKE) {
            _paint.setShader(null);
            _paint.setColor(textColor);
            _paint.setStrokeWidth(0); //圆环的宽度
            _paint.setTextSize(textSize);
            _paint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(
                    "$percent%",
                    (getWidth() - _paint.measureText("$percent%")) / 2f,  //y公式： float baselineY = centerY + (fontMetrics.bottom-fontMetrics.top)/2 - fontMetrics.bottom
                    getWidth() / 2f - (_paint.descent() + _paint.ascent()) / 2f,
                    _paint
            );
        }
    }
}
