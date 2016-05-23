package net.dgardiner.mdv;

import android.content.Context;
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

	public void loadMarkdown(String markdown) {
		loadMarkdown(markdown, null);
	}

	public void loadMarkdown(String markdown, String themeUrl) {
		currentRequest = new LoadMarkdownTask.RawRequest(markdown, themeUrl);

		// Load markdown request
		new LoadMarkdownTask(getContext(), processor, this).execute(currentRequest);
	}

	public void loadMarkdownAsset(String assetUri) {
		loadMarkdownAsset(assetUri, null);
	}

	public void loadMarkdownAsset(String assetUri, String themeUrl) {
		currentRequest = new LoadMarkdownTask.AssetRequest(assetUri, themeUrl);

		// Load markdown request
		new LoadMarkdownTask(getContext(), processor, this).execute(currentRequest);
	}

	public void loadMarkdownUrl(String url) {
		loadMarkdownUrl(url, null);
	}

	public void loadMarkdownUrl(String url, String themeUrl) {
		currentRequest = new LoadMarkdownTask.WebRequest(url, themeUrl);

		// Load markdown request
		new LoadMarkdownTask(getContext(), processor, this).execute(currentRequest);
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