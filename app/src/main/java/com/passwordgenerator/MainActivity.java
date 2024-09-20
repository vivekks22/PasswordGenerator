package com.passwordgenerator;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText lengthEditText, nameEditText;
    private CheckBox specialCharCheckBox, numberCheckBox;
    private Button generateButton;
    private TextView passwordTextView, errorTextView;
    private ImageView errorImageView;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        lengthEditText = findViewById(R.id.lengthEditText);
        specialCharCheckBox = findViewById(R.id.specialCharCheckBox);
        numberCheckBox = findViewById(R.id.numberCheckBox);
        nameEditText = findViewById(R.id.nameEditText);
        generateButton = findViewById(R.id.generateButton);
        passwordTextView = findViewById(R.id.passwordTextView);
        errorTextView = findViewById(R.id.errorTextView);
        errorImageView = findViewById(R.id.errorImageView);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePassword();
            }
        });
    }

    private void generatePassword() {
        // Check if length and name are provided
        String lengthStr = lengthEditText.getText().toString();
        String name = nameEditText.getText().toString();

        if (lengthStr.isEmpty() || name.isEmpty()) {
            showError("Please fill in the requirements.");
            errorImageView.setVisibility(View.VISIBLE);
            return;
        }

        hideError();

        int length = Integer.parseInt(lengthStr);
        String generatedPassword = generateRandomPassword(length);

        // Save to database
        saveToDatabase(name, generatedPassword);

        passwordTextView.setText(generatedPassword);
    }

    private void showError(String errorMessage) {
        errorTextView.setText(errorMessage);
        errorImageView.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        errorImageView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
    }

    private void saveToDatabase(String name, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.PasswordEntry.COLUMN_NAME, name);
        values.put(DBContract.PasswordEntry.COLUMN_PASSWORD, password);
        db.insert(DBContract.PasswordEntry.TABLE_NAME, null, values);
        db.close();
    }

    private String generateRandomPassword(int length) {
        // Your password generation logic here
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder password = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }

        return password.toString();
    }
}
