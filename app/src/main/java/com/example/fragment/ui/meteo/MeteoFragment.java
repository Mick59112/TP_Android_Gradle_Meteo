package com.example.fragment.ui.meteo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fragment.R;
import com.example.fragment.ui.meteo.models.OpenWeatherMap;
import com.example.fragment.ui.meteo.models.Weather;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

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
                    if (Network.isNetworkAvailable(getContext())){
                        // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        String url = String.format(Constant.URL, editTextCity.getText().toString());

                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e("response", response);

                                        OpenWeatherMap owp = new Gson().fromJson(response, OpenWeatherMap.class);

                                        if (owp.cod.equals("200")) {
                                            textViewCity.setText(owp.name);
                                            textViewTemperature.setText(owp.main.temp);
                                            String value = owp.weather.get(0).icon;
                                            Picasso.get().load(String.format(Constant.URL_IMAGE, value)).into(imageViewIcon);
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("response", "error");
                            }
                        });

                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);
                    } else {
                        FastDialog.showDialog(
                                getContext(),
                                FastDialog.SIMPLE_DIALOG
                                ,getString(R.string.dialog_meteo_network)
                        );
                    }
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
