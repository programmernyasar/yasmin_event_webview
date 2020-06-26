package com.kulodev.yasminevent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WebView websiteku;
    private ProgressDialog progressDialog;

    //URL web, yang akan kita gunakan pada Webview
    private String URL = "http://yasapp.kulodev.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        websiteku = findViewById(R.id.my_web);
        settings();
    }

    //Method Ini Digunakan sebagai Setelan/Pengaturan Web
    private void settings(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);


        websiteku.requestFocus();
        websiteku.getSettings().setLoadsImagesAutomatically(true);
        websiteku.getSettings().setJavaScriptEnabled(true);
        websiteku.getSettings().setDomStorageEnabled(true);

        // Tiga baris di bawah ini agar laman yang dimuat dapat
        // melakukan zoom.
        websiteku.getSettings().setSupportZoom(true);
        websiteku.getSettings().setBuiltInZoomControls(true);
        websiteku.getSettings().setDisplayZoomControls(false);
        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        websiteku.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        websiteku.setWebViewClient(new myWebclient());
        websiteku.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // Menampilkan loading ketika webview proses load halaman
                progressDialog.show();

            }
        });

        // Memuat halaman web eksternal
        websiteku.loadUrl(URL);
    }

    public class myWebclient extends WebViewClient{


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            progressDialog.dismiss();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressDialog.dismiss();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if((keyCode== KeyEvent.KEYCODE_BACK) && websiteku.canGoBack()){
//            websiteku.goBack();
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onBackPressed() {
        // Jika Webview bisa di back maka backward page sebelumnya
        if (websiteku.canGoBack()) {
            websiteku.goBack();
        } else {
            finish();
            System.exit(0);
        }
    }
}
