package android.com.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhangjunjun on 2018/5/14.
 */

public class PathTest extends View {

    private int mWidth;
    private int mHeight;
    private Paint mPaint;

    public PathTest(Context context) {
        this(context,null);
    }

    public PathTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public void initPaint(){
         mPaint = new Paint();             // 创建画笔
        mPaint.setColor(Color.BLACK);           // 画笔颜色 - 黑色
        mPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
        mPaint.setStrokeWidth(10);              // 边框宽度 - 10
    }


    public void path(Canvas canvas){

        /**
         * 简单使用
         */
        //        canvas.translate(mWidth/2,mHeight/2);
        //
        //        Path path = new Path();
        //        //第一次由于之前没有过操作，所以默认点就是坐标原点O，结果就是坐标原点O到A(200,200)之间连直线
        //        path.lineTo(200,200);
        //        path.lineTo(200,0);
        //        canvas.drawPath(path,mPaint);


        /**
         * movte to 只改变下次操作的起点
         */
        //        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        //        Path path = new Path();                     // 创建Path
        //        path.lineTo(200, 200);                      // lineTo
        //        path.moveTo(200,100);                       //  moveTo只改变下次操作的起点
        //        path.lineTo(200,0);                         // lineTo
        //        canvas.drawPath(path, mPaint);              // 绘制Path


        /**
         *
         *
         * setLastPoint是重置上一次操作的最后一个点，
         * 在执行完第一次的lineTo的时候，最后一个点是A(200,200),
         * 而setLastPoint更改最后一个点为C(200,100),所以在实际执行的时候，第一次的lineTo就不是从原点O到A(200,200)的连线了，
         * 而变成了从原点O到C(200,100)之间的连线了。
         */
        //        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        //        Path path = new Path();                     // 创建Path
        //        path.lineTo(200, 200);                      // lineTo
        //        path.setLastPoint(200,100);                 // setLastPoint  setLastPoint是重置上一次操作的最后一个点
        //        path.lineTo(200,0);                         // lineTo
        //        canvas.drawPath(path, mPaint);              // 绘制Path


        /**
         * close方法 方法用于连接当前最后一个点和最初的一个点(如果两个点不重合的话)，最终形成一个封闭的图形。
         * close的作用是封闭路径，与连接当前最后一个点和第一个点并不等价。如果连接了最后一个点和第一个点仍然无法形成封闭图形，则close什么 也不做
         */
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        Path path = new Path();                     // 创建Path
        path.lineTo(200, 200);                      // lineTo
        path.lineTo(200,0);                         // lineTo
        path.close();                               // close
        canvas.drawPath(path, mPaint);              // 绘制Path

    }


    /**
     * addXxx与arcTo
     * @param canvas
     *
     *
     */
    public void pathAddXx(Canvas canvas){

        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        Path path = new Path();
        path.addRect(-200,-200,200,200, Path.Direction.CW);
        path.setLastPoint(-300,300);                // <-- 重置最后一个点的位置
        canvas.drawPath(path,mPaint);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       // path(canvas);
        pathAddXx(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }
}
