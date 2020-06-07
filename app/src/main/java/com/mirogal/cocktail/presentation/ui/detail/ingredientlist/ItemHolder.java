package com.mirogal.cocktail.presentation.ui.detail.ingredientlist;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirogal.cocktail.R;
import com.mirogal.cocktail.presentation.ui.detail.ingredientlist.entity.IngredientEntity;

class ItemHolder extends RecyclerView.ViewHolder {

    private final TextView tvPosition;
    private final TextView tvIngredient;
    private final TextView tvMeasure;

    ItemHolder(@NonNull View itemView) {
        super(itemView);
        tvPosition = itemView.findViewById(R.id.tv_position);
        tvIngredient = itemView.findViewById(R.id.tv_ingredient);
        tvMeasure = itemView.findViewById(R.id.tv_measure);
    }

    @SuppressLint("SetTextI18n")
    void bind(IngredientEntity ingredientEntity) {
        tvPosition.setText(ingredientEntity.getId() + ".");
        tvIngredient.setText(ingredientEntity.getName());
        tvMeasure.setText(ingredientEntity.getMeasure());
    }
}
