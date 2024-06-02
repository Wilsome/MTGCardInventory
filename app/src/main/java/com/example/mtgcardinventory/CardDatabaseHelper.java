package com.example.mtgcardinventory;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/*
DbHelper allows us to setup our sqlite db
where were able to store our cards and work
with them
*/
public class CardDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mtgCardInventory.db";
    private static final String TABLE_CARDS = "cards";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CARD_SET = "card_set";
    private static final String COLUMN_RARITY = "rarity";
    private static final String COLUMN_CONDITION = "condition";
    private static final String COLUMN_QUANTITY = "quantity";

    public CardDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CARDS_TABLE = "CREATE TABLE " + TABLE_CARDS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_CARD_SET + " TEXT,"
                + COLUMN_RARITY + " TEXT,"
                + COLUMN_CONDITION + " TEXT,"
                + COLUMN_QUANTITY + " INTEGER" + ")";
        db.execSQL(CREATE_CARDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
        onCreate(db);
    }

    // Add a new card
    public void addCard(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, card.getName());
        values.put(COLUMN_CARD_SET, card.getSet());
        values.put(COLUMN_RARITY, card.getRarity());
        values.put(COLUMN_CONDITION, card.getCondition());
        values.put(COLUMN_QUANTITY, card.getQuantity());
        db.insert(TABLE_CARDS, null, values);
        db.close();
    }

    // Get a single card by ID
    public Card getCard(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CARDS, new String[]{
                        COLUMN_ID, COLUMN_NAME, COLUMN_CARD_SET, COLUMN_RARITY,
                        COLUMN_CONDITION, COLUMN_QUANTITY},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Card card = new Card(
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CARD_SET)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_RARITY)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CONDITION)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
            );
            cursor.close();
            return card;
        }

        // Handle the case when the cursor is empty or null
        return null;
    }


    // Get all cards
    public List<Card> getAllCards() {
        List<Card> cardList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARDS, null);

        if (cursor.moveToFirst()) {
            do {
                Card card = new Card();
                card.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                card.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                card.setSet(cursor.getString(cursor.getColumnIndex(COLUMN_CARD_SET)));
                card.setRarity(cursor.getString(cursor.getColumnIndex(COLUMN_RARITY)));
                card.setCondition(cursor.getString(cursor.getColumnIndex(COLUMN_CONDITION)));
                card.setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)));
                cardList.add(card);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cardList;
    }

    // Update a card
    public int updateCard(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, card.getName());
        values.put(COLUMN_CARD_SET, card.getSet());
        values.put(COLUMN_RARITY, card.getRarity());
        values.put(COLUMN_CONDITION, card.getCondition());
        values.put(COLUMN_QUANTITY, card.getQuantity());

        // Updating row
        return db.update(TABLE_CARDS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(card.getId())});
    }

    // Delete a card
    public void deleteCard(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CARDS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
