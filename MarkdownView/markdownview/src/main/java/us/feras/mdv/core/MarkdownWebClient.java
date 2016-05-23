package us.feras.mdv.core;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import us.feras.mdv.MarkdownView;

public class MarkdownWebClient extends WebViewClient {
    private static final String TAG = "MarkdownWebClient";

    private final MarkdownView view;

    public MarkdownWebClient(MarkdownView view) {
        this.view = view;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        // Trigger event
        MarkdownView.OnPageLoadingListener listener = this.view.getPageLoadingListener();

        if(listener != null) {
            listener.onPageLoading(this.view.getCurrentRequest());
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        // Trigger event
        MarkdownView.OnPageFinishedListener listener = this.view.getPageFinishedListener();

        if(listener != null) {
            listener.onPageFinished(this.view.getCurrentRequest());
        }
    }
}
