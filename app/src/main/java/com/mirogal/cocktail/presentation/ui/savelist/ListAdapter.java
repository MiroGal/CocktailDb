package com.mirogal.cocktail.presentation.ui.savelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;

class ListAdapter extends PagedListAdapter<CocktailDbEntity, ItemHolder> {

    private final Context context;
    private final OnItemClickListener onItemClickListener;
    private final OnItemLongClickListener onItemLongClickListener;
    private final int itemLayoutId;

    private static final DiffUtil.ItemCallback<CocktailDbEntity> DIFF_CALLBACK =
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

    ListAdapter(Context context, OnItemClickListener clickListener,
                       OnItemLongClickListener longClickListener, int itemLayoutId) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.onItemClickListener = clickListener;
        this.onItemLongClickListener = longClickListener;
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
            holder.setListener(onItemClickListener, onItemLongClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    interface OnItemClickListener {
        void onItemClick(CocktailDbEntity cocktail);
    }

    interface OnItemLongClickListener {
        void onItemLongClick(int cocktailId);
    }
}
