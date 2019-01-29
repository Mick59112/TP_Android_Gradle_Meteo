package com.example.fragment.ui.temperature;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.fragment.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemperatureFragment extends Fragment {

    private EditText editTextCelcius, editTextFahrenheit;
    private Button buttonsave;
    private ListView listViewTemperature;

    private List<String> temperatureList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    public TemperatureFragment() {
        // Required empty public constructor
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // efface les infos des champs
        if (id == R.id.action_delete) {
            editTextCelcius.setText("0");
            editTextFahrenheit.setText("0");

            // efface les infos de la liste
            temperatureList.clear();
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);
        editTextCelcius = view.findViewById(R.id.editTextCelcius);
        editTextFahrenheit = view.findViewById(R.id.editTextFahrenheit);
        buttonsave = view.findViewById(R.id.temperatureButtonSave);
        listViewTemperature = view.findViewById(R.id.ListViewTemperature);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         editTextCelcius.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {

             }

             @Override
             public void afterTextChanged(Editable editable) {
                 // recupere la valeur est la converti en fahraneit
                 String valeur = editable.toString();
                 if (editTextCelcius.hasFocus() && !valeur.isEmpty() && isNumeric(valeur)) {
                     double val = Double.valueOf(valeur);
                     String valeurInFahrenheit = TemperatureConverter.fahrenheitFromCelcius(val);
                     editTextFahrenheit.setText(valeurInFahrenheit);
                 }
                 if (valeur.isEmpty()) {
                     editTextFahrenheit.setText("0");
                 }
             }
         });

         editTextFahrenheit.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {

             }

             @Override
             public void afterTextChanged(Editable editable) {
                 // recupere la valeur est la converti en celcius
                 String valeur = editable.toString();
                 if (editTextFahrenheit.hasFocus() && !valeur.isEmpty() && isNumeric(valeur)) {
                     double val = Double.valueOf(valeur);
                     String valeurInCelcius = TemperatureConverter.celsiusFromFahrenheit(val);
                     editTextCelcius.setText(valeurInCelcius);
                 }
                 if (valeur.isEmpty()) {
                     editTextCelcius.setText("0");
                 }
             }
         });
         // Enregistre la valeur pour l'afficher dans la liste
         buttonsave.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String celclius = editTextCelcius.getText().toString();
                 String fahrenheit = editTextFahrenheit.getText().toString();

                 temperatureList.add(String.format(getString(R.string.temperature_convert_text),celclius,fahrenheit));
//                 temperatureList.add(celclius+" °C est égal à " + fahrenheit + " °F");

                 adapter.notifyDataSetChanged();
             }
         });

         // gestion liste / adapter
         adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, temperatureList);
         listViewTemperature.setAdapter(adapter);
         listViewTemperature.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                 //efface les elements dans la liste et rafraichi l'adapter
                 temperatureList.remove(position);
                 adapter.notifyDataSetChanged();

                 return false;
             }
         });

    }

    // verifie si la valeur est numerique
    public static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
