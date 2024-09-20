package com.passwordgenerator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShowPasswordActivity extends AppCompatActivity {

    private TextView passwordsTextView;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_password);

        dbHelper = new DBHelper(this);

        passwordsTextView = findViewById(R.id.passwordsTextView);
        displayPasswords();
    }

    private void displayPasswords() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DBContract.PasswordEntry.COLUMN_NAME,
                DBContract.PasswordEntry.COLUMN_PASSWORD
        };

        Cursor cursor = db.query(
                DBContract.PasswordEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        StringBuilder passwords = new StringBuilder();

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.PasswordEntry.COLUMN_NAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.PasswordEntry.COLUMN_PASSWORD));
            passwords.append("Name: ").append(name).append(", Password: ").append(password).append("\n");
        }

        cursor.close();
        db.close();

        passwordsTextView.setText(passwords.toString());
    }
}
