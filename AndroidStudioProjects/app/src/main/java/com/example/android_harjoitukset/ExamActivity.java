package com.example.android_harjoitukset;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExamActivity extends AppCompatActivity {

    private static final String TAG = "ExamActivity";

    private Toolbar toolbar;

    private MaterialButtonToggleGroup btnGroup;

    TextView currencyResult;
    TextInputEditText currencyInput;
    TextInputLayout currencyLayout;
    double input;
    private double sek = 0.09968;
    private double nok = 0.10288;
    private double dkk = 0.13440;
    private double currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exam);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar = findViewById(R.id.curToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        currencyLayout = findViewById(R.id.currencyInputLayout);
        currencyResult = findViewById(R.id.currencyResult);
        currencyInput = findViewById(R.id.currencyInput);
        currencyInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Pattern pattern = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(s);
                boolean matchFound = matcher.find();
                if (!matchFound) {
                    currencyInput.setError(getString(R.string.curError));
                    input = 0;
                    currencyResult.setText(null);
                } else {
                    String inputText = currencyInput.getText().toString();
                    inputText = inputText.replaceAll(",", ".");
                    input = Double.parseDouble(inputText);
                    currencyResult.setError(null);
                    convert(input);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnGroup = findViewById(R.id.currencyBtnGroup);

        btnGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup materialButtonToggleGroup, int i, boolean b) {
                if (b) {
                    if (i == R.id.sekBtn) {
                        currency = sek;
                        convert(input);
                        currencyLayout.setHint(R.string.SEK);
                    } else if (i == R.id.nokBtn) {
                        currency = nok;
                        convert(input);
                        currencyLayout.setHint(R.string.NOK);
                    } else if (i == R.id.dkkBtn) {
                        currency = dkk;
                        convert(input);
                        currencyLayout.setHint(R.string.DKK);
                    } else {
                        currency = 0;
                    }
                }


            }
        });

    }

    private void convert(double input) {

        if (currencyInput.getError() == null && currency != 0) {

            double result = currency * input;
            String resultText = String.format(Locale.FRANCE, "%,.3f €", result);
            currencyResult.setText(resultText);


        }


    }
}