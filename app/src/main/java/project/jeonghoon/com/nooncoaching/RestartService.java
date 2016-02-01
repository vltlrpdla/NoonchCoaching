package project.jeonghoon.com.nooncoaching;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by han on 2015-11-24.
 */
public class RestartService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("RestartService", "RestartService called! :" + intent.getAction());
    /* 서비스 죽일때 알람으로 다시 서비스 등록 */

        if(intent.getAction().equals("ACTION.RESTART.FOODGPS")){
            Log.d("RestartService", "ACTION_RESTART_PERSISTENTSERVICE");
            Intent i = new Intent(context,GpsService.class);
            context.startService(i);
        }

    /* 폰 재부팅할때 서비스 등록 */
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d("RestartService", "ACTION_BOOT_COMPLETED");
            Intent i = new Intent(context,GpsService.class);
            context.startService(i);
        }
    }

}