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
public class CateAdapter extends RecyclerView.Adapter<CateAdapter.ViewHolder> {
    Context mContext;

    ArrayList<CateItem> items = new ArrayList<CateItem>();

    OnItemClickListener mListener;

    public static interface OnItemClickListener {
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public CateAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public CateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.cate_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CateAdapter.ViewHolder holder, int position) {
        holder.setItem(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    public void addItem(CateItem item) {
        items.add(item);
    }

    public CateItem getItem(int position) {
        return items.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //TextView textDate;
        TextView textTitle;
        TextView textPhone;
        TextView textCate;
        ImageView cookImage;

        OnItemClickListener listener;

        public ViewHolder(View itemView) {
            super(itemView);

            //textDate = (TextView) itemView.findViewById(R.id.textDate);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
            textPhone = (TextView) itemView.findViewById(R.id.textPhone);
            textCate = (TextView) itemView.findViewById(R.id.textCate);
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

        public void setItem(CateItem item) {
            //textDate.setText(item.getfDate());
            textTitle.setText(item.getTitle());
            textPhone.setText(item.getPhone());
            textTitle.setText(item.getTitle());
            textCate.setText(item.getCate());

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
