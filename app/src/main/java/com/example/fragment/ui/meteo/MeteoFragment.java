package com.example.fragment.ui.meteo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragment.R;

public class MeteoFragment extends Fragment {

    private EditText editTextCity;
    private Button buttonSubmit;
    private TextView textViewCity, textViewTemperature;
    private ImageView imageViewIcon;

    public MeteoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meteo, container, false);

        // Créations des vues
        editTextCity = view.findViewById(R.id.editTextVille);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);
        textViewCity = view.findViewById(R.id.textViewCity);
        textViewTemperature = view.findViewById(R.id.textViewTemperature);
        imageViewIcon = view.findViewById(R.id.imageViewIcon);

        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextCity.getText().toString().isEmpty()) {

                } else {
                    FastDialog.showDialog(
                            getContext(),
                            FastDialog.SIMPLE_DIALOG
                            ,getString(R.string.dialog_meteo_city_empty)
                            );
                }
            }
        });
    }


}