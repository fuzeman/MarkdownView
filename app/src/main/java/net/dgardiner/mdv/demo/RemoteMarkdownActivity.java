package net.dgardiner.mdv.demo;

import android.view.View;
import android.widget.LinearLayout;
import net.dgardiner.markdown.flavours.github.GithubFlavour;
import net.dgardiner.mdv.MarkdownView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import net.dgardiner.mdv.core.LoadMarkdownTask;

public class RemoteMarkdownActivity extends AppCompatActivity implements
		MarkdownView.OnPageStartedListener,
		MarkdownView.OnPageFinishedListener {

	private static final String TAG = "RemoteMarkdownActivity";

	private MarkdownView mdv;
	private LinearLayout progressContainer;

	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.markdown_remote);

		// Find elements
		mdv = (MarkdownView) findViewById(R.id.mdv);
		progressContainer = (LinearLayout) findViewById(R.id.progressContainer);

		// Setup markdown view
		mdv.setPageStartedListener(this);
		mdv.setPageFinishedListener(this);

		mdv.setFlavour(new GithubFlavour());
		mdv.setZoomControls(true);
		mdv.setWideViewPort(true);

		// Load markdown from URL
		mdv.loadMarkdownUrl("https://raw.github.com/fuzeman/MarkdownView/master/README.md");
	}

	//
	// Event handlers
	//

	@Override
	public void onPageStarted(LoadMarkdownTask.Request request) {
		progressContainer.setVisibility(View.VISIBLE);
	}

	@Override
	public void onPageFinished(LoadMarkdownTask.Request request) {
		progressContainer.setVisibility(View.GONE);
	}
}
