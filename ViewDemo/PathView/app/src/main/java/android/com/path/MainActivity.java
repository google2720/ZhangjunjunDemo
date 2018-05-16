package android.com.path;

import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    SeekBar lightSeekBar;
    SeekBar nightSeekBar;
    PathView pathView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightSeekBar = findViewById(R.id.lightSeekBar);
        nightSeekBar = findViewById(R.id.nightSeekBar);
        pathView = findViewById(R.id.pathView);


        Path path1 = new Path();

        path1.lineTo(600, 30);
        path1.lineTo(600, 100);
        path1.lineTo(650, 200);
        path1.lineTo(650, 400);






        pathView.setPath(path1);

        lightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pathView.setLightLineProgress(0,progress);
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
                pathView.setDarkLineProgress(0,progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
