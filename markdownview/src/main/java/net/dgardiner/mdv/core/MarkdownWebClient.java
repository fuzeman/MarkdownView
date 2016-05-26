package net.dgardiner.mdv.core;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import net.dgardiner.mdv.MarkdownView;

public class MarkdownWebClient extends WebViewClient {
    private static final String TAG = "MarkdownWebClient";

    private final MarkdownView view;

    public MarkdownWebClient(MarkdownView view) {
        this.view = view;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if(!url.equals("mdv://")) {
            return;
        }

        // Trigger event
        MarkdownView.OnPageLoadingListener listener = this.view.getPageLoadingListener();

        if(listener != null) {
            listener.onPageLoading(this.view.getCurrentRequest());
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if(!url.equals("mdv://")) {
            return;
        }

        // Trigger event
        MarkdownView.OnPageFinishedListener listener = this.view.getPageFinishedListener();

        if(listener != null) {
            listener.onPageFinished(this.view.getCurrentRequest());
        }
    }
}
