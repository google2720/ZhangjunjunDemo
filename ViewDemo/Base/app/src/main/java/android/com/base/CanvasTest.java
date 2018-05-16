package android.com.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhangjunjun on 2018/5/8.
 * Canvas绘制图形
 *
 操作类型	相关API	备注
 绘制颜色	drawColor, drawRGB, drawARGB	使用单一颜色填充整个画布
 绘制基本形状	drawPoint, drawPoints, drawLine, drawLines, drawRect, drawRoundRect, drawOval, drawCircle, drawArc	依次为 点、线、矩形、圆角矩形、椭圆、圆、圆弧
 绘制图片	drawBitmap, drawPicture	绘制位图和图片
 绘制文本	drawText, drawPosText, drawTextOnPath	依次为 绘制文字、绘制文字时指定每个文字位置、根据路径绘制文字
 绘制路径	drawPath	绘制路径，绘制贝塞尔曲线时也需要用到该函数
 顶点操作	drawVertices, drawBitmapMesh	通过对顶点操作可以使图像形变，drawVertices直接对画布作用、 drawBitmapMesh只对绘制的Bitmap作用
 画布剪裁	clipPath, clipRect	设置画布的显示区域
 画布快照	save, restore, saveLayerXxx, restoreToCount, getSaveCount	依次为 保存当前状态、 回滚到上一次保存的状态、 保存图层状态、 回滚到指定状态、 获取保存次数
 画布变换	translate, scale, rotate, skew	依次为 位移、缩放、 旋转、错切
 Matrix(矩阵)	getMatrix, setMatrix, concat	实际上画布的位移，缩放等操作的都是图像矩阵Matrix， 只不过Matrix比较难以理解和使用，故封装了一些常用的方法。
 */

public class CanvasTest extends View {

    Paint mPaint;

    public CanvasTest(Context context) {
        this(context, null);
    }

    public CanvasTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    public void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
       // mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        //drawPoint(canvas);
        //drawLine(canvas);
        //drawRect(canvas);
        //drawRoundRect(canvas);
        //drawOval(canvas);
        //drawCircle(canvas);
        //drawArc(canvas);
        paintBase(canvas);
    }

    /**
     * 绘制点 可以绘制一个点，也可以绘制一组点
     * @param canvas
     */
    public void drawPoint(android.graphics.Canvas canvas) {
        canvas.drawPoint(200, 200, mPaint);//在坐标(200,200)位置绘制一个点
        canvas.drawPoints(new float[]{ //绘制一组点，坐标位置由float数组指定
                500, 200,
                500, 300,
                500, 400
        }, mPaint);
    }



    /**
     * 绘制直线：
             绘制直线需要两个点，初始点和结束点，同样绘制直线也可以绘制一条或者绘制一组
     * */
    public void drawLine(Canvas canvas) {
        canvas.drawLine(200, 200, 400, 400, mPaint);// 在坐标(200,200)(400,400)之间绘制一条直线
        canvas.drawLines(new float[]{// 绘制一组线 每四数字(两个点的坐标)确定一条线
                100, 200, 200, 200,
                100, 300, 200, 300
        }, mPaint);
    }



    /**
     * 绘制矩形
     *  确定确定一个矩形最少需要四个数据，就是对角线的两个点的坐标值，这里一般采用左上角和右下角的两个点的坐标。
         关于绘制矩形，Canvas提供了三种重载方法，
         第一种就是提供四个数值(矩形左上角和右下角两个点的坐标)来确定一个矩形进行绘制。
         其余两种是先将矩形封装为Rect或RectF(实际上仍然是用两个坐标点来确定的矩形)
     */
    public void drawRect(Canvas canvas){
        canvas.drawRect(10,10,20,400,mPaint);

        Rect rect = new Rect(50,50,100,100);
        canvas.drawRect(rect,mPaint);

        RectF rectf = new RectF(400,200,550,300); //Rect 与 RectF的区别是 RectF精度更高
        canvas.drawRect(rectf,mPaint);
    }

    /**
     * 绘制圆角钜形
     * 与矩形相比，圆角矩形多出来了两个参数rx 和 ry
     * 代表圆角的半径
     * 这里圆角矩形的角实际上不是一个正圆的圆弧，而是椭圆的圆弧，这里的两个参数实际上是椭圆的两个半径
     */

    public void drawRoundRect(Canvas canvas){

        canvas.drawRoundRect(100,10,200,200, 20,20,mPaint);


        // 矩形
        RectF rectF = new RectF(100,100,800,400);

        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF,mPaint);

        // 绘制圆角矩形
        mPaint.setColor(Color.BLUE);
        canvas.drawRoundRect(rectF,700,400,mPaint);

        //绘制的矩形宽度为700，高度为300，当你让 rx大于350(宽度的一半)， ry大于150(高度的一半) 时奇迹就出现了，你会发现圆角矩形变成了一个椭圆
        // 实际上在rx为宽度的一半，ry为高度的一半时，刚好是一个椭圆，通过上面我们分析的原理推算一下就能得到:
        // 而当rx大于宽度的一半，ry大于高度的一半时，实际上是无法计算出圆弧的，
        // 所以drawRoundRect对大于该数值的参数进行了限制(修正)，凡是大于一半的参数均按照一半来处理。

    }

    /**
     * 绘制椭圆：
     * 只需要一个矩形作为参数
     */
    public void drawOval(Canvas canvas){
        // 第一种
        RectF rectF = new RectF(100,100,800,400);
        canvas.drawOval(rectF,mPaint);

        // 第二种
        canvas.drawOval(100,100,800,400,mPaint);
    }


    /**
     * 绘制圆
     */
    public void drawCircle(Canvas canvas){
        canvas.drawCircle(500,400,100,mPaint);  // 绘制一个圆心坐标在(500,400)，半径为100 的圆。
    }

    /**
     * 绘制圆弧
     *
     // 第一种
         public void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter, @NonNull Paint paint){}

     // 第二种
         public void drawArc(float left, float top, float right, float bottom, float startAngle,float sweepAngle, boolean useCenter, @NonNull Paint paint) {}

        startAngle  // 开始角度
        sweepAngle  // 扫过角度
        useCenter   // 是否使用中心
     */
    public void drawArc(Canvas canvas){

        RectF rectF = new RectF(100,100,300,300);
        // 绘制背景矩形
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF,mPaint);

        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF,0,90,false,mPaint);


        //-------------------------------------

        RectF rectF2 = new RectF(100,350,300,550);
        // 绘制背景矩形
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF2,mPaint);

        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF2,0,360,true,mPaint);


        //-------------------------------------
        RectF rectF3 = new RectF(400,100,600,300);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rectF3,mPaint);

        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rectF3,0,90,true,mPaint);



        //-------------------------------------
        RectF rectF4 = new RectF(400,350,600,550);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rectF4,mPaint);

        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rectF4,0,360,true,mPaint);


        //-------------------------------------
        RectF rectF5 = new RectF(700,100,900,300);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(rectF5,mPaint);

        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rectF5,0,90,true,mPaint);



        //-------------------------------------
        RectF rectF6 = new RectF(700,350,900,550);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(rectF6,mPaint);

        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rectF6,0,360,true,mPaint);
    }


    /**
     * paint参数
     *  STROKE                 //描边
         FILL                  //填充
         FILL_AND_STROKE       //描边加填充
     */
    public void paintBase(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(40);     //为了实验效果明显，特地设置描边宽度非常大

        // 描边
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(200,200,100,paint);

        // 填充
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(500,200,100,paint);

        // 描边加填充
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(800, 200, 100, paint);    }

}
