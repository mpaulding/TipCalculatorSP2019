package edu.miracosta.cs134.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Class handles the MainActivity for the Tip Calculator using MVC architecture.
 */
public class MainActivity extends AppCompatActivity {

    private NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());
    private NumberFormat percent = NumberFormat.getPercentInstance(Locale.getDefault());

    // Member variables for each component used in the app
    private EditText amountEditText;
    private TextView percentTextView;
    private TextView tipTextView;
    private TextView totalTextView;
    private SeekBar percentSeekBar;

    // Member variable to keep track of the Bill
    private Bill currentBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize each member variable
        amountEditText = findViewById(R.id.amountEditText);
        percentTextView = findViewById(R.id.percentTextView);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);
        percentSeekBar = findViewById(R.id.percentSeekBar);

        currentBill = new Bill();
        currentBill.setTipPercent(0.1);

        // Register an event for the Seek Bar (when progress changes)
        percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentBill.setTipPercent(progress / 100.0);
                updateViews();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });


        // Register an event for the EditText (when text changes)
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               try {
                   double amount = Double.parseDouble(s.toString());
                   currentBill.setAmount(amount);
               }
               catch (NumberFormatException e)
               {
                   Log.w(this.getClass().getSimpleName(), "Warning: amount not in decimal format");
                   currentBill.setAmount(0.0);
               }
                updateViews();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }

    private void updateViews()
    {
        percentTextView.setText(percent.format(currentBill.getTipPercent()));
        tipTextView.setText(currency.format(currentBill.getTipAmount()));
        totalTextView.setText(currency.format(currentBill.getTotalAmount()));
    }
}
