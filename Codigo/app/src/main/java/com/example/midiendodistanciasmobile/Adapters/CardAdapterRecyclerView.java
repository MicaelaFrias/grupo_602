package com.example.midiendodistanciasmobile.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midiendodistanciasmobile.R;
import com.example.midiendodistanciasmobile.Models.Card;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardAdapterRecyclerView extends RecyclerView.Adapter<CardAdapterRecyclerView.CardViewHolder>{

    private ArrayList<Card> cards;
    private int resource;
    private Activity activity;

    public CardAdapterRecyclerView(ArrayList<Card> cards, int resource, Activity activity) {
        this.cards = cards;
        this.resource = resource;
        this.activity = activity;
    }
    /*Inicializar clase Card*/

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false); //inyectar el code, el recurso..
        return new CardViewHolder(view);
    }

    //*Trabajar con la lista de objetos*/
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cards.get(position);
        holder.titleCard.setText(card.getTitleCard());
        holder.descriptionCard.setText(card.getDescriptionCard());
        Picasso.with(activity).load(card.getPicture()).into(holder.pictureCard);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{

        private ImageView pictureCard;
        private TextView titleCard;
        private TextView descriptionCard;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            pictureCard = (ImageView) itemView.findViewById(R.id.card_picture);
            titleCard = (TextView) itemView.findViewById(R.id.card_title);
            descriptionCard = (TextView) itemView.findViewById(R.id.card_description);

        }
    }
}
