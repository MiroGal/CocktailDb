package com.mirogal.cocktail.presentation.ui.searchlist;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mirogal.cocktail.R;
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;

public class ItemHolder extends RecyclerView.ViewHolder {

    private Context context;
    private CocktailDbEntity cocktailEntity;

    private TextView tvName;
    private ImageView ivImage;

    public ItemHolder(@NonNull Context context, @NonNull View itemView) {
        super(itemView);
        this.context = context;
        tvName = itemView.findViewById(R.id.tv_name);
        ivImage = itemView.findViewById(R.id.iv_image);
    }

    public void bind(CocktailDbEntity cocktailEntity) {
        this.cocktailEntity = cocktailEntity;
        tvName.setText(cocktailEntity.getName());

        Glide.with(context)
                .load(cocktailEntity.getImagePath())
                .centerCrop()
                .placeholder(R.drawable.anim_placeholder_progress)
                .error(R.drawable.ic_placeholder_error)
                .into(ivImage);
    }

    public void setListener(final ListAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(cocktailEntity);
            }
        });
    }
}
