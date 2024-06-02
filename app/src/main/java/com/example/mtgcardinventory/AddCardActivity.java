package com.example.mtgcardinventory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/*
* Card activity class is where we take all the users info
* to create a new card and add it to the database
* */
public class AddCardActivity extends AppCompatActivity {

    private EditText editTextName, editTextSet, editTextRarity, editTextCondition, editTextQuantity;
    private Button buttonAddCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        editTextName = findViewById(R.id.editTextName);
        editTextSet = findViewById(R.id.editTextSet);
        editTextRarity = findViewById(R.id.editTextRarity);
        editTextCondition = findViewById(R.id.editTextCondition);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        buttonAddCard = findViewById(R.id.buttonSave);

        buttonAddCard.setOnClickListener(v -> {
            // Retrieve values from EditText fields
            String name = editTextName.getText().toString().trim();
            String set = editTextSet.getText().toString().trim();
            String rarity = editTextRarity.getText().toString().trim();
            String condition = editTextCondition.getText().toString().trim();
            int quantity = Integer.parseInt(editTextQuantity.getText().toString().trim());

            // Create a new card object
            Card card = new Card(name, set, rarity, condition, quantity);

            // Add the card to the database
            CardDatabaseHelper dbHelper = new CardDatabaseHelper(AddCardActivity.this);
            dbHelper.addCard(card);

            // Show a toast message
            Toast.makeText(AddCardActivity.this, "Card added", Toast.LENGTH_SHORT).show();

            // Finish the activity and return to the previous screen
            finish();
        });
    }
}
