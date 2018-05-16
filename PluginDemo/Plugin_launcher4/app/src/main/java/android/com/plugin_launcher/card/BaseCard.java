package android.com.plugin_launcher.card;

import android.com.plugin_launcher.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;




/**
 * Created by zhangjunjun on 2017/3/29.
 */

public abstract class BaseCard extends FrameLayout {

    private LayoutInflater layoutInflater;

    public BaseCard(Context context) {
        super(context);
        init();
    }

    public BaseCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseCard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        if(getLayoutId()!=-1) {
            layoutInflater = LayoutInflater.from(getContext());
            layoutInflater.inflate(getLayoutId(), this, true);
        }
        setCardBackgroundResource();
    }


    protected  int getLayoutId(){
        return -1;
    }

    protected  void setCardBackgroundResource(){
        setBackgroundResource(R.drawable.car_bg);
    }

    protected  void cardOnClick(){
    }

    @Override
    public boolean performClick() {
        cardOnClick();
        return super.performClick();
    }

}
