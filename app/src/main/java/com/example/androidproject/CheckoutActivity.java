package com.example.androidproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class CheckoutActivity extends AppCompatActivity {

    private TextInputEditText nameEditText, addressEditText, postCodeEditText, cardNumberEditText, cvvEditText, expiryDateEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        nameEditText = findViewById(R.id.nameEdtTxt);
        addressEditText = findViewById(R.id.addressEdtTxt);
        postCodeEditText = findViewById(R.id.postCodeEdtTxt);
        cardNumberEditText = findViewById(R.id.cardNumberEdtTxt);
        cvvEditText = findViewById(R.id.cvvEdtTxt);
        expiryDateEditText = findViewById(R.id.dateEdtTxt);
        Button placeOrderButton = findViewById(R.id.buttonPlaceOrder);

        placeOrderButton.setOnClickListener(v -> {
            if (!validateFields()) {
                Toast.makeText(this, "Please fill all fields correctly.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Payment processed successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CheckoutActivity.this,ThankyouActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateFields() {
        return !isEmpty(nameEditText) && !isEmpty(addressEditText) && !isEmpty(postCodeEditText)
                && validateCardNumber(cardNumberEditText) && validateCVV(cvvEditText) && validateExpiryDate(expiryDateEditText);
    }

    private boolean isEmpty(TextInputEditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    private boolean validateCardNumber(TextInputEditText editText) {
        String cardNumber = editText.getText().toString().trim();
        return cardNumber.length() == 16; // Basic validation, adjust as necessary
    }

    private boolean validateCVV(TextInputEditText editText) {
        String cvv = editText.getText().toString().trim();
        return cvv.length() == 3; // Basic validation for CVV
    }

    private boolean validateExpiryDate(TextInputEditText editText) {
        String expiry = editText.getText().toString().trim();
        return expiry.matches("\\d{2}\\d{2}"); // Basic validation for expiry date format
    }
}