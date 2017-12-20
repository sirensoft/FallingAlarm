package utehn.app.fallingalarm;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView tv_x,tv_y,tv_z,tv_res,tv_stat;
    SensorManager sensorManager;
    Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        tv_x = (TextView)findViewById(R.id.tv_x);
        tv_y = (TextView)findViewById(R.id.tv_y);
        tv_z = (TextView)findViewById(R.id.tv_z);
        tv_res = (TextView)findViewById(R.id.tv_res);
        tv_stat = (TextView)findViewById(R.id.tv_stat);


    }

    public void onResume() {
        super.onResume();
        sensorManager.registerListener(accelListener, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(accelListener);
    }

    SensorEventListener accelListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) { }

        public void onSensorChanged(SensorEvent event) {
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];
            tv_x.setText("X: "+Double.toString(x));
            tv_y.setText("Y: "+Double.toString(y));
            tv_z.setText("Z: "+Double.toString(z));
            double xx = x*x;
            double yy = y*y;
            double zz = z*z;
            double sum = xx+yy+zz;
            double A = Math.sqrt(sum);
            tv_res.setText("A: "+Double.toString(A));
            if(A>16){
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 50);
                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                tv_stat.append(Double.toString(A)+"\n");
            }
        }
    };

}
