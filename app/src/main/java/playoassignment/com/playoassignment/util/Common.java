package playoassignment.com.playoassignment.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import playoassignment.com.playoassignment.R;

/**
 * Created by Anuj on 10/10/17.
 */

public class Common {


    public static boolean isNetworkAvailable(Context mContext) {

        try {
            ConnectivityManager connec = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null
                    && connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState() == NetworkInfo.State.CONNECTED)
                return true;

            TelephonyManager tm = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (tm.getDataState() == TelephonyManager.DATA_CONNECTED
                    || tm.getDataState() == TelephonyManager.DATA_CONNECTING)
                return true;

            if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
                if (connec.getNetworkInfo(ConnectivityManager.TYPE_WIMAX) != null
                        && connec
                        .getNetworkInfo(ConnectivityManager.TYPE_WIMAX)
                        .getState() == NetworkInfo.State.CONNECTED)
                    return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public static void setToolbar(Toolbar toolbar, String title, final AppCompatActivity appCompact, boolean navigation) {

        TextView titleView = (TextView) toolbar.findViewById(R.id.toolBarTitleTextView);
        titleView.setText(title);
        titleView.setSingleLine(true);
        appCompact.setSupportActionBar(toolbar);
        if (navigation) {

            toolbar.setNavigationIcon(R.drawable.ic_arrow);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    appCompact.onBackPressed();
                }
            });
        }else{
            titleView.setPadding((int) convertDpToPixel(10f,appCompact),0,0,0);
        }

        appCompact.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
}
