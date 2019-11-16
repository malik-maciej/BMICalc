package com.mm.bmicalc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Locale;

public class CalcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent intent = new Intent(CalcActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void calculate(View view) {
        TextView bmi = findViewById(R.id.bmiResult);

        double bmiResult;

        try {
            bmiResult = getBmiResult();
            String text = getBmiResultText(bmiResult);
            bmi.setText(text);
        } catch (Exception e) {
            bmi.setText(getString(R.string.wrongValues));
            return;
        }

        CharSequence text = getCategory(bmiResult);
        showToast(text);
    }

    private double getBmiResult() {
        EditText heightInput = findViewById(R.id.heightInput);
        EditText weightInput = findViewById(R.id.weightInput);

        double height = Double.valueOf(heightInput.getText().toString()) / 100;
        double weight = Double.valueOf(weightInput.getText().toString());

        double bmiResult = weight / (height * height);
        String bmiFormat = String.format(Locale.US, "%.2f", bmiResult);

        return Double.valueOf(bmiFormat);
    }

    private String getBmiResultText(double bmiResult) {
        ToggleButton sexButton = findViewById(R.id.sexButton);
        EditText ageInput = findViewById(R.id.ageInput);

        String sex = sexButton.isChecked() ? getString(R.string.female) : getString(R.string.male);
        int age = Integer.valueOf(ageInput.getText().toString());

        return getString(R.string.yourBmi) + ": " + bmiResult
                + " (" + sex + ", " + age + " " + getString(R.string.years) + ")";
    }

    private void showToast(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private CharSequence getCategory(double bmiResult) {
        CharSequence text;
        if (bmiResult >= 60) text = getString(R.string.hyperObese);
        else if (bmiResult >= 50) text = getString(R.string.superObese);
        else if (bmiResult >= 45) text = getString(R.string.morbidlyObese);
        else if (bmiResult >= 40) text = getString(R.string.verySeverelyObese);
        else if (bmiResult >= 35) text = getString(R.string.severelyObese);
        else if (bmiResult >= 30) text = getString(R.string.moderatelyObese);
        else if (bmiResult >= 25) text = getString(R.string.overweight);
        else if (bmiResult >= 18.5) text = getString(R.string.normal);
        else if (bmiResult >= 16) text = getString(R.string.underweight);
        else if (bmiResult >= 15) text = getString(R.string.severelyUnderweight);
        else text = getString(R.string.verySeverelyUnderweight);
        return text;
    }
}