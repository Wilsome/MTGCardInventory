package com.example.mtgcardinventory;

/*
Card class is the basic structure
of how we want to shape our card objects
*/
public class Card {
    private int id;
    private String name;
    private String cardSet;
    private String rarity;
    private String condition;
    private int quantity;

    public Card() {
    }

    //constructor, takes in card properties
    public Card(String name, String cardSet, String rarity, String condition, int quantity) {
        this.name = name;
        this.cardSet = cardSet;
        this.rarity = rarity;
        this.condition = condition;
        this.quantity = quantity;
    }

    //getters and setters for the fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSet() {
        return cardSet;
    }

    public void setSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
