package com.app.petron.preuba2temperatura;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textTEMPERATURE_available, textTEMPERATURE_reading;
    TextView textAMBIENT_TEMPERATURE_available, textAMBIENT_TEMPERATURE_reading;
    TextView temperatura_am,temperatura_nor;
    TextView tvgradosnor,tvgradosam;
    TextView tvgradoswichc,tvgradosswichf,tvgradosswichc1,tvgradosswichf1;
    ImageView tempnormal,temamb;
    Switch sw1,sw2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textTEMPERATURE_available
                = (TextView)findViewById(R.id.TEMPERATURE_available);
        textTEMPERATURE_reading
                = (TextView)findViewById(R.id.TEMPERATURE_reading);
        textAMBIENT_TEMPERATURE_available
                = (TextView)findViewById(R.id.AMBIENT_TEMPERATURE_available);
        textAMBIENT_TEMPERATURE_reading
                = (TextView)findViewById(R.id.AMBIENT_TEMPERATURE_reading);
        tempnormal = (ImageView) findViewById(R.id.imagen_temperatura_normal);
        temamb = (ImageView) findViewById(R.id.imagen_temperatura_ambiente);
        temperatura_am = (TextView)findViewById(R.id.tv_Temperatura_amb);
        temperatura_nor = (TextView)findViewById(R.id.tv_Temperatura);
        sw1 = (Switch)findViewById(R.id.switch1);
        sw2 = (Switch)findViewById(R.id.switch2);
        tvgradosnor = (TextView)findViewById(R.id.tvgrados1);
        tvgradosam = (TextView)findViewById(R.id.tvgrados2);
        tvgradoswichc = (TextView)findViewById(R.id.tvgradosc);
        tvgradosswichf = (TextView)findViewById(R.id.tvgradosf);
        tvgradosswichc1 = (TextView)findViewById(R.id.tvgradosc1);
        tvgradosswichf1 = (TextView)findViewById(R.id.tvgradosf2);

        SensorManager mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        Sensor TemperatureSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        if(TemperatureSensor != null){
            textTEMPERATURE_available.setText(R.string.sensor_tem_normal);
            tempnormal.setImageResource(R.mipmap.ok);
            mySensorManager.registerListener(
                    TemperatureSensorListener,
                    TemperatureSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        }else{
            textTEMPERATURE_available.setText(R.string.sensor_tem_normal);
            tempnormal.setImageResource(R.mipmap.aspa);
            temperatura_nor.setText("");
            tvgradoswichc.setText("");
            tvgradosswichf.setText("");
            sw1.setVisibility(View.INVISIBLE);
        }

        Sensor AmbientTemperatureSensor
                = mySensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(AmbientTemperatureSensor != null){
            textAMBIENT_TEMPERATURE_available.setText(R.string.sensor_tem_am);
            temamb.setImageResource(R.mipmap.ok);
            mySensorManager.registerListener(
                    AmbientTemperatureSensorListener,
                    AmbientTemperatureSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            textAMBIENT_TEMPERATURE_available.setText(R.string.sensor_tem_am);
            temamb.setImageResource(R.mipmap.aspa);
            temperatura_am.setText("");
            tvgradosswichc1.setText("");
            tvgradosswichf1.setText("");
            sw2.setVisibility(View.INVISIBLE);
        }
    }

    private final SensorEventListener TemperatureSensorListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_TEMPERATURE){
                final float [] values = event.values;
                final double faren = values[0]*1.8 +32;
                //convertimos float a Strong con solo 1 decimal
                final String convertido;
                convertido = String.format("%.1f",values[0]);
                final String grafaren;
                grafaren = String.format("%.1f",faren);
                final String[] mostrar_normal = new String[1];

                //colores temperatura

                if(values[0] >10 || values[0]< 20){
                    textTEMPERATURE_reading.setTextColor(getResources().getColor(R.color.color_normal));
                }
                if (values[0]>= 20){
                    textTEMPERATURE_reading.setTextColor(getResources().getColor(R.color.colo_calor));
                }
                if (values[0]<= 10){
                    textTEMPERATURE_reading.setTextColor(getResources().getColor(R.color.color_frio));
                }

                //color texto
                if(values[0] >10 || values[0]< 20){
                    temperatura_nor.setTextColor(getResources().getColor(R.color.color_normal));
                    tvgradosnor.setTextColor(getResources().getColor(R.color.color_normal));
                }
                if (values[0]>= 20){
                    temperatura_nor.setTextColor(getResources().getColor(R.color.colo_calor));
                    tvgradosnor.setTextColor(getResources().getColor(R.color.colo_calor));
                }
                if (values[0]<= 10){
                    temperatura_nor.setTextColor(getResources().getColor(R.color.color_frio));
                    tvgradosnor.setTextColor(getResources().getColor(R.color.color_frio));
                }

                //poner el swicht a escuchar

                sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            mostrar_normal[0] = grafaren;
                            tvgradosnor.setText("ºF");
                        }else {
                            mostrar_normal[0]=convertido;
                            tvgradosnor.setText("ºC");
                        }

                    }
                });

                if(sw1.isChecked()){
                    mostrar_normal[0] = grafaren;
                    tvgradosnor.setText("ºF");
                }else {
                    mostrar_normal[0]= convertido;
                    tvgradosnor.setText("ºC");
                }



                textTEMPERATURE_reading.setText(mostrar_normal[0]);
            }
        }

    };

    private final SensorEventListener AmbientTemperatureSensorListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){

                final float [] values = event.values;
                final double faren = values[0]*1.8 +32;
                //convertimos float a String con solo 1 decimal
                final String convertido1;
                convertido1 = String.format("%.1f",values[0]);
                final String grafaren1;
                grafaren1 = String.format("%.1f",faren);
                final String[] mostrar_ambiente = new String[1];

                //colores temperatura

                if(values[0] >10 || values[0]< 20){
                    textAMBIENT_TEMPERATURE_reading.setTextColor(getResources().getColor(R.color.color_normal));
                }
                if (values[0]>= 20){
                    textAMBIENT_TEMPERATURE_reading.setTextColor(getResources().getColor(R.color.colo_calor));
                }
                if (values[0]<= 10){
                    textAMBIENT_TEMPERATURE_reading.setTextColor(getResources().getColor(R.color.color_frio));
                }
                //colores texto temperatura
                if(values[0] >10 || values[0]< 20){
                    temperatura_am.setTextColor(getResources().getColor(R.color.color_normal));
                    tvgradosam.setTextColor(getResources().getColor(R.color.color_normal));
                }
                if (values[0]>= 20){
                    temperatura_am.setTextColor(getResources().getColor(R.color.colo_calor));
                    tvgradosam.setTextColor(getResources().getColor(R.color.colo_calor));
                }
                if (values[0]<= 10){
                    temperatura_am.setTextColor(getResources().getColor(R.color.color_frio));
                    tvgradosam.setTextColor(getResources().getColor(R.color.color_frio));
                }

                //poner el swicht a escuchar

                sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView1, boolean isChecked) {
                        if(isChecked){
                            mostrar_ambiente[0] = grafaren1;
                            tvgradosam.setText("ºF");
                        }else {
                            mostrar_ambiente[0]=convertido1;
                            tvgradosam.setText("ºC");
                        }

                    }
                });

                if(sw2.isChecked()){
                    mostrar_ambiente[0] = grafaren1;
                    tvgradosam.setText("ºF");
                }else {
                    mostrar_ambiente[0]= convertido1;
                    tvgradosam.setText("ºC");
                }

                textAMBIENT_TEMPERATURE_reading.setText(mostrar_ambiente[0]);
            }
        }

    };
    }

