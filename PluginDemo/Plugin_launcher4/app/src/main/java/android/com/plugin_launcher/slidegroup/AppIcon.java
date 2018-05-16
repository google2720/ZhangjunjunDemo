package android.com.plugin_launcher.slidegroup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;



/**
 * Created by zhangjunjun on 2018/2/6.
 */

@SuppressLint("AppCompatCustomView")
public class AppIcon extends TextView {
    private IconCache iconCache;

    public AppIcon(Context context) {
        super(context);
    }

    public AppIcon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AppIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    ResolveInfo info;
    public ResolveInfo getInfo() {
        return info;
    }

    public void setInfo(ResolveInfo info) {
        this.info = info;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iconCache = new IconCache(getContext());
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Drawable drawable = getBackground();
                if(drawable!=null) {
                    if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                        drawable.setAlpha(255);
                    }
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        drawable.setAlpha(100);
                    }
                }
                return false;
            }
        });
    }

}
