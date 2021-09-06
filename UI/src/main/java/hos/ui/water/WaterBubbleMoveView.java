package hos.ui.water;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hos.ui.R;

/**
 * <p>Title: WaterBubbleView </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/5/22 18:14
 */
public class WaterBubbleMoveView extends View {

    private boolean mBubbleStart = false;
    private int mBubbleMaxRadius = 10;          // 气泡最大半径 px
    private int mBubbleMinRadius = 4;           // 气泡最小半径 px
    private int mBubbleMaxSize = 100;            // 气泡数量
    private int mBubbleRefreshTime = 10;        // 刷新间隔
    private int mBubbleMaxSpeedY = 5;           // 气泡速度
    private int mBubbleAlpha = 128;             // 气泡画笔

    private int mBubbleColor = 0x0F4286f4;
    private int mBubbleBackgroundColorStart = 0xFF4286f4;
    private int mBubbleBackgroundColorEnd = 0xFF373B44;
    private int mBubbleBottomPadding = 0;

    private RectF mWaterRectF;                  // 水占用的区域

    private final Path mWaterPath;                    // 水

    private final Paint mWaterPaint;                  // 水画笔
    private Paint mBubblePaint;                 // 气泡画笔
    // 控件的宽度
    private int mViewWidth;

    public WaterBubbleMoveView(Context context) {
        this(context, null);
    }

    public WaterBubbleMoveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterBubbleMoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterBubbleMoveView);

        mBubbleMaxSize = typedArray.getInt(R.styleable.WaterBubbleMoveView_bubbleMaxSize, mBubbleMaxSize);
        mBubbleMaxRadius = typedArray.getDimensionPixelOffset(R.styleable.WaterBubbleMoveView_bubbleMaxRadius, mBubbleMaxRadius);
        mBubbleMinRadius = typedArray.getDimensionPixelOffset(R.styleable.WaterBubbleMoveView_bubbleMinRadius, mBubbleMinRadius);
        mBubbleBottomPadding = typedArray.getDimensionPixelOffset(R.styleable.WaterBubbleMoveView_bubbleBottomPadding, mBubbleBottomPadding);

        mBubbleColor = typedArray.getColor(R.styleable.WaterBubbleMoveView_bubbleColor, mBubbleColor);
        mBubbleBackgroundColorStart = typedArray.getColor(R.styleable.WaterBubbleMoveView_bubbleBackgroundColorStart, mBubbleBackgroundColorStart);
        mBubbleBackgroundColorEnd = typedArray.getColor(R.styleable.WaterBubbleMoveView_bubbleBackgroundColorEnd, mBubbleBackgroundColorEnd);
        mBubbleStart = typedArray.getBoolean(R.styleable.WaterBubbleMoveView_bubbleStart, false);

        typedArray.recycle();
        mWaterPath = new Path();

        mWaterPaint = new Paint();
        mWaterPaint.setAntiAlias(true);
        // 初始化气泡
        mBubblePaint = new Paint();
        mBubblePaint.setColor(0x0F4286f4);
        mBubblePaint.setAlpha(mBubbleAlpha);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mWaterRectF = new RectF();

        mWaterPath.reset();
        mWaterPath.moveTo(0, 0);
        mWaterPath.lineTo(w, 0);
        mWaterPath.lineTo(w, h);
        mWaterPath.lineTo(0, h);
        mWaterPath.close();

        mWaterRectF.set(0, 0, w, h);

        LinearGradient gradient = new LinearGradient(mWaterRectF.centerX(), mWaterRectF.top,
                mWaterRectF.centerX(), mWaterRectF.bottom, mBubbleBackgroundColorStart, mBubbleBackgroundColorEnd, Shader.TileMode.CLAMP);
        mWaterPaint.setShader(gradient);
        if (mBubbleStart) {
            start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mWaterPath, mWaterPaint);
        drawBubble(canvas);
    }

    //--- 气泡效果 ---------------------------------------------------------------------------------

    private static class Bubble {
        int radius;     // 气泡半径
        float speedY;   // 上升速度
        float speedX;   // 平移速度
        float x;        // 气泡x坐标
        float y;        // 气泡y坐标
    }

    private ArrayList<Bubble> mBubbles = new ArrayList<>();

    private Random random = new Random();
    //    private Thread mBubbleThread;
    private boolean isStart = false;


    // 开始气泡线程
    public void start() {
        stop();
//        mBubbleThread = new Thread() {
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(mBubbleRefreshTime);
//                        tryCreateBubble();
//                        refreshBubbles();
//                        postInvalidate();
//                    } catch (InterruptedException e) {
//                        System.out.println("Bubble线程结束");
//                        break;
//                    }
//                }
//            }
//        };
//        mBubbleThread.start();
        isStart = true;
        postStart();
    }

    private void postStart() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                int oneWidth = mViewWidth / mBubbleMaxSize;
                int oneBubbleSize = mBubbleMaxSize / 10;
                random.nextInt(mBubbleMaxSize);
                for (int i = 0; i < 10; i++) {
                    tryCreateBubble(oneWidth * (oneBubbleSize * i + 1 + random.nextInt(oneBubbleSize)));
                }
                tryCreateBubble(mViewWidth - (random.nextInt(oneBubbleSize)));
                refreshBubbles();
                postInvalidate();
                if (isStart) {
                    postStart();
                }
            }
        }, mBubbleRefreshTime);
    }

    // 停止气泡线程
    public void stop() {
        isStart = false;
//        if (null == mBubbleThread) return;
//        mBubbleThread.interrupt();
//        mBubbleThread = null;
    }

    // 绘制气泡
    private void drawBubble(Canvas canvas) {
        List<Bubble> list = new ArrayList<>(mBubbles);
        for (Bubble bubble : list) {
            if (null == bubble) continue;
            canvas.drawCircle(bubble.x, bubble.y,
                    bubble.radius, mBubblePaint);
        }
    }

    // 尝试创建气泡
    private void tryCreateBubble(int bubbleX) {
        if (null == mWaterRectF) return;
        if (mBubbles.size() >= mBubbleMaxSize) {
            return;
        }
        if (random.nextFloat() < 0.95) {
            return;
        }
        Bubble bubble = new Bubble();
        int radius = random.nextInt(mBubbleMaxRadius - mBubbleMinRadius);
        radius += mBubbleMinRadius;
        float speedY = random.nextFloat() * mBubbleMaxSpeedY;
        while (speedY < 1) {
            speedY = random.nextFloat() * mBubbleMaxSpeedY;
        }
        bubble.radius = radius;
        bubble.speedY = speedY;
        bubble.x = bubbleX;
        bubble.y = mWaterRectF.bottom - radius - mBubbleBottomPadding;
        float speedX = random.nextFloat() - 0.5f;
        while (speedX == 0) {
            speedX = random.nextFloat() - 0.5f;
        }
        bubble.speedX = speedX * 2;
        mBubbles.add(bubble);
    }

    // 刷新气泡位置，对于超出区域的气泡进行移除
    private void refreshBubbles() {
        List<Bubble> list = new ArrayList<>(mBubbles);
        for (Bubble bubble : list) {
            if (bubble.y - bubble.speedY <= mWaterRectF.top + bubble.radius) {
                mBubbles.remove(bubble);
            } else {
                int i = mBubbles.indexOf(bubble);
                if (bubble.x + bubble.speedX <= mWaterRectF.left + bubble.radius) {
                    bubble.x = mWaterRectF.left + bubble.radius;
                } else if (bubble.x + bubble.speedX >= mWaterRectF.right - bubble.radius) {
                    bubble.x = mWaterRectF.right - bubble.radius;
                } else {
                    bubble.x = bubble.x + bubble.speedX;
                }
                bubble.y = bubble.y - bubble.speedY;
                mBubbles.set(i, bubble);
            }
        }
    }
}
