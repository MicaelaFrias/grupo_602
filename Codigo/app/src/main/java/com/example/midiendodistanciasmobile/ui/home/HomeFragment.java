package com.example.midiendodistanciasmobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midiendodistanciasmobile.Adapters.CardAdapterRecyclerView;
import com.example.midiendodistanciasmobile.MainActivity;
import com.example.midiendodistanciasmobile.R;
import com.example.midiendodistanciasmobile.Models.Card;
import com.example.midiendodistanciasmobile.Utilities.Constants;

import java.util.ArrayList;

//*Vive en una actividad*/
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    TextView tvName;
    TextView tvEmail;
    TextView tvGroup;
    TextView tvCom;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView cardsRecycles = root.findViewById(R.id.cardRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        cardsRecycles.setLayoutManager(linearLayoutManager);
        CardAdapterRecyclerView cardAdapterRecyclerView =
                new CardAdapterRecyclerView( buildCard(), R.layout.cardview_picture,getActivity());
        cardsRecycles.setAdapter(cardAdapterRecyclerView);

        String userName = ((MainActivity)getActivity()).UserName;
        String userEmail = ((MainActivity)getActivity()).UserEmail;

        tvName = root.findViewById(R.id.user_name);
        tvName.setText(userName);

        tvEmail = root.findViewById(R.id.user_email);
        tvEmail.setText(userEmail);

        tvGroup = root.findViewById(R.id.user_group);
        tvGroup.setText("App del grupo: " + Constants.NUM_GROUP);

        tvCom = root.findViewById(R.id.user_com);
        tvCom.setText(" Com: " +Constants.NUM_COMMISSION);





        return root;
    }

    public ArrayList<Card> buildCard() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card("https://resizer.iproimg.com/unsafe/880x/https://assets.iprofesional.com/assets/jpg/2020/04/495335.jpg?5.6.0.5", "MI ACTIVIDAD", "Revisa tu actividad", "VER MI ACTIVIDAD"));
        cards.add(new Card("https://d25rq8gxcq0p71.cloudfront.net/dictionary-images/465/6c65affc-6eed-4391-aef7-0615ebde9ae5.jpg", "INICIA TU RECORRIDO", "Controla tu salida", "INICIAR RECORRIDO"));

        return cards;
    }
}