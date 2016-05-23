package net.dgardiner.mdv.demo;

import net.dgardiner.markdown.flavours.github.GithubFlavour;
import net.dgardiner.mdv.MarkdownView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LocalMarkdownActivity extends AppCompatActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MarkdownView mdv = new MarkdownView(this);
		setContentView(mdv);

		// Setup markdown view
		mdv.setFlavour(new GithubFlavour());

		// Load markdown from file
		mdv.loadMarkdownAsset("file:///android_asset/hello.md");
	}
}
