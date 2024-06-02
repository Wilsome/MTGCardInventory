package com.example.mtgcardinventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*
Edit card allows us to select a card from our
list and alter its values, then saves them
back to the database
*/

public class EditCardActivity extends AppCompatActivity {

    private EditText editTextName, editTextSet, editTextRarity, editTextCondition, editTextQuantity;
    private Button buttonUpdate;
    private CardDatabaseHelper dbHelper;
    private int cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);

        dbHelper = new CardDatabaseHelper(this);

        editTextName = findViewById(R.id.editTextName);
        editTextSet = findViewById(R.id.editTextSet);
        editTextRarity = findViewById(R.id.editTextRarity);
        editTextCondition = findViewById(R.id.editTextCondition);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        Intent intent = getIntent();
        cardId = intent.getIntExtra("card_id", -1);
        if (cardId != -1) {
            Card card = dbHelper.getCard(cardId);
            if (card != null) {
                editTextName.setText(card.getName());
                editTextSet.setText(card.getSet());
                editTextRarity.setText(card.getRarity());
                editTextCondition.setText(card.getCondition());
                editTextQuantity.setText(String.valueOf(card.getQuantity()));
            }
        }

        buttonUpdate.setOnClickListener(v -> {
            // Retrieve modified card details from EditText fields 
            String name = editTextName.getText().toString().trim();
            String set = editTextSet.getText().toString().trim();
            String rarity = editTextRarity.getText().toString().trim();
            String condition = editTextCondition.getText().toString().trim();
            int quantity = Integer.parseInt(editTextQuantity.getText().toString().trim());

            // Retrieve cardId
            int cardId = getIntent().getIntExtra("card_id", -1);

            // Update the card in the database
            CardDatabaseHelper dbHelper = new CardDatabaseHelper(EditCardActivity.this);
            Card existingCard = dbHelper.getCard(cardId);
            existingCard.setName(name);
            existingCard.setSet(set);
            existingCard.setRarity(rarity);
            existingCard.setCondition(condition);
            existingCard.setQuantity(quantity);
            dbHelper.updateCard(existingCard);

            // Show a toast message
            Toast.makeText(EditCardActivity.this, "Card updated", Toast.LENGTH_SHORT).show();

            // Finish the activity and return to the previous screen
            finish();
        });

    }
}
