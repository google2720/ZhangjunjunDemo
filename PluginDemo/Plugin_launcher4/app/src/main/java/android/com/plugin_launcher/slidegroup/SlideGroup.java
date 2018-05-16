package android.com.plugin_launcher.slidegroup;

import android.com.plugin_launcher.BuildConfig;
import android.com.plugin_launcher.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * Created by zhangjunjun on 2017/5/28.
 */

public class SlideGroup extends ViewGroup {

    private static final String TAG = "SlideGroup";


    /**
     * SlideGroup 每页的宽度
     */
    int pageWidth;

    /**
     * SlideGroup 的高度
     */
    int pageHeight;

    /**
     * 总宽度
     */
    int totalWidth;

    /**
     * 卡片宽度
     */
    int cardWidth = 240;

    /**
     * 卡片高度
     */
    int cardHeight = 392;

    /**
     * 每页列数
     */
    int pageRow = 4;

    /**
     * 每页行数
     */
    int pageLine = 1;

    /**
     * 每页卡片数
     */
    int pageNumber = pageLine * pageRow;

    /**
     * 列间隔
     */
    int rowSpace;

    /**
     * 行间隔
     */
    int lineSpace;

    /**
     * 总页数
     */
    int totalPage;

    int lastTotalPage;

    /**
     * 当前在第几页
     */
     int mCurrentPage;

    private disPathEvent disPathEvent;

    /**
     * 页码指示器相关
     */
    private int mPageIndicatorViewId;
    public PageIndicator mPageIndicator;
    public boolean mAllowPagedViewAnimations = true;
    int tempWidth;
    int tempHeight;



    public SlideGroup(Context context) {
        this(context, null);
    }

    public SlideGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public SlideGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlideGroup);
        mPageIndicatorViewId = a.getResourceId(R.styleable.SlideGroup_DragPageIndicator, -1);
        pageRow = a.getInt(R.styleable.SlideGroup_pageRow, 4);
        pageLine = a.getInt(R.styleable.SlideGroup_pageLine, 1);
        tempWidth = a.getDimensionPixelSize(R.styleable.SlideGroup_cardWidth, 240);
        tempHeight = a.getDimensionPixelSize(R.styleable.SlideGroup_cardHeight, 392);
        pageNumber = pageLine * pageRow;
        new SlideGroupHelper(this);
    }





    int measurePageIndex = 0;
    int measureLineIndex = 0;
    int measureRowIndex = 0;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        pageWidth = MeasureSpec.getSize(widthMeasureSpec);
        pageHeight = MeasureSpec.getSize(heightMeasureSpec);
        cardWidth = tempWidth;
        cardHeight = tempHeight;

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "pageWidth: " + pageWidth);
            Log.d(TAG, "pageHeight: " + pageHeight);
            Log.d(TAG, "cardWidth: " + cardWidth);
            Log.d(TAG, "cardHeight: " + cardHeight);
        }

        //计算卡片之间的间隔
        //    -|-|-|-|-
        if (cardWidth * pageRow >= pageWidth) {
            setCardWidth(pageWidth / pageRow);
            rowSpace = 0;
        } else {
            //页面宽度  -  页所有卡片总宽度 / 间隔数 = 间隔宽度
            rowSpace = (pageWidth - cardWidth * pageRow) / (pageRow + 1);
        }

        // ---
        //  |
        // ---
        //  |
        // ---
        if (cardHeight * pageLine >= pageHeight) {
            setCardHeight(pageHeight / pageLine);
            lineSpace = 0;
        } else {
            //页面高度  -  页所有卡片总高度 / 间隔数 = 间隔宽度
            if (pageLine > 1) {
                lineSpace = (pageHeight - cardHeight * pageLine) / (pageLine + 1);
            } else {
                lineSpace = 0;
            }
        }


        Log.d(TAG, "rowSpace: " + rowSpace);
        Log.d(TAG, "lineSpace: " + lineSpace);


        //总页数
        int count = getChildCount();
        Log.d(TAG, "getChildCount: " + count);
        totalPage = count % (pageRow * pageLine) == 0 ? count / (pageRow * pageLine) : count / (pageRow * pageLine) + 1;
        Log.d(TAG, "totalPage: " + totalPage);


        for (int i = 0; i < count; ++i) {
            View v = getChildAt(i);
            v.setId(i);
            v.setTag(v.getClass().getName());
            SlideGroupParams lp = (SlideGroupParams) v.getLayoutParams();
            lp.width = cardWidth;
            lp.height = cardHeight;
            v.setLayoutParams(lp);
            measureChild(v, widthMeasureSpec, heightMeasureSpec);

            //当前测量第几页
            measurePageIndex = i / pageNumber;

            //测量当前第几行
            measureLineIndex = (i - measurePageIndex * pageNumber) / pageRow;

            //测量当前第几列
            measureRowIndex = (i - measurePageIndex * pageNumber) % pageRow;

            //Log.d(TAG, "第几行: " + measureLineIndex);
            //Log.d(TAG, "第几页: " + measurePageIndex);
            //Log.d(TAG, "第几列: " + measureRowIndex);

            if (i % pageRow == 0) {
                lp.left = measurePageIndex * pageWidth + rowSpace;
            } else {//其它卡片的左间隔 ,用前一个view的left,可消除累积误差
                View prev = getChildAt(i - 1);
                SlideGroupParams prevParams = (SlideGroupParams) prev.getLayoutParams();
                lp.left = prevParams.left + cardWidth + rowSpace;
            }

            lp.top = (measureLineIndex * (cardHeight + lineSpace))+lineSpace;
        }

        //高度
        pageHeight = pageLine * (cardHeight + lineSpace);
        //总容器的宽度
        totalWidth = pageWidth * totalPage;
        Log.d(TAG, "pageHeight: " + pageHeight + " totalWidth: " + totalWidth);
        setMeasuredDimension(resolveSize(totalWidth, widthMeasureSpec), resolveSize(pageHeight, heightMeasureSpec));
        //初始化页码指示器
        initPageIndicator();

        lastTotalPage = totalPage;

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            View v = getChildAt(i);
            int childWidth = v.getMeasuredWidth();
            int childHeight = v.getMeasuredHeight();
            SlideGroupParams lp = (SlideGroupParams) v.getLayoutParams();
            v.layout(lp.left, lp.top, lp.left + childWidth, lp.top + childHeight);
        }
    }


    @Override
    public void addView(View child) {
        super.addView(child);
    }

    protected PageIndicator.PageMarkerResources getPageIndicatorMarker(int pageIndex) {
        return new PageIndicator.PageMarkerResources();
    }

    protected OnClickListener getPageIndicatorClickListener() {
        return null;
    }

    public void initPageIndicator() {

        ViewGroup parent = (ViewGroup) getParent();
        if (mPageIndicator == null && mPageIndicatorViewId > -1 && lastTotalPage!=totalPage) {

            mPageIndicator = (PageIndicator) parent.findViewById(mPageIndicatorViewId);
            mPageIndicator.removeAllMarkers(mAllowPagedViewAnimations);

            ArrayList<PageIndicator.PageMarkerResources> markers =
                    new ArrayList<PageIndicator.PageMarkerResources>();

            for (int i = 0; i < totalPage; ++i) {
                markers.add(getPageIndicatorMarker(i));
            }

            mPageIndicator.addMarkers(markers, mAllowPagedViewAnimations);
            OnClickListener listener = getPageIndicatorClickListener();
            if (listener != null) {
                mPageIndicator.setOnClickListener(listener);
            }
            mPageIndicator.setContentDescription("");
            mPageIndicator.setActiveMarker(0);
        }
    }


    @Override
    protected boolean addViewInLayout(View child, int index, LayoutParams params) {
        return super.addViewInLayout(child, index, params);
    }

    public void setCardWidth(int cardWidth) {
        this.cardWidth = cardWidth;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCardHeight(int cardHeight) {
        this.cardHeight = cardHeight;
        Log.d(TAG, "setCardHeight: " + cardHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(disPathEvent!=null) {
            disPathEvent.onEvent(event);
        }
        return true;
    }


    @Override
    public void computeScroll() {
        if(disPathEvent!=null) {
            disPathEvent.computeScroll();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(disPathEvent!=null) {
            return disPathEvent.onInterceptTouchEvent(ev);
        }
        else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    public boolean dispatchDragEvent(DragEvent event) {
        if(disPathEvent!=null) {
             disPathEvent.dispatchDragEvent(event);
        }
        return super.dispatchDragEvent(event);
    }




    public static class SlideGroupParams extends MarginLayoutParams {
        public int left = 0;
        public int top = 0;

        public SlideGroupParams(Context arg0, AttributeSet arg1) {
            super(arg0, arg1);
        }

        public SlideGroupParams(int arg0, int arg1) {
            super(arg0, arg1);
        }

        public SlideGroupParams(LayoutParams arg0) {
            super(arg0);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new SlideGroupParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new SlideGroupParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }

   @Override
    protected LayoutParams generateLayoutParams(
            LayoutParams p) {
        return new SlideGroupParams(p);
    }


    /**
     * 页码监听接口
     */
    public OnPageChangerListener onPageChangerListener;
    public interface OnPageChangerListener {
        void onPageChange(int item);
    }



    /**
     * 单卡片点击监听接口
     */
    public OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onClick(View v, int item);
    }

    public interface disPathEvent {
         void onEvent(MotionEvent event);
         boolean onInterceptTouchEvent(MotionEvent event);
         void dispatchDragEvent(DragEvent event);
         void computeScroll();
    }
    public void setDisPathEvent(disPathEvent listener) {
        this.disPathEvent = listener;
    }

    public void setOnPageChangerListener(OnPageChangerListener listener) {
        this.onPageChangerListener = listener;
    }

    public void setOnItemClick(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}


