package com.mirogal.cocktail.presentation.ui.detail.ingredientlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirogal.cocktail.presentation.ui.detail.ingredientlist.entity.IngredientEntity;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ItemHolder> {

    private List<IngredientEntity> ingredientList;

    private int itemLayoutId;

    public ListAdapter(List<IngredientEntity> ingredientList, int itemLayoutId) {
        this.ingredientList = ingredientList;
        this.itemLayoutId = itemLayoutId;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(itemLayoutId, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.bind(ingredientList.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }
}
