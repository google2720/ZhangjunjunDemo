package android.com.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhangjunjun on 2018/5/11.
 * Canvas常用操作
 *
 * 1. 所有的画布操作都只影响后续的绘制，对之前已经绘制过的内容没有影响。
 *
 */

public class CanvasOperationTest extends View{

    Paint mPaint;
    private int mWidth;
    private int mHeight;

    public CanvasOperationTest(Context context) {
        this(context,null);
    }

    public CanvasOperationTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasOperationTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
       // mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
         mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       // translate(canvas);
       // scale(canvas);
       // rotate(canvas);
        skew(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth= w;
        mHeight = h;
    }

    public void translate(Canvas canvas){
        /**
         * 1.位移(translate)
         * 位移是基于当前位置移动，而不是每次基于屏幕左上角的(0,0)点移动
         */
        // 在坐标原点绘制一个黑色圆形
        mPaint.setColor(Color.BLACK);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);

        // 在坐标原点绘制一个蓝色圆形
        mPaint.setColor(Color.BLUE);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);
    }


    /**
     * 缩放
     * @param canvas
     *      前两个参数是相同的分别为x轴和y轴的缩放比例。而第二种方法比前一种多了两个参数，用来控制缩放中心位置的
     *      public void scale (float sx, float sy)
            public final void scale (float sx, float sy, float px, float py)

     */
    public void scale(Canvas canvas){

        /**
         * 缩放中心就是坐标原点
         */
        // 将坐标系原点移动到画布正中心
//        canvas.translate(mWidth / 2, mHeight / 2);
//
//        RectF rect = new RectF(0,-400,400,0);   // 矩形区域
//
//        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
//        canvas.drawRect(rect,mPaint);
//
//        canvas.scale(0.5f,0.5f);                // 画布缩放
//
//        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
//        canvas.drawRect(rect,mPaint);


        /**
         * 缩放中心位置稍微改变
         */
//        canvas.translate(mWidth / 2, mHeight / 2);
//
//        RectF rect = new RectF(0,-400,400,0);   // 矩形区域
//
//        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
//        canvas.drawRect(rect,mPaint);
//
//        canvas.scale(0.5f,0.5f,200,0);          // 画布缩放  <-- 缩放中心向右偏移了200个单位
//
//        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
//        canvas.drawRect(rect,mPaint);


        /**
         * 当缩放比例为负数的时候会根据缩放中心轴进行翻转
         */
//        canvas.translate(mWidth / 2, mHeight / 2);
//
//        RectF rect = new RectF(0,-400,400,0);   // 矩形区域
//
//        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
//        canvas.drawRect(rect,mPaint);
//
//
//        canvas.scale(-0.5f,-0.5f);          // 画布缩放
//
//        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
//        canvas.drawRect(rect,mPaint);


        /**
         * 缩放中心位置改变,  并且缩放比例为负数的时候会根据缩放中心轴进行翻转
         */
//        canvas.translate(mWidth / 2, mHeight / 2);
//
//        RectF rect = new RectF(0,-400,400,0);   // 矩形区域
//
//        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
//        canvas.drawRect(rect,mPaint);
//
//        canvas.scale(-0.5f,-0.5f,200,0);          // 画布缩放  <-- 缩放中心向右偏移了200个单位
//
//        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
//        canvas.drawRect(rect,mPaint);


        /**
         * 和位移(translate)一样，缩放也是可以叠加的。

         canvas.scale(0.5f,0.5f);
         canvas.scale(0.5f,0.1f);
         调用两次缩放则 x轴实际缩放为0.5x0.5=0.25 y轴实际缩放为0.5x0.1=0.05
         */
        canvas.translate(mWidth / 2, mHeight / 2);

        RectF rect = new RectF(-400,-400,400,400);   // 矩形区域

        for (int i=0; i<=20; i++)
        {
            canvas.scale(0.9f,0.9f);
            canvas.drawRect(rect,mPaint);
        }
    }


    /**
     * 旋转
     * @param canvas
     * 旋转提供了两种方法：

        public void rotate (float degrees)

        public final void rotate (float degrees, float px, float py)

        第二种方法多出来的两个参数依旧是控制旋转中心点
     */
    public void rotate(Canvas canvas){
        /**
         * 默认旋转中心为原点
         */
//        canvas.translate(mWidth / 2, mHeight / 2);
//
//        RectF rect = new RectF(0,-300,300,0);   // 矩形区域
//
//        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
//        canvas.drawRect(rect,mPaint);
//
//        canvas.rotate(180);                     // 旋转180度 <-- 默认旋转中心为原点
//
//        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
//        canvas.drawRect(rect,mPaint);


        /**
         * 改变旋转中心位置
         */
//        canvas.translate(mWidth / 2, mHeight / 2);
//
//        RectF rect = new RectF(0,-200,200,0);   // 矩形区域
//
//        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
//        canvas.drawRect(rect,mPaint);
//
//        canvas.rotate(180,100,0);               // 旋转180度 <-- 旋转中心向右偏移100个单位
//
//        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
//        canvas.drawRect(rect,mPaint);


        /**
         * 旋转也是可叠加的
             canvas.rotate(180);
             canvas.rotate(20);
             调用两次旋转，则实际的旋转角度为180+20=200度。
         */

        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);

        canvas.drawCircle(0,0,200,mPaint);          // 绘制两个圆形
        canvas.drawCircle(0,0,150,mPaint);

//        canvas.drawLine(0,150,0,200,mPaint);
//        canvas.rotate(10);
//        canvas.drawLine(0,150,0,200,mPaint);

        for (int i=0; i<=360; i+=10){               // 绘制圆形之间的连接线
            canvas.drawLine(0,150,0,200,mPaint);
            canvas.rotate(10);
        }
    }


    /**
     * 错切只提供了一种方法：

     public void skew (float sx, float sy)
     参数含义：
     float sx:将画布在x方向上倾斜相应的角度，sx倾斜角度的tan值，
     float sy:将画布在y轴方向上倾斜相应的角度，sy为倾斜角度的tan值.

     变换后:

     X = x + sx * y
     Y = sy * x + y
     * @param canvas
     */
    public void skew(Canvas canvas){
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);

        RectF rect = new RectF(0,0,200,200);   // 矩形区域

        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);

        canvas.skew(1,0);                       // 水平错切 <- 45度

        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);
    }


    /**
     * restore

         状态回滚，就是从栈顶取出一个状态然后根据内容进行恢复。

         同样以上面状态栈图片为例，调用一次restore方法则将状态栈中第5次取出，根据里面保存的状态进行状态恢复。

     restoreToCount

         弹出指定位置以及以上所有状态，并根据指定位置状态进行恢复。

         以上面状态栈图片为例，如果调用restoreToCount(2) 则会弹出 2 3 4 5 的状态，并根据第2次保存的状态进行恢复。

     getSaveCount

         获取保存的次数，即状态栈中保存状态的数量，以上面状态栈图片为例，使用该函数的返回值为5。
     */
    public void save(){
        /**
         * 记住下面的步骤就可以了：

             save();      //保存状态
             ...          //具体操作
             restore();   //回滚到之前的状态
             这种方式也是最简单和最容易理解的使用方法。
         */
    }

}
