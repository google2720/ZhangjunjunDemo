package android.com.host;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginApplication;

/**
 * Created by zhangjunjun on 2018/4/24.
 */

public class SampleApplication extends RePluginApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        RePlugin.install("/sdcard/plugin1.apk");
        RePlugin.install("/sdcard/plugin_launcher4.apk");

    }
}
