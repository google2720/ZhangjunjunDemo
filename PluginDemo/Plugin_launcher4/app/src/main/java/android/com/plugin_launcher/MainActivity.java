package android.com.plugin_launcher;

import android.com.plugin_launcher.slidegroup.SlideGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity {

    private SlideGroup slideGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slideGroup = (SlideGroup) findViewById(R.id.sl);

//        slideGroup.addView(new FmCard(this));
//        slideGroup.addView(new FmCard(this));
//        slideGroup.addView(new FmCard(this));
//        slideGroup.addView(new FmCard(this));
//        slideGroup.addView(new FmCard(this));


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
