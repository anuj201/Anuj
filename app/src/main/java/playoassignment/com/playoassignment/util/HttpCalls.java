package playoassignment.com.playoassignment.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Anuj on 10/10/17.
 */

public class HttpCalls {

    private static HttpCalls mInstance;
    private RequestQueue mRequestQueue;
    private Activity activity;

    private HttpCalls(Activity activity) {
        this.activity = activity;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized HttpCalls getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new HttpCalls(activity);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = prepareSerialRequestQueue(activity.getApplicationContext());
            mRequestQueue.start();
        }
        return mRequestQueue;
    }


    public void handleHttpCalls(final String url,
                                   final CallCompleted callListener, final int requestCode) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                callListener.onCallComplete(response, requestCode, AppConstant.API_STATUS_SUCCESS);
                }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                callListener.onCallComplete(error.toString(), requestCode, AppConstant.API_STATUS_FAIL);
            }
        }) {
            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }

        };
        RetryPolicy policy = new DefaultRetryPolicy(AppConstant.HTTP_CALL_TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        stringRequest.setShouldCache(true);
        stringRequest.setTag("");
        getRequestQueue().add(stringRequest);
    }

    private static RequestQueue prepareSerialRequestQueue(Context context) {

        Network network = new BasicNetwork(new HurlStack());
        Cache cache = new DiskBasedCache(context.getCacheDir(), 2 * 1024 * 1024);
        return new RequestQueue(cache, network, 1);
    }}

