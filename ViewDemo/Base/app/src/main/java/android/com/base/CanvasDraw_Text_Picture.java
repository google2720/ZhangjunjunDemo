package android.com.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.PictureDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhangjunjun on 2018/5/11.
 * Canvas之图片文字
 *
 *
 *
 */

public class CanvasDraw_Text_Picture extends View{

    Paint mPaint;
    private int mWidth;
    private int mHeight;


    public void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        // mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
        mPaint.setAntiAlias(true);
    }



    public CanvasDraw_Text_Picture(Context context) {
        this(context,null);
    }

    public CanvasDraw_Text_Picture(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasDraw_Text_Picture(Context context, @Nullable AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();


        // 3.在使用前调用(我在构造函数中调用了)
        recording();    // 调用录制
    }



    // 1.创建Picture
    private Picture mPicture = new Picture();



    // 2.录制内容方法
    private void recording() {
        // 开始录制 (接收返回值Canvas)
        Canvas canvas = mPicture.beginRecording(500, 500);
        // 创建一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        // 在Canvas中具体操作
        // 位移
        canvas.translate(250,250);
        // 绘制一个圆
        canvas.drawCircle(0,0,100,paint);

        mPicture.endRecording();
    }


    public void drawPicture(Canvas canvas){
        /**
         * 将Picture中的内容绘制出来可以有以下几种方法：
         1	使用Picture提供的draw方法绘制。
         2	使用Canvas提供的drawPicture方法绘制。
         3	将Picture包装成为PictureDrawable，使用PictureDrawable的draw方法绘制。


         主要区别	分类	简介
         是否对Canvas有影响
         1有影响
         2,3不影响	此处指绘制完成后是否会影响Canvas的状态(Matrix clip等)
         可操作性强弱	1可操作性较弱
         2,3可操作性较强	此处的可操作性可以简单理解为对绘制结果可控程度。
         */


        /**
         *   1.将Picture中的内容绘制在Canvas上
         */

        //mPicture.draw(canvas);

        /**
         * 2.使用Canvas提供的drawPicture方法绘制
         drawPicture有三种方法：

         public void drawPicture (Picture picture)

         public void drawPicture (Picture picture, Rect dst)

         public void drawPicture (Picture picture, RectF dst)
         */
        //canvas.drawPicture(mPicture,new RectF(0,0,mPicture.getWidth(),200));


        /**
         * 3.将Picture包装成为PictureDrawable，使用PictureDrawable的draw方法绘制。
         */
        // 包装成为Drawable
        PictureDrawable drawable = new PictureDrawable(mPicture);
        // 设置绘制区域 -- 注意此处所绘制的实际内容不会缩放
        drawable.setBounds(0,0,250,mPicture.getHeight());
        // 绘制
        drawable.draw(canvas);
    }




    public void drawBitmap(Canvas canvas){
        /**
             * 通过BitmapFactory从不同位置获取Bitmap:
              资源文件(drawable/mipmap/raw):

             Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.raw.bitmap);
             资源文件(assets):

             Bitmap bitmap=null;
             try {
             InputStream is = mContext.getAssets().open("bitmap.png");
             bitmap = BitmapFactory.decodeStream(is);
             is.close();
             } catch (IOException e) {
             e.printStackTrace();
             }


             内存卡文件:
             Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/bitmap.png");


             网络文件:

             // 此处省略了获取网络输入流的代码
             Bitmap bitmap = BitmapFactory.decodeStream(is);
             is.close();
         */


        /**
         * 依照惯例先预览一下drawBitmap的常用方法：

             // 第一种
             public void drawBitmap (Bitmap bitmap, Matrix matrix, Paint paint)

             // 第二种
             public void drawBitmap (Bitmap bitmap, float left, float top, Paint paint)

             // 第三种
             public void drawBitmap (Bitmap bitmap, Rect src, Rect dst, Paint paint)
             public void drawBitmap (Bitmap bitmap, Rect src, RectF dst, Paint paint)
         */

        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.js);


        //第一种
        //canvas.drawBitmap(bitmap,new Matrix(),new Paint());


        //-----------------------------------------------------------------------------
        // 第二种
        //此处指定的是与坐标原点的距离，并非是与屏幕顶部和左侧的距离, 虽然默认状态下两者是重合的
        //canvas.drawBitmap(bitmap,200,500,new Paint());



        //------------------------------------------------------------------------------
        // 第三种
        // 将画布坐标系移动到画布中央
//        canvas.translate(mWidth/2,mHeight/2);
//
//        // 指定图片绘制区域(左上角的四分之一)
//        Rect src = new Rect(0,0,bitmap.getWidth()/2,bitmap.getHeight()/2);
//
//        // 指定图片在屏幕上显示的区域
//        Rect dst = new Rect(0,0,200,400);
//
//        // 绘制图片
//        canvas.drawBitmap(bitmap,src,dst,null);


        //-----------------------------------js-------------------------------------------
        canvas.translate(mWidth/2,mHeight/2);
        // 指定图片绘制区域(左上角的四分之一)
        Rect src = new Rect(0,0,67,84);

        // 指定图片在屏幕上显示的区域
        Rect dst = new Rect(0,0,67,84);

        // 绘制图片
        canvas.drawBitmap(bitmap,src,dst,null);

    }


    /**
     * // 第一类
     public void drawText (String text, float x, float y, Paint paint)
     public void drawText (String text, int start, int end, float x, float y, Paint paint)
     public void drawText (CharSequence text, int start, int end, float x, float y, Paint paint)
     public void drawText (char[] text, int index, int count, float x, float y, Paint paint)

     // 第二类
     public void drawPosText (String text, float[] pos, Paint paint)
     public void drawPosText (char[] text, int index, int count, float[] pos, Paint paint)

     // 第三类
     public void drawTextOnPath (String text, Path path, float hOffset, float vOffset, Paint paint)
     public void drawTextOnPath (char[] text, int index, int count, Path path, float hOffset, float vOffset, Paint paint)

     * @param canvas
     */
    public void drawText(Canvas canvas){
        Paint textPaint = new Paint();          // 创建画笔
        textPaint.setColor(Color.BLACK);        // 设置颜色
        textPaint.setStyle(Paint.Style.FILL);   // 设置样式
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(50);              // 设置字体大小


        //-------1.drawText -------------------------------
        // 文本(要绘制的内容)
        String str = "ABCDE";

        // 参数分别为 (文本 基线x 基线y 画笔)
           //canvas.drawText(str,200,500,textPaint);

        // 参数分别为 (字符串 开始截取位置 结束截取位置 基线x 基线y 画笔)
        //使用start和end指定的区间是前闭后开的，即包含start指定的下标，而不包含end指定的下标，故[1,3)最后获取到的下标只有 下标1 和 下标2 的字符，就是”BC”
           //canvas.drawText(str,1,3,200,500,textPaint);



        //-------2.drawPosText  不推荐使用---------------------------

        canvas.drawPosText(str,new float[]{
                100,100,    // 第一个字符位置
                200,200,    // 第二个字符位置
                300,300,    // ...
                400,400,
                500,500
        },textPaint);


        // ----- 3.drawTextOnPath --------------------------------

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       // drawPicture(canvas);
       // drawBitmap(canvas);
       drawText(canvas);


    }




    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth= w;
        mHeight = h;
    }








}
