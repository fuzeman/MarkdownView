package net.dgardiner.mdv;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.webkit.WebView;
import net.dgardiner.markdown.MarkdownProcessor;
import net.dgardiner.markdown.flavours.base.Flavour;
import net.dgardiner.mdv.core.LoadMarkdownTask;
import net.dgardiner.mdv.core.MarkdownWebClient;

public class MarkdownView extends WebView {
	private static final String TAG = "MarkdownView";

	private final MarkdownProcessor processor;

	private LoadMarkdownTask.Request currentRequest = null;
	private LoadMarkdownTask currentTask = null;

	public MarkdownView(Context context) {
		super(context);

		processor = new MarkdownProcessor();

		setDefaults();
	}

	public MarkdownView(Context context, AttributeSet attrs) {
		super(context, attrs);

		processor = new MarkdownProcessor();

		setDefaults();
	}

	private void setDefaults() {
		setWebViewClient(new MarkdownWebClient(this));
	}

	//
	// Properties
	//


	public LoadMarkdownTask.Request getCurrentRequest() {
		return currentRequest;
	}

	public void setZoomControls(boolean enabled) {
		getSettings().setBuiltInZoomControls(enabled);
		getSettings().setDisplayZoomControls(false);
	}

	public void setWideViewPort(boolean enabled) {
		setInitialScale(enabled ? 1 : 0);
		getSettings().setUseWideViewPort(enabled);
	}

	public void setFlavour(Flavour flavour) {
		processor.setFlavour(flavour);
	}

	//
	// Methods
	//

	public void clear() { loadUrl("about:blank"); }

	public void loadMarkdown(String markdown) { loadMarkdown(markdown, null); }
	public void loadMarkdown(String markdown, String themeUrl) { loadMarkdown(new LoadMarkdownTask.RawRequest(markdown, themeUrl)); }

	public void loadMarkdownAsset(String assetUri) { loadMarkdownAsset(assetUri, null); }
	public void loadMarkdownAsset(String assetUri, String themeUrl) { loadMarkdown(new LoadMarkdownTask.AssetRequest(assetUri, themeUrl)); }

	public void loadMarkdownUrl(String url) { loadMarkdownUrl(url, null); }
	public void loadMarkdownUrl(String url, String themeUrl) { loadMarkdown(new LoadMarkdownTask.WebRequest(url, themeUrl)); }

	public void loadMarkdown(LoadMarkdownTask.Request request) {
		currentRequest = request;

		// Cancel existing task
		if(currentTask != null) {
			currentTask.cancel(true);
		}

		// Load markdown in background
		currentTask = new LoadMarkdownTask(getContext(), processor, this);
		currentTask.execute(request);
	}

	//
	// OnPageStartedListener
	//

	private OnPageStartedListener pageStartedListener = null;

	public void setPageStartedListener(OnPageStartedListener listener) {
		pageStartedListener = listener;
	}

	public OnPageStartedListener getPageStartedListener() {
		return pageStartedListener;
	}

	public interface OnPageStartedListener {
		void onPageStarted(LoadMarkdownTask.Request request);
	}

	//
	// OnPageFetchedListener
	//

	private OnPageFetchedListener pageFetchedListener = null;

	public void setPageFetchedListener(OnPageFetchedListener listener) {
		pageFetchedListener = listener;
	}

	public OnPageFetchedListener getPageFetchedListener() {
		return pageFetchedListener;
	}

	public interface OnPageFetchedListener {
		void onPageFetched(LoadMarkdownTask.Request request);
	}

	//
	// OnPageParsedListener
	//

	private OnPageParsedListener pageParsedListener = null;

	public void setPageParsedListener(OnPageParsedListener listener) {
		pageParsedListener = listener;
	}

	public OnPageParsedListener getPageParsedListener() {
		return pageParsedListener;
	}

	public interface OnPageParsedListener {
		void onPageParsed(LoadMarkdownTask.Request request);
	}

	//
	// OnPageLoadingListener
	//

	private OnPageLoadingListener pageLoadingListener = null;

	public void setPageLoadingListener(OnPageLoadingListener listener) {
		pageLoadingListener = listener;
	}

	public OnPageLoadingListener getPageLoadingListener() {
		return pageLoadingListener;
	}

	public interface OnPageLoadingListener {
		void onPageLoading(LoadMarkdownTask.Request request);
	}

	//
	// OnPageFinishedListener
	//

	private OnPageFinishedListener pageFinishedListener = null;

	public void setPageFinishedListener(OnPageFinishedListener listener) {
		pageFinishedListener = listener;
	}

	public OnPageFinishedListener getPageFinishedListener() {
		return pageFinishedListener;
	}

	public interface OnPageFinishedListener {
		void onPageFinished(LoadMarkdownTask.Request request);
	}
}