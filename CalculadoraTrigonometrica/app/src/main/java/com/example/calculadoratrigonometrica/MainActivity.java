package com.example.calculadoratrigonometrica;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainActivity extends Activity {

    private RadioButton checkedDegreeRadioButton, checkedFunctionRadioButton;
    private RadioGroup functionOptions, degreeOptions;
    private ImageView degreeImage;
    private TextView result;
    private static final String RESULT = "result";

    private static double sin(double angle) {
        return Math.sin( angle );
    }

    private static double cos(double angle) {
        return Math.cos( angle );
    }

    private static double tan(double angle) {
        return Math.tan( angle );
    }

    private void setImage(ImageView container, int angle) {
        switch (angle) {
            case 45:
                container.setImageResource(R.drawable.img_45);
                break;
            case 90:
                container.setImageResource(R.drawable.img_90);
                break;
            case 180:
                container.setImageResource(R.drawable.img_180);
                break;
        }
    }

    private void doCalculation(String functionSelected, double angle) {
        double calculationResult = 0.0;
        switch ( functionSelected ) {
            case "SIN":
                calculationResult = sin(angle);
                break;
            case "COS":
                calculationResult = cos(angle);
                break;
            case "TAN":
                calculationResult = tan(angle);
                break;
        }
        result.setText(String.valueOf(calculationResult));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // load the activity_main.xml content

        degreeImage = findViewById(R.id.angulo); // find the ImageView to place the angle image
        result = findViewById(R.id.resultado); // find the TextView called resultado
        functionOptions = findViewById(R.id.opciones_funciones); // find the radio group of functions
        degreeOptions = findViewById(R.id.opciones_grados); // find the radio group of degrees
    }

    @Override
    protected void onResume() {
        super.onResume();

        functionOptions.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                checkedFunctionRadioButton = findViewById(checkedId);
                checkedDegreeRadioButton = findViewById(degreeOptions.getCheckedRadioButtonId());

                if ( checkedDegreeRadioButton != null ) {
                    // do calculation and change picture
                    if ( checkedFunctionRadioButton.isChecked() && checkedDegreeRadioButton.isChecked() ) {
                        // We get the trigonometric function to be executed
                        String trigonometricFunction = (String) checkedFunctionRadioButton.getText();
                        // We get the selected angle
                        double angle = Double.parseDouble( checkedDegreeRadioButton.getText().toString() );
                        // Execute calculation
                        doCalculation(trigonometricFunction, angle);
                        if ( degreeImage.getDrawable() == null ) // only if there is no image, set it
                            setImage(degreeImage, (int) angle);
                    }
                } else {
                    result.setText(R.string.seleccionar_angulo);
                }
            }
        });

        degreeOptions.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                checkedFunctionRadioButton = findViewById(functionOptions.getCheckedRadioButtonId());
                checkedDegreeRadioButton = findViewById(checkedId);

                // Set image even if no function has been selected
                double angle = Double.parseDouble( checkedDegreeRadioButton.getText().toString() );
                setImage(degreeImage, (int) angle);

                if ( checkedFunctionRadioButton != null ) {
                    // do calculation and change picture
                    if ( checkedFunctionRadioButton.isChecked() && checkedDegreeRadioButton.isChecked() ) {
                        String trigonometricFunction = (String) checkedFunctionRadioButton.getText();
                        doCalculation(trigonometricFunction, angle);
                    }
                } else {
                    result.setText(R.string.seleccionar_funcion);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RESULT, result.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String finalResult = savedInstanceState.getString(RESULT);
        result.setText(finalResult);
    }
}
