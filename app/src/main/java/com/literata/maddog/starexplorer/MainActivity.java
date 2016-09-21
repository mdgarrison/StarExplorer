package com.literata.maddog.starexplorer;

import com.literata.maddog.starexplorer.R;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private MainView mView;
    private final int mVibTime = 25;
    private Vibrator myVib;
    Button b_Sphere;
    Button b_Mag;
    Button b_LockX;
    Button b_LockY;
    Button b_Reset;
    
    Button b_Pov;
    Button b_PlanetRot;
    Button b_LightRot;
    Button b_CameraRot;
    Button b_Refs;
    
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

    RotationEnum rotEnum = RotationEnum.ROTATE_NONE;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

		setContentView(R.layout.activity_main);

		mView = (MainView) findViewById(R.id.tmainView);
		
		b_Sphere = (Button) findViewById(R.id.button1);
		b_Sphere.setText("Sphere");
		b_Sphere.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_Sphere.setTextColor(mView.getToggleSphereVisible() == true ? Color.GREEN : Color.BLACK);

		b_Mag = (Button) findViewById(R.id.button2);
		b_Mag.setText("Mag:" + Integer.toString(mView.getToggleMagnitude()));
		b_Mag.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_Mag.setTextColor(Color.GREEN);
		
		b_LockX = (Button) findViewById(R.id.button3);
		b_LockX.setText("LockX");
		b_LockX.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_LockX.setTextColor(mView.getToggleLockX() == true ? Color.GREEN : Color.BLACK);

		b_LockY = (Button) findViewById(R.id.button4);
		b_LockY.setText("LockY");
		b_LockY.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_LockY.setTextColor(mView.getToggleLockY() == true ? Color.GREEN : Color.BLACK);

		b_Reset = (Button) findViewById(R.id.button5);
		b_Reset.setText("Reset");
		b_Reset.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_Reset.setTextColor(Color.GREEN);
		
		b_Pov = (Button) findViewById(R.id.button6);
		b_Pov.setText("Pov");
		b_Pov.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_Pov.setTextColor(Color.GREEN);

		b_PlanetRot = (Button) findViewById(R.id.button7);
		b_PlanetRot.setText("P.Rot");
		b_PlanetRot.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_PlanetRot.setTextColor(mView.getTogglePlanetRotation() ? Color.GREEN : Color.BLACK);

		b_LightRot = (Button) findViewById(R.id.button8);
		b_LightRot.setText("L.Rot");
		b_LightRot.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_LightRot.setTextColor(mView.getToggleLightRotation() ? Color.GREEN : Color.BLACK);

		b_CameraRot = (Button) findViewById(R.id.button9);
		b_CameraRot.setText("C.Rot");
		b_CameraRot.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_CameraRot.setTextColor(mView.getToggleCameraRotation() ? Color.GREEN : Color.BLACK);

		b_Refs = (Button) findViewById(R.id.button10);
		b_Refs.setText("Refs");
		b_Refs.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.silverbuttonbig, null));
		b_Refs.setTextColor(mView.getToggleRefs() ? Color.GREEN : Color.BLACK);
		
		b_Sphere.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVib.vibrate(mVibTime);
				b_Sphere.setTextColor(mView.toggleSphereVisible() ? Color.GREEN : Color.BLACK);
			}
		});
		
		b_Mag.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVib.vibrate(mVibTime);
				b_Mag.setText("Mag:" + Integer.toString(mView.toggleMagnitude()));
				b_Mag.setTextColor(Color.GREEN);
			}
		});

		b_LockX.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				b_LockX.setTextColor(Color.YELLOW);
				myVib.vibrate(mVibTime);
				b_LockX.setTextColor(mView.toggleLockX() ? Color.GREEN : Color.BLACK);
			}
		});

		b_LockY.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVib.vibrate(mVibTime);
				b_LockY.setTextColor(mView.toggleLockY() ? Color.GREEN : Color.BLACK);
			}
		});

		b_Reset.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				b_Reset.setTextColor(Color.YELLOW);
				myVib.vibrate(mVibTime);
				b_Reset.setTextColor(Color.GREEN);
				mView.doReset();
			}
		});

		b_Refs.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				b_Refs.setTextColor(Color.YELLOW);
				myVib.vibrate(mVibTime);
				b_Refs.setTextColor(mView.toggleRefs() ? Color.GREEN : Color.BLACK);
			}
		});

		b_Pov.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVib.vibrate(mVibTime);
				b_Pov.setText(mView.toggleActiveCamera());
			}
		});

		b_PlanetRot.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVib.vibrate(mVibTime);
				b_PlanetRot.setTextColor(mView.togglePlanetRotation() ? Color.GREEN : Color.BLACK);
			}
		});

		b_LightRot.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVib.vibrate(mVibTime);
				b_LightRot.setTextColor(mView.toggleLightRotation() ? Color.GREEN : Color.BLACK);
			}
		});

		b_CameraRot.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				myVib.vibrate(mVibTime);
				b_CameraRot.setTextColor(mView.toggleCameraRotation() ? Color.GREEN : Color.BLACK);
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