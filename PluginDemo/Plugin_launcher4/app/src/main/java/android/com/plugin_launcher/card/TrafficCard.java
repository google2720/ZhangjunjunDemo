package android.com.plugin_launcher.card;

import android.com.plugin_launcher.R;
import android.content.Context;




/**
 * Created by zhangjunjun on 2017/3/29.
 */

public class TrafficCard extends BaseCard {

    public TrafficCard(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_traffic_card;
    }

    @Override
    protected void setCardBackgroundResource() {

    }

}
