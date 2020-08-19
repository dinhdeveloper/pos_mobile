package qtc.project.pos_tablet.adapter.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import qtc.project.pos_tablet.R;
import qtc.project.pos_tablet.model.CategoryModel;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private CategoryAdapterListener listener;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    ArrayList<CategoryModel> list;
    CategoryModel categoryModel;
    Context context;

    public CategoryAdapter(ArrayList<CategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public interface CategoryAdapterListener {
        void onItemClick(CategoryModel model);
        void onHeaderItemClick();
    }

    public void setListener(CategoryAdapterListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_category, parent, false);
            return new HeaderViewHolder(itemView);
        } else if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_category, parent, false);
            return new ItemViewHolder(itemView);
        } else return null;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutBorder;
        TextView nameCategory;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutBorder = itemView.findViewById(R.id.layoutBorder);
            nameCategory = itemView.findViewById(R.id.nameCategory);

            layoutBorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener !=null){
                        listener.onHeaderItemClick();
                    }
                }
            });
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutBorder;
        TextView nameCategory;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutBorder = itemView.findViewById(R.id.layoutBorder);
            nameCategory = itemView.findViewById(R.id.nameCategory);

            layoutBorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(list.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        categoryModel = list.get(i);
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.layoutBorder.setBackgroundResource(R.drawable.custom_background_item_category);
            headerViewHolder.nameCategory.setText("Tất cả sản phẩm");
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.layoutBorder.setBackgroundResource(R.drawable.custom_background_item_unhover_category);
            itemViewHolder.nameCategory.setText(list.get(i).getName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
