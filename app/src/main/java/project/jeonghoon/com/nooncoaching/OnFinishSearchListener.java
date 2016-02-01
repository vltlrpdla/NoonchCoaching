package project.jeonghoon.com.nooncoaching;

import java.util.List;

/**
 * Created by han on 2015-11-24.
 */
public interface OnFinishSearchListener {
    public void onSuccess(List<Item> itemList);

    public void onFail();
}
