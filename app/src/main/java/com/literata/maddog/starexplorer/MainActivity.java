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
		b_Button1.setTextColor(mView.getToggleSphereVisible() == true ? Color.GREEN : Color.BLACK);

		b_Button2 = (Button) findViewById(R.id.button2);
		b_Button2.setText(R.string.lbl_Button2);
		b_Button2.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_Button2.setTextColor(Color.GREEN);
		
		b_Button3 = (Button) findViewById(R.id.button3);
		b_Button3.setText(R.string.lbl_Button3);
		b_Button3.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_Button3.setTextColor(mView.getToggleLockX() == true ? Color.GREEN : Color.BLACK);

		b_Button4 = (Button) findViewById(R.id.button4);
		b_Button4.setText(R.string.lbl_Button4);
		b_Button4.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_Button4.setTextColor(mView.getToggleLockY() == true ? Color.GREEN : Color.BLACK);

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
		b_Button7.setTextColor(mView.getTogglePlanetRotation() ? Color.GREEN : Color.BLACK);

		b_Button8 = (Button) findViewById(R.id.button8);
		b_Button8.setText(R.string.lbl_Button8);
		b_Button8.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_Button8.setTextColor(mView.getToggleLightRotation() ? Color.GREEN : Color.BLACK);

		b_Button9 = (Button) findViewById(R.id.button9);
		b_Button9.setText(R.string.lbl_Button9);
		b_Button9.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_Button9.setTextColor(mView.getToggleCameraRotation() ? Color.GREEN : Color.BLACK);

		b_ButtonA = (Button) findViewById(R.id.button10);
		b_ButtonA.setText(R.string.lbl_ButtonA);
		b_ButtonA.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_ButtonA.setTextColor(mView.getToggleRefs() ? Color.GREEN : Color.BLACK);
		
		b_Button1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVib.vibrate(mVibTime);
				b_Button1.setTextColor(mView.toggleSphereVisible() ? Color.GREEN : Color.BLACK);
			}
		});
		
		b_Button2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVib.vibrate(mVibTime);
				b_Button2.setText(R.string.lbl_Button2 + Integer.toString(mView.toggleMagnitude()));
				b_Button2.setTextColor(Color.GREEN);
			}
		});

		b_Button3.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				b_Button3.setTextColor(Color.YELLOW);
				myVib.vibrate(mVibTime);
				b_Button3.setTextColor(mView.toggleLockX() ? Color.GREEN : Color.BLACK);
			}
		});

		b_Button4.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVib.vibrate(mVibTime);
				b_Button4.setTextColor(mView.toggleLockY() ? Color.GREEN : Color.BLACK);
			}
		});

		b_Button5.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				b_Button5.setTextColor(Color.YELLOW);
				myVib.vibrate(mVibTime);
				b_Button5.setTextColor(Color.GREEN);
				mView.doReset();
			}
		});

		b_ButtonA.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				b_ButtonA.setTextColor(Color.YELLOW);
				myVib.vibrate(mVibTime);
				b_ButtonA.setTextColor(mView.toggleRefs() ? Color.GREEN : Color.BLACK);
			}
		});

		b_Button6.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVib.vibrate(mVibTime);
				b_Button6.setText(mView.toggleActiveCamera());
			}
		});

		b_Button7.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVib.vibrate(mVibTime);
				b_Button7.setTextColor(mView.togglePlanetRotation() ? Color.GREEN : Color.BLACK);
			}
		});

		b_Button8.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVib.vibrate(mVibTime);
				b_Button8.setTextColor(mView.toggleLightRotation() ? Color.GREEN : Color.BLACK);
			}
		});

		b_Button9.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVib.vibrate(mVibTime);
				b_Button9.setTextColor(mView.toggleCameraRotation() ? Color.GREEN : Color.BLACK);
			}
		});
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