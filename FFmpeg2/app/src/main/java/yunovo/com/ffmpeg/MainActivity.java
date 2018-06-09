package yunovo.com.ffmpeg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private TextView tv;
    private Button start;
    private long startTime;
    private VideoCompress videoCompress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.sample_text);
        start = (Button) findViewById(R.id.start);

        videoCompress = new VideoCompress();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("转换中....");
                startTime = System.currentTimeMillis();
                videoCompress.startCompress("/sdcard/test.mp4", "/sdcard/videoOut/compress.mp4", new VideoCompress.VideoCompressListener() {

                    @Override
                    public void onSucceed() {
                        long userTime = System.currentTimeMillis()  - startTime;
                        tv.setText("转换完成 用时："+userTime/1000 +" 秒");
                    }

                    @Override
                    public void onFail(String msg) {
                        tv.setText(msg);
                    }
                });

            }
        });

    }


}




