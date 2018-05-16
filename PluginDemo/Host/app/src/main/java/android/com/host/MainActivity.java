package android.com.host;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.qihoo360.replugin.RePlugin;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerSkinReceiver();

        String skin = SharePreference.getStringValue(this,"skin");
        if(skin.equals("one")){
            startSkinI();
        }else if(skin.equals("two")){
            startSkinII();
        }else {
            startSkinI();
        }
    }

    private BroadcastReceiver skinReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int skin = intent.getIntExtra("skin", -1);
            if (skin == 1) {
                startSkinI();
            } else if (skin == 2) {
                startSkinII();
            }

//            Intent MyIntent = new Intent(Intent.ACTION_MAIN);
//            MyIntent.addCategory(Intent.CATEGORY_HOME);
//            MyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(MyIntent);
        }
    };


    public void startSkinI() {
        RePlugin.startActivity(MainActivity.this, RePlugin.createIntent("android.com" +
                ".plugin_launcher", "android.com.plugin_launcher.MainActivity"));

        SharePreference.putStringValue(this,"skin","one");
    }

    public void startSkinII() {
        RePlugin.startActivity(MainActivity.this, RePlugin.createIntent("android.com.plugin1",
                "android.com.plugin1.MainActivity"));

        SharePreference.putStringValue(this,"skin","two");
    }

    public void registerSkinReceiver() {
        IntentFilter intentFilter = new IntentFilter("yunovo.skin.setting");
        intentFilter.setPriority(1000);
        registerReceiver(skinReceiver, intentFilter);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
