package nanodevlab.imran.aqwalehazratalias;


import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewDuasActivity extends AppCompatActivity {

    private TextView textView;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_duas);

        seekBar = findViewById(R.id.textSizeSeekBar);
        textView = findViewById(R.id.duaText);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        float fs = prefs.getFloat("fontSize", 80);
        seekBar.setProgress((int)fs);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,seekBar.getProgress());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences  prefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed = prefs.edit();
                ed.putFloat("fontSize", textView.getTextSize());
                ed.apply();
            }
        });



        Typeface typeface=Typeface.createFromAsset(getAssets(), "PDMS_Saleem_QuranFont.ttf");

        String duaName = getIntent().getStringExtra("duaName");
        String duaText = getIntent().getStringExtra("duaText");
        try {
            textView.setText(duaText);
            textView.setTypeface(typeface);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


}



