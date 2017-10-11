package playoassignment.com.playoassignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import playoassignment.com.playoassignment.util.Common;

/**
 * Created by Anuj on 11/10/17.
 */

public class ActivityContentViewer extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_content_viewer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Common.setToolbar(toolbar, getString(R.string.view_content), this, true);

        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(
                true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportMultipleWindows(
                true);
        webView.getSettings()
                .setJavaScriptCanOpenWindowsAutomatically(true);

        webView.setVisibility(View.VISIBLE);
        webView.setBackgroundColor(0x00000000);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getIntent().getStringExtra("url"));
    }

}
