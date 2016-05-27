package net.dgardiner.mdv.core;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import net.dgardiner.markdown.MarkdownProcessor;
import net.dgardiner.mdv.MarkdownView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoadMarkdownTask extends AsyncTask<LoadMarkdownTask.Request, Integer, String> {
    private static final String TAG = "LoadMarkdownTask";

    private Context context;
    private Request request;

    private MarkdownProcessor processor;
    private MarkdownView view;

    private MarkdownView.OnPageStartedListener pageStartedListener;
    private MarkdownView.OnPageFetchedListener pageFetchedListener;
    private MarkdownView.OnPageParsedListener pageParsedListener;

    public LoadMarkdownTask(Context context, MarkdownProcessor processor, MarkdownView view) {
        this.context = context;

        this.processor = processor;
        this.view = view;

        this.pageStartedListener = view.getPageStartedListener();
        this.pageFetchedListener = view.getPageFetchedListener();
        this.pageParsedListener = view.getPageParsedListener();
    }

    protected String doInBackground(LoadMarkdownTask.Request... params) {
        // Load markdown
        try {
            // Retrieve request
            this.request = params[0];

            // Update request
            this.request.onStarted();

            // Trigger page started event
            if(pageStartedListener != null) {
                pageStartedListener.onPageStarted(this.request);
            }

            // Validate request
            if(this.request == null) {
                return null;
            }

            // Retrieve markdown
            String markdown = fetch();

            if(markdown == null) {
                return null;
            }

            // Parse markdown
            return parse(markdown);
        } catch (Exception e) {
            Log.w(TAG, "Unable to load markdown", e);
            return null;
        }
    }

    protected void onProgressUpdate(Integer... progress) {
        // no-op
    }

    protected void onPostExecute(String result) {
        if(result == null) {
            // No markdown returned
            this.view.loadUrl("about:blank");
        }

        // Load result (or blank page)
        if(result != null) {
            load(result);
        } else {
            this.view.loadUrl("about:blank");
        }
    }

    private String fetch() {
        String result = request.getData(context);

        // Trigger event
        if(pageFetchedListener != null) {
            pageFetchedListener.onPageFetched(this.request);
        }

        return result;
    }

    private String parse(String markdown) {
        // Parse markdown
        String body = null;

        try {
            body = processor.process(markdown);
        } catch (IOException e) {
            Log.w(TAG, "Unable to parse markdown", e);
            return null;
        }

        // Trigger event
        if(pageParsedListener != null) {
            pageParsedListener.onPageParsed(this.request);
        }

        if (request.themeUrl != null) {
            body = 	"<link rel='stylesheet' type='text/css' href='" + request.themeUrl +"' />" + body;
        }

        // Load html
        return
            "<html>" +
                "<head>" +
                    "<style>" +
                        "code, img { display: inline; height: auto; max-width: 100%; }" +
                    "</style>" +
                "</head>" +
                "<body>" + body + "</body>" +
            "</html>";
    }

    private void load(String html) {
        this.view.loadDataWithBaseURL("mdv://", html, "text/html", "UTF-8", "");
    }

    public static abstract class Request {
        private final String themeUrl;

        private boolean finished = false;

        public Request(String themeUrl) {
            this.themeUrl = themeUrl;
        }

        public abstract String getData(Context context);
        public String getThemeUrl() {
            return themeUrl;
        }

        public boolean isFinished() { return finished; }

        public void onStarted() {
            finished = false;
        }
        public void onFinished() { finished = true; }
    }

    public static class AssetRequest extends Request {
        private final String assetUri;

        public AssetRequest(String assetUri) { this(assetUri, null); }
        public AssetRequest(String assetUri, String themeUrl) {
            super(themeUrl);

            this.assetUri = assetUri;
        }

        public String getAssetUri() {
            return assetUri;
        }

        @Override
        public String getData(Context context) {
            return readFileFromAsset(context, assetUri.substring("file:///android_asset/".length(), assetUri.length()));
        }

        private String readFileFromAsset(Context context, String fileName){
            try {
                InputStream input = context.getAssets().open(fileName);

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder content = new StringBuilder(input.available());
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        content.append(line);
                        content.append(System.getProperty("line.separator"));
                    }

                    return content.toString();
                } finally {
                    input.close();
                }
            } catch (Exception e){
                Log.w(TAG, "Error reading file from assets", e);
                return null;
            }
        }
    }

    public static class RawRequest extends Request {
        private final String data;

        public RawRequest(String data) { this(data, null); }
        public RawRequest(String data, String themeUrl) {
            super(themeUrl);

            this.data = data;
        }

        public String getData(Context context) {
            return data;
        }
    }

    public static class WebRequest extends Request {
        private final String url;

        public WebRequest(String url) { this(url, null); }
        public WebRequest(String url, String themeUrl) {
            super(themeUrl);

            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String getData(Context context) {
            try {
                return HttpHelper.get(url).getResponseMessage();
            } catch (IOException e) {
                Log.w(TAG, "Error while fetching markdown from URL \"" + url + "\"", e);
                return null;
            }
        }
    }
}
