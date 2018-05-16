package android.com.plugin_launcher.widget;

import android.annotation.SuppressLint;
import android.com.plugin_launcher.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;



/**
 * Created by zhangjunjun on 2018/1/22.
 */

@SuppressLint("AppCompatCustomView")
public class ClickButton extends TextView {


    private static final String TAG = "ClickButton";
    private String skinRes;
    int pressedDrawableId = -1;
    int normalDrawableId = -1;
    int disableDrawableId = -1;
    int pressedTextColorId = -1;
    int normalTextColor = -1;
    int disableTextColor = -1;
    View.OnClickListener onClickListener;

    public ClickButton(Context context) {
        this(context, null);
    }

    public ClickButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClickView);
        pressedDrawableId = a.getResourceId(R.styleable.ClickView_pressedBackground, -1);
        normalDrawableId = a.getResourceId(R.styleable.ClickView_normalBackground, -1);
        disableDrawableId = a.getResourceId(R.styleable.ClickView_disableBackground, -1);
        pressedTextColorId = a.getResourceId(R.styleable.ClickView_pressedTextColor, -1);
        normalTextColor = a.getResourceId(R.styleable.ClickView_normalTextColor, -1);
        disableTextColor = a.getResourceId(R.styleable.ClickView_disableTextColor, -1);


        if(normalDrawableId!=-1){
            setBackgroundResource(normalDrawableId);
        }
        if(normalTextColor!=-1){
            setTextColor(normalTextColor);
        }



    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Drawable drawable = getBackground();
            if (drawable != null) {
                if (normalDrawableId != -1 && !isSelected()) {
                    setBackgroundResource(normalDrawableId);
                }
                drawable.setAlpha(255);
            }

            if (normalTextColor != -1 && !isSelected()) {
                setTextColor(normalTextColor);
            }
        }
    };




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isEnabled()){
                Drawable drawable = getBackground();
                switch (event.getAction()){

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                         //避免闪烁
                         postDelayed(runnable,30);
//                            if (drawable != null) {
//                                if (normalDrawableId != -1) {
//                                    setBackgroundResource(normalDrawableId);
//                                }
//                                drawable.setAlpha(255);
//                            }
//
//                            if (normalTextColor != -1) {
//                                setTextColor(getResources().getColor(normalTextColor));
//                            }

                        break;

                    case MotionEvent.ACTION_DOWN:
                            if (drawable != null) {
                                if (pressedDrawableId == -1) {
                                    drawable.setAlpha(150);
                                } else {
                                    setBackgroundResource(pressedDrawableId);
                                }

                            }

                        if(pressedTextColorId!=-1){
                            setTextColor(pressedTextColorId);
                        }
                        break;
                }

        }
        return onClickListener!=null?super.onTouchEvent(event):true;
    }

    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {
        super.setOnClickListener(onClickListener = l);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if(selected){
            if(pressedDrawableId!=-1) {
                setBackgroundResource(pressedDrawableId);
            }

            if(pressedTextColorId!=-1){
                setTextColor(pressedTextColorId);
            }
        }else {
            if(normalDrawableId!=-1) {
                setBackgroundResource(normalDrawableId);
            }

            if(normalTextColor!=-1){
                setTextColor(normalTextColor);
            }
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void setTextColor(int color) {
        String resName;
        if (TextUtils.isEmpty(skinRes) || TextUtils.equals(skinRes,"day")) {
            resName = getResources().getResourceTypeName(color);
        }else {
            resName = getResources().getResourceEntryName(color)+"_"+skinRes;
        }

        int c = getContext().getResources().getIdentifier(resName, "color", getContext().getPackageName());
        if(c!=0) {
            super.setTextColor(getResources().getColor(c));
        }
    }



    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(enabled){
            if(normalDrawableId!=-1) {
                setBackgroundResource(normalDrawableId);
            }
            if(normalTextColor!=-1){
                setTextColor(normalTextColor);
            }

        }else {
            if(disableDrawableId!=-1) {
                setBackgroundResource(disableDrawableId);
            }

            if(disableTextColor!=-1){
                setTextColor(normalTextColor);
            }
        }
    }

    public void setDisableDrawableId(int disableDrawableId) {
        this.disableDrawableId = disableDrawableId;
        setEnabled(isEnabled());
    }

    public void setPressedDrawableId(int pressedDrawableId) {
        this.pressedDrawableId = pressedDrawableId;
        setSelected(isSelected());
    }

    public void setNormalDrawableId(int normalDrawableId) {
        this.normalDrawableId = normalDrawableId;
        setSelected(isSelected());
    }

    public void setPressedTextColorId(int pressedTextColorId) {
        this.pressedTextColorId = pressedTextColorId;
        setSelected(isSelected());
    }

    public void setNormalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
        setSelected(isSelected());
    }

    public void setDisableTextColor(int disableTextColor) {
        this.disableTextColor = disableTextColor;
        setEnabled(isEnabled());
    }
}
