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
public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.ViewHolder> {
    Context mContext;

    ArrayList<SingerItem> items = new ArrayList<SingerItem>();

    OnItemClickListener mListener;

    public static interface OnItemClickListener {
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public SingerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public SingerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.singer_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SingerAdapter.ViewHolder holder, int position) {
        holder.setItem(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    public void addItem(SingerItem item) {
        items.add(item);
    }

    public SingerItem getItem(int position) {
        return items.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textDate;
        TextView textTitle;
        TextView textPhone;
        ImageView cookImage;

        OnItemClickListener listener;

        public ViewHolder(View itemView) {
            super(itemView);

            textDate = (TextView) itemView.findViewById(R.id.textDate);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
            textPhone = (TextView) itemView.findViewById(R.id.textPhone);
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

        public void setItem(SingerItem item) {
            textDate.setText(item.getfDate());
            textTitle.setText(item.getTitle());
            textPhone.setText(item.getPhone());
            textTitle.setText(item.getTitle());

            if(item.imageUrl.equals("")){
                item.imageUrl ="http://222.116.135.76:8080/Noon/images/noon.png";
                new DownloadImageTask(cookImage).execute(item.imageUrl);
            }else{
                new DownloadImageTask(cookImage).execute(item.imageUrl);
            }
        }

        public void setOnItemClickListener(OnItemClickListener inListener) {
            listener = inListener;
        }

    }
}
