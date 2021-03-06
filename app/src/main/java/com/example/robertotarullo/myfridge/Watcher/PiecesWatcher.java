package com.example.robertotarullo.myfridge.Watcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.robertotarullo.myfridge.Activity.EditProduct;
import com.example.robertotarullo.myfridge.R;
import com.example.robertotarullo.myfridge.Utils.TextUtils;

public class PiecesWatcher implements TextWatcher {
    private Button addButton, subtractButton;
    private SeekBar currentWeightSlider;
    private TextView currentPiecesField;
    private EditText weightField, currentWeightField;

    public PiecesWatcher(Button addButton, Button subtractButton, SeekBar currentWeightSlider, TextView currentPiecesField, EditText weightField, EditText currentWeightField){
       this.addButton = addButton;
       this.subtractButton = subtractButton;
       this.currentWeightSlider = currentWeightSlider;
       this.currentPiecesField = currentPiecesField;
       this.weightField = weightField;
       this.currentWeightField = currentWeightField;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        TextUtils.updateQuantityButtonsView(addButton, subtractButton, s, EditProduct.MIN_PIECES, EditProduct.MAX_PIECES);
        int pieces = TextUtils.getInt(s);

        if(pieces>1){
            // setta lo slider in base al numero di pezzi
            currentWeightSlider.setTag("pieces");

            // calcola il numero di pezzi rimanenti rispetto al valore percentuale
            float currentPiecesAsFloat = (Integer.valueOf(currentWeightSlider.getTag(R.id.percentageValue).toString()) * pieces / (float)100);
            int currentPieces = (int) Math.ceil(currentPiecesAsFloat);
            currentWeightSlider.setMax(pieces);
            currentWeightSlider.setProgress(currentPieces);
            currentPiecesField.setText(String.valueOf(currentPieces));

            if(!TextUtils.isEmpty(weightField)){
                // calcola il nuovo currentWeight rispetto ai pezzi
                float currentWeightAsFloat = (TextUtils.getInt(weightField) * currentWeightSlider.getProgress()) / (float)pieces;
                int currentWeight = (int) Math.ceil(currentWeightAsFloat);
                currentWeightField.setText(String.valueOf(currentWeightAsFloat));
            }

        } else { // setta lo slider in base al peso, se non compilato in percentuale generica
            currentPiecesField.setText(s.toString());
            if(!TextUtils.isEmpty(weightField)){
                // ripristina lo slide rispetto al peso attuale
                currentWeightSlider.setTag("currentWeight");
                // calcola il nuovo currentWeight rispetto al valore percentuale
                float currentWeightAsFloat = (Integer.valueOf(currentWeightSlider.getTag(R.id.percentageValue).toString()) * TextUtils.getInt(weightField)) / (float)100;
                int currentWeight = (int) Math.ceil(currentWeightAsFloat);
                currentWeightSlider.setMax(TextUtils.getInt(weightField));
                currentWeightSlider.setProgress(currentWeight);
            } else {
                // ripristina lo slide rispetto al valore percentuale
                currentWeightSlider.setTag("percentage");
                currentWeightSlider.setMax(100);
                currentWeightSlider.setProgress(Integer.valueOf(currentWeightSlider.getTag(R.id.percentageValue).toString()));
            }
        }
    }
}