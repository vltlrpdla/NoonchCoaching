package project.jeonghoon.com.nooncoaching;

import java.util.List;

/**
 * Created by jeonghoon on 2016-11-24.
 */
public interface OnFinishAnnivSearchListner {
    public void onSuccess(List<String> foodList);

    public void onFail();
}
