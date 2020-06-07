package com.mirogal.cocktail.presentation.ui.savelist;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mirogal.cocktail.R;
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;

class ItemHolder extends RecyclerView.ViewHolder {

    private final Context context;
    private CocktailDbEntity cocktailEntity;

    private final TextView tvName;
    private final ImageView ivImage;

    ItemHolder(@NonNull Context context, @NonNull View itemView) {
        super(itemView);
        this.context = context;
        tvName = itemView.findViewById(R.id.tv_name);
        ivImage = itemView.findViewById(R.id.iv_image);
    }

    void bind(CocktailDbEntity cocktailEntity) {
        this.cocktailEntity = cocktailEntity;
        tvName.setText(cocktailEntity.getName());

        Glide.with(context)
                .load(cocktailEntity.getImagePath())
                .centerCrop()
                .placeholder(R.drawable.anim_placeholder_progress)
                .error(R.drawable.ic_placeholder_error)
                .into(ivImage);
    }

    void setListener(final ListAdapter.OnItemClickListener clickListener,
                            final ListAdapter.OnItemLongClickListener longClickListener) {

        itemView.setOnClickListener(v -> clickListener.onItemClick(cocktailEntity));

        itemView.setOnLongClickListener(v -> {
            longClickListener.onItemLongClick(cocktailEntity.getId());
            return false;
        });
    }
}
