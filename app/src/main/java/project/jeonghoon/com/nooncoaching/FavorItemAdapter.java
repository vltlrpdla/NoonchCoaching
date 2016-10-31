package project.jeonghoon.com.nooncoaching;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *
 */
public class FavorItemAdapter extends RecyclerView.Adapter<FavorItemAdapter.ViewHolder> {
    Context mContext;

    ArrayList<FavorItem> items = new ArrayList<FavorItem>();

    OnItemClickListener mListener;

    public static interface OnItemClickListener {
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public FavorItemAdapter(Context context) {
        mContext = context;
    }

    public void Clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public FavorItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.singer_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavorItemAdapter.ViewHolder holder, int position) {
        holder.setItem(items.get(position));
        holder.setOnItemClickListener(mListener);

    }

    public void addItem(FavorItem item) {
        items.add(item);
    }

    public FavorItem getItem(int position) {
        return items.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textAddress;
        TextView textTitle;
        TextView textPhone;
        TextView textCategory;
        ImageView cookImage;

        OnItemClickListener listener;

        public ViewHolder(View itemView) {
            super(itemView);

            textAddress = (TextView) itemView.findViewById(R.id.textAddress);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
            textPhone = (TextView) itemView.findViewById(R.id.textPhone);
            textCategory = (TextView) itemView.findViewById(R.id.textCate);
            cookImage = (ImageView) itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });


        }

        public void setItem(FavorItem item) {
            textAddress.setText(item.getAddress());
            textTitle.setText(item.getTitle());
            textPhone.setText(item.getPhone());
            textCategory.setText(item.getCategory());

            if(item.getImageUrl().equals("")){
                item.setImageUrl("http://222.116.135.79:8080/Noon/images/noon.png");
                new DownloadImageTask(cookImage).execute(item.getImageUrl());
            }else{
                new DownloadImageTask(cookImage).execute(item.getImageUrl());
            }
        }


        public void setOnItemClickListener(OnItemClickListener inListener) {
            listener = inListener;
        }

    }
}
