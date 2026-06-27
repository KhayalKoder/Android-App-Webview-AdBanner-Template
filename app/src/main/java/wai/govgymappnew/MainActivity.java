package wai.govgymappnew;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Status Bar rəngi (R.color.black və ya öz rəngin)
        getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));

        // AdMob
        MobileAds.initialize(this, "ca-app-pub-2878374163061282/6615438108");
        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // UI elementlər
        WebView webview = (WebView) findViewById(R.id.webview);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // WebView tənzimləmələri
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // Sürət üçün keşləmə

        // Progress Bar üçün WebChromeClient
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        // Linklərin kənarda açılmaması və Xəta idarəetməsi
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false; 
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.loadData("<html><body style='text-align:center; padding-top:200px; font-family:sans-serif;'><h1>Xəta</h1><p>İnternet bağlantısını yoxlayın.</p></body></html>", "text/html", "UTF-8");
            }
        });

        if (savedInstanceState == null) {
            webview.loadUrl("https://www.crazygames.pics");
        }
    }

    // Əvvəlki düymə funksiyaları eyni qalır...
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView webview = (WebView) findViewById(R.id.webview);
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        WebView webview = (WebView) findViewById(R.id.webview);
        super.onSaveInstanceState(outState);
        webview.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        WebView webview = (WebView) findViewById(R.id.webview);
        super.onRestoreInstanceState(savedInstanceState);
        webview.restoreState(savedInstanceState);
    }
}
