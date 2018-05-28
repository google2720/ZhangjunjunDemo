package android.com.path;

import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    SeekBar lightSeekBar;
    SeekBar nightSeekBar;
    SurfacePathView pathView;


    float startProgress,endProgress;
    float startNightProgress,endNightProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightSeekBar = findViewById(R.id.lightSeekBar);
        nightSeekBar = findViewById(R.id.nightSeekBar);
        pathView = findViewById(R.id.pathView);


        Path path1 = new Path();
        path1.moveTo(310, 50);
        path1.lineTo(310, 200);
        path1.lineTo(210, 300);
        path1.lineTo(210, 400);
        path1.lineTo(310, 500);
        path1.lineTo(310, 600);
        path1.lineTo(210, 700);
        path1.lineTo(210, 800);
        path1.lineTo(310, 900);
        path1.lineTo(310, 1000);
        path1.lineTo(210, 1100);
        path1.lineTo(210, 1200);
        path1.lineTo(310, 1300);
        path1.lineTo(310, 1400);




        pathView.setPath(path1);

        lightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float result;
                result = progress/100f;

                endProgress = result;

                if(endProgress > startProgress){
                    startProgress = endProgress - 0.5f;
                }

                if(startProgress<0){
                    startProgress = 0;
                }

                pathView.setLightLineProgress(startProgress,endProgress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        nightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float result;
                result = progress/100f;
                endNightProgress = result;
                pathView.setDarkLineProgress(startNightProgress,endNightProgress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pathView.setDarkLineProgress(0,1f);
        pathView.startAnimation();
    }
}
