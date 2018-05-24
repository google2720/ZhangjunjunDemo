package android.com.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Arrays;

/**
 * Created by zhangjunjun on 2018/4/28.
 */

public class SurfacePathView extends SurfaceView implements SurfaceHolder.Callback,Runnable {


    private int numPoints;
    private float[] mData;
    static final float PRECISION = 1f;

    private SurfaceHolder mSurfaceHolder;
    private ValueAnimator mValueAnimator;
    private long mAnimationDuration, mAnimationStartDelay;
    private boolean isAnimationStarted;

    private Paint mPaint;
    private int mLightLineColor;
    private int mDarkLineColor;
    private Keyframes mKeyframes;
    private float[] mLightPoints;
    private float[] mDarkPoints;
    private boolean isDrawing;


    public SurfacePathView(Context context) {
        this(context,null);
    }

    public SurfacePathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SurfacePathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        //初始化画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        setLineWidth(10);

        //默认颜色
        mLightLineColor = Color.RED;
        mDarkLineColor = Color.DKGRAY;

        setZOrderOnTop(true);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        mSurfaceHolder.addCallback(this);

        mAnimationDuration = 6000L;
        mAnimationStartDelay = 2000L;
    }





    public void setAnimationDuration(long duration) {
        mAnimationDuration = duration;
    }

    public void setStartDelay(long delay) {
        mAnimationStartDelay = delay;
    }

    public void startAnimation() {
        if (!isAnimationStarted) {
            isAnimationStarted = true;
            mValueAnimator = ValueAnimator.ofFloat(-1.4F, 1F).setDuration(mAnimationDuration);
            mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
            mValueAnimator.setStartDelay(mAnimationStartDelay);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float currentProgress = (float) animation.getAnimatedValue();
                    float lightLineStartProgress, lightLineEndProgress;
                    float darkLineStartProgress, darkLineEndProgress;
                    darkLineEndProgress = currentProgress;
                    darkLineStartProgress = lightLineStartProgress = darkLineEndProgress + 1.4F;
                    lightLineEndProgress = darkLineEndProgress + 1;
                    if (lightLineEndProgress < 0) {
                        lightLineEndProgress = 0;
                    }
                    if (darkLineEndProgress < 0) {
                        darkLineEndProgress = 0;
                    }
                    if (lightLineStartProgress > 1) {
                        darkLineStartProgress = lightLineStartProgress = 1;
                    }
                    setLightLineProgress(lightLineStartProgress, lightLineEndProgress);
                    setDarkLineProgress(darkLineStartProgress, darkLineEndProgress);
                }
            });
            mValueAnimator.start();
        }
    }


    public void setPath(Path path) {
        mKeyframes = new Keyframes(path);
    }

    public void setLineWidth(float width) {
        mPaint.setStrokeWidth(width);
    }

    public void setLightLineColor(@ColorInt int color) {
        mLightLineColor = color;
    }

    public void setDarkLineColor(@ColorInt int color) {
        mDarkLineColor = color;
    }

    public void setLightLineProgress(float start, float end) {
        setLineProgress(start, end, true);
    }

    public void setDarkLineProgress(float start, float end) {
        setLineProgress(start, end, false);
    }

    private void setLineProgress(float start, float end, boolean isLightPoints) {
        if (mKeyframes == null)
            throw new IllegalStateException("path not set yet");

        if (isLightPoints)
            mLightPoints = mKeyframes.getRangeValue(start, end);
        else
            mDarkPoints = mKeyframes.getRangeValue(start, end);
        invalidate();
    }

    private void restart() {
        isDrawing = true;
        new Thread(this).start();
    }
    private void stop() {
        isDrawing = false;
        if (mValueAnimator != null && mValueAnimator.isRunning())
            mValueAnimator.cancel();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        restart();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stop();
    }


    private void startDraw(Canvas canvas) {
        mPaint.setColor(mDarkLineColor);
        if (mDarkPoints != null) {
            canvas.drawPoints(mDarkPoints, mPaint);
        }
        mPaint.setColor(mLightLineColor);
        if (mLightPoints != null) {
            canvas.drawPoints(mLightPoints, mPaint);
        }
    }

    @Override
    public void run() {
        while (isDrawing) {
            Canvas canvas = mSurfaceHolder.lockCanvas();
            if (canvas == null) return;
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            startDraw(canvas);
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    private static class Keyframes {
        static final float PRECISION = 1f; //精度我们用1就够了 (数值越少 numPoints 就越大)
        int numPoints;
        float[] mData;

        Keyframes(Path path) {
            init(path);
        }

        private void init(Path path) {
            final PathMeasure pathMeasure = new PathMeasure(path, false);
            final float pathLength = pathMeasure.getLength();
            numPoints = (int) (pathLength / PRECISION) + 1;
            mData = new float[numPoints * 2];
            final float[] position = new float[2];
            int index = 0;
            for (int i = 0; i < numPoints; ++i) {
                final float distance = (i * pathLength) / (numPoints - 1);
                //拿到当前距离上点的数据 存在position中
                pathMeasure.getPosTan(distance, position, null);

                //放进数组，最后mData 是这样的: {x0, y0, x1, y1, x2, y2, ...}这样我们就可以直接使用 Canvas   drawPoints 批量画点了
                mData[index] = position[0];
                mData[index + 1] = position[1];
                index += 2;
            }
            numPoints = mData.length;
        }


        /**
         * 拿到start和end之间的x,y数据
         *
         * @param start 开始百分比
         * @param end   结束百分比
         * @return 裁剪后的数据
         */
        float[] getRangeValue(float start, float end) {
            if (start >= end)
                return null;

            int startIndex = (int) (numPoints * start);
            int endIndex = (int) (numPoints * end);

            //必须是偶数，因为需要float[]{x,y}这样x和y要配对的
            if (startIndex % 2 != 0) {
                //直接减，不用担心 < 0  因为0是偶数，哈哈
                --startIndex;
            }
            if (endIndex % 2 != 0) {
                //不用检查越界
                ++endIndex;
            }
            //根据起止点裁剪
            return Arrays.copyOfRange(mData, startIndex, endIndex);
        }
    }



}
