package project.jeonghoon.com.nooncoaching;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by kku on 2016-11-07.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {


    private Context mContext;
    private List<Item> itemList;
    private static final int VIEW_INDEX_FAVORITE = 0;
    private static final int VIEW_INDEX_NORMAL = 1;
    private static final int VIEW_TYPE_COUNT = 2;
    DBHandler dbHandler;
    boolean flag;
    private static final String LOG_TAG = "ItemsAdapter";
    String weather,address;


public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title, count;
    public ImageView thumbnail, overflow;

    public MyViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);
        count = (TextView) view.findViewById(R.id.count);
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        overflow = (ImageView) view.findViewById(R.id.overflow);
    }
}

    public void delete(int position){
        itemList.remove(position);
        notifyDataSetChanged();
    }


    public ItemsAdapter(Context mContext, List<Item> itemList ,String weather, String address) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.weather = weather;
        this.address = address;
    }

    public int getViewTypeCount(){
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        Item item = itemList.get(position);

        if ( item instanceof FavorItem ){
            return VIEW_INDEX_FAVORITE;
        }else{
            return VIEW_INDEX_NORMAL;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Item item = itemList.get(position);
        holder.title.setText(item.getTitle());
        holder.count.setText(item.getPhone());

        // loading album cover using Glide library
        Glide.with(mContext).load(item.getImageUrl()).into(holder.thumbnail);

        if ( getItemViewType(position) == VIEW_INDEX_FAVORITE ){
            holder.overflow.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    showFavoriteMenu(holder.overflow, position);
                }
            });
        }else {
            holder.overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(holder.overflow, position);
                }
            });
        }
    }


    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_item, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }

    private void showFavoriteMenu(View view, int position) {
        // inflate menu
        PopupMenu favoritePopup = new PopupMenu(mContext, view);
        MenuInflater inflater = favoritePopup.getMenuInflater();
        inflater.inflate(R.menu.menu_favorite, favoritePopup.getMenu());
        favoritePopup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        favoritePopup.show();
    }


    public void clear() {
        itemList.clear();
    }

/**
 * Click listener for popup menu items
 */
class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

    int position;

    public MyMenuItemClickListener(int position) {
        this.position = position;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_add_favourite:
                Toast.makeText(mContext, "Add to favorite", Toast.LENGTH_SHORT).show();
                Item item = itemList.get(position);
                dbHandler = DBHandler.open(MainActivity.mContext, item);
                dbHandler.click_time();
                flag = dbHandler.insertFavorItem();
                if ( flag ){
                    Log.d(LOG_TAG, "입력 완료");
                }else{
                    Log.d(LOG_TAG, "입력 실패");
                }
                dbHandler.stored_data_insert(weather,address);
                dbHandler.selectFood(weather,address);
                dbHandler.close();
                return true;
            case R.id.action_go_to_summarization:
                Item selectedItem = itemList.get(position);
                Intent intent = new Intent(mContext, SummarizationActivity.class);
                intent.putExtra("ITEM",selectedItem);
                mContext.startActivity(intent);
                return true;
            case R.id.action_delete_favourite:
                FavorItem favorItem = (FavorItem)itemList.get(position);
                dbHandler = DBHandler.open(MainActivity.mContext, favorItem);
                dbHandler.click_time();
                flag = dbHandler.deleteFavorItem(favorItem);
                if ( flag ){
                    Log.d(LOG_TAG, "삭제 완료");
                }else{
                    Log.d(LOG_TAG, "삭제 실패");
                }
                dbHandler.stored_data_delete();
                dbHandler.selectFood(weather,address);
                dbHandler.close();
                delete(position);
                Toast.makeText(mContext, "Delete to favorite", Toast.LENGTH_SHORT).show();
                return true;
            default:
        }
        return false;
    }
}

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
