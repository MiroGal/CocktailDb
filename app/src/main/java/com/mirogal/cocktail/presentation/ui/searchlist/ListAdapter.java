package com.mirogal.cocktail.presentation.ui.searchlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;

public class ListAdapter extends PagedListAdapter<CocktailDbEntity, ItemHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;
    private int itemLayoutId;

    private static DiffUtil.ItemCallback<CocktailDbEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CocktailDbEntity>() {

        @Override
        public boolean areItemsTheSame(@NonNull CocktailDbEntity oldItem,
                                       @NonNull CocktailDbEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CocktailDbEntity oldItem,
                                          @NonNull CocktailDbEntity newItem) {
            if (oldItem.getName() != null && oldItem.getImagePath() != null) {
                return (oldItem.getName().equals(newItem.getName())
                        && oldItem.getImagePath().equals(newItem.getImagePath()));
            }
            return true;
        }
    };

    public ListAdapter(Context context, OnItemClickListener clickListener, int itemLayoutId) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.onItemClickListener = clickListener;
        this.itemLayoutId = itemLayoutId;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(itemLayoutId, parent, false);
        return new ItemHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        if (position <= -1) {
            return;
        }
        CocktailDbEntity item = getItem(position);
        try {
            assert item != null;
            holder.bind(item);
            holder.setListener(onItemClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CocktailDbEntity cocktail);
    }
}
