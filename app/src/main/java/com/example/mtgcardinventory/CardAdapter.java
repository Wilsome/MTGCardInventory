package com.example.mtgcardinventory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/*
The Card Adapter class feeds the list
of cards into the recycler view to be
able to display all the cards.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private Context context;
    private List<Card> cardList;

    private CardDatabaseHelper dbHelper;

    //constructor takes a context and a list of card
    public CardAdapter(Context context, List<Card> cardList) {
        this.context = context;
        this.cardList = cardList;
        this.dbHelper = new CardDatabaseHelper(context);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.textViewName.setText(card.getName());
        holder.textViewSet.setText(card.getSet());
        holder.textViewRarity.setText(card.getRarity());
        holder.textViewCondition.setText(card.getCondition());
        holder.textViewQuantity.setText(String.valueOf(card.getQuantity()));

        // Set a default image for now
        holder.imageViewCard.setImageResource(R.drawable.a);

        holder.buttonEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditCardActivity.class);
            intent.putExtra("card_id", card.getId());
            context.startActivity(intent);
        });

        holder.buttonDelete.setOnClickListener(v -> {
            dbHelper.deleteCard(card.getId());
            cardList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cardList.size());
            Toast.makeText(context, "Card deleted", Toast.LENGTH_SHORT).show();
        });
    }

    //returns the amount of cards in the list
    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewSet, textViewRarity, textViewCondition, textViewQuantity;
        ImageView imageViewCard;
        ImageButton buttonEdit, buttonDelete;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewSet = itemView.findViewById(R.id.textViewSet);
            textViewRarity = itemView.findViewById(R.id.textViewRarity);
            textViewCondition = itemView.findViewById(R.id.textViewCondition);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            imageViewCard = itemView.findViewById(R.id.imageViewCard);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
