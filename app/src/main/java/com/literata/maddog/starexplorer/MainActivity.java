package com.literata.maddog.starexplorer;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private MainView mView;
    private final int mVibTime = 25;
    private Vibrator myVib;
    Button b_Button1;
    Button b_Button2;
    Button b_Button3;
    Button b_Button4;
    Button b_Button5;
    
    Button b_Button6;
    Button b_Button7;
    Button b_Button8;
    Button b_Button9;
    Button b_ButtonA;
    
    public enum RotationEnum {
        ROTATE_NONE,
        ROTATE_PLANET,
        ROTATE_LIGHT,
        ROTATE_CAMERA,
        ROTATE_PLANET_LIGHT,
        ROTATE_PLANET_CAMERA,
        ROTATE_LIGHT_CAMERA,
        ROTATE_PLANET_LIGHT_CAMERA;

        public RotationEnum next() {
            RotationEnum rots[] = RotationEnum.values();  
            int ordinal = this.ordinal();  
            ordinal = ++ordinal % RotationEnum.values().length;  
            return rots[ordinal];
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        setContentView(R.layout.activity_main);

        mView = (MainView) findViewById(R.id.tmainView);

        b_Button1 = (Button) findViewById(R.id.button1);
        b_Button1.setText(R.string.lbl_Button1);
        b_Button1.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
        b_Button1.setTextColor(Color.GREEN);

        b_Button2 = (Button) findViewById(R.id.button2);
        b_Button2.setText(R.string.lbl_Button2);
        b_Button2.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
        b_Button2.setTextColor(Color.BLACK);

        b_Button3 = (Button) findViewById(R.id.button3);
        b_Button3.setText(R.string.lbl_Button3);
        b_Button3.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
        b_Button3.setTextColor(Color.GREEN);

        b_Button4 = (Button) findViewById(R.id.button4);
        b_Button4.setText(R.string.lbl_Button4);
        b_Button4.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
        b_Button4.setTextColor(Color.GREEN);

        b_Button5 = (Button) findViewById(R.id.button5);
        b_Button5.setText(R.string.lbl_Button5);
        b_Button5.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
        b_Button5.setTextColor(Color.GREEN);

        b_Button6 = (Button) findViewById(R.id.button6);
        b_Button6.setText(R.string.lbl_Button6);
        b_Button6.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
        b_Button6.setTextColor(Color.GREEN);

        b_Button7 = (Button) findViewById(R.id.button7);
        b_Button7.setText(R.string.lbl_Button7);
        b_Button7.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
        b_Button7.setTextColor(Color.GREEN);

        b_Button8 = (Button) findViewById(R.id.button8);
        b_Button8.setText(R.string.lbl_Button8);
        b_Button8.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
        b_Button8.setTextColor(Color.GREEN);

        b_Button9 = (Button) findViewById(R.id.button9);
        b_Button9.setText(R.string.lbl_Button9);
        b_Button9.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
        b_Button9.setTextColor(Color.GREEN);

        b_ButtonA = (Button) findViewById(R.id.button10);
        b_ButtonA.setText(R.string.lbl_ButtonA);
        b_ButtonA.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
        b_ButtonA.setTextColor(Color.GREEN);

        b_Button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(mVibTime);
                handleSphereToggle(b_Button1);
            }
        });

        b_Button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(mVibTime);
                handleOrbitToggle(b_Button2);
            }
        });

        b_Button3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(mVibTime);
                handleResetAction(b_Button3);
            }
        });

        b_Button4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(mVibTime);
                handleZPosAction(b_Button4);
            }
        });

        b_Button5.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(mVibTime);
                handleZNegAction(b_Button4);
            }
        });

        b_Button6.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(mVibTime);
                handleXPosAction(b_Button6);
            }
        });

        b_Button7.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(mVibTime);
                handleXNegAction(b_Button7);
            }
        });

        b_Button8.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(mVibTime);
                handleYPosAction(b_Button8);
            }
        });

        b_Button9.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(mVibTime);
                handleYNegAction(b_Button7);
            }
        });

        b_ButtonA.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(mVibTime);
                handleOriginAction(b_ButtonA);
            }
        });
    }

    private void handleSphereToggle(Button b) {
        b.setTextColor(mView.toggleSphereVisible() ? Color.GREEN : Color.BLACK);
    }

    private void handleOrbitToggle(Button b) {
        b.setTextColor(mView.toggleCameraRotation() ? Color.GREEN : Color.BLACK);
    }

    private void handleResetAction(Button b) {
        mView.doReset();
    }

    private void handleZPosAction(Button b) {
        mView.getRenderer().getSceneManager().getActiveCamera().adjustZVal(+10.0f);
    }

    private void handleZNegAction(Button b) {
        mView.getRenderer().getSceneManager().getActiveCamera().adjustZVal(-10.0f);
    }

    private void handleXPosAction(Button b) {
        mView.getRenderer().getSceneManager().getActiveCamera().adjustXVal(+10.0f);
    }

    private void handleXNegAction(Button b) {
        mView.getRenderer().getSceneManager().getActiveCamera().adjustXVal(-10.0f);
    }

    private void handleYPosAction(Button b) {
        mView.getRenderer().getSceneManager().getActiveCamera().adjustYVal(+10.0f);
    }

    private void handleYNegAction(Button b) {
        mView.getRenderer().getSceneManager().getActiveCamera().adjustYVal(-10.0f);
    }

    private void handleOriginAction(Button b) {
        mView.getRenderer().getSceneManager().getActiveCamera().reset();
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}