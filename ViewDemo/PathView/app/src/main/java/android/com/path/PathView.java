package android.com.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;

/**
 * Created by zhangjunjun on 2018/4/28.
 */

public class PathView extends View {


    private int numPoints;
    private float[] mData;
    static final float PRECISION = 1f;

    private Paint mPaint;
    private int mLightLineColor;
    private int mDarkLineColor;
    private Keyframes mKeyframes;
    private float[] mLightPoints;
    private float[] mDarkPoints;

    public PathView(Context context) {
        this(context,null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        //初始化画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        setLineWidth(20);

        //默认颜色
        mLightLineColor = Color.RED;
        mDarkLineColor = Color.DKGRAY;
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




    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mDarkLineColor);
        if (mDarkPoints != null)
            canvas.drawPoints(mDarkPoints, mPaint);
        mPaint.setColor(mLightLineColor);
        if (mLightPoints != null)
            canvas.drawPoints(mLightPoints, mPaint);
    }


}
