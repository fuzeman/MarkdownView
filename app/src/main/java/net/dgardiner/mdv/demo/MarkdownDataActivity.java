package net.dgardiner.mdv.demo;

import net.dgardiner.markdown.flavours.github.GithubFlavour;
import net.dgardiner.mdv.MarkdownView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MarkdownDataActivity extends AppCompatActivity {

	private EditText markdownEditText;
	private MarkdownView mdv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.markdown_view);

		markdownEditText = (EditText) findViewById(R.id.markdownText);
		mdv = (MarkdownView) findViewById(R.id.markdownView);

		// Enable GFM
		mdv.setFlavour(new GithubFlavour());

		// Load initialize markdown text
		String text = getResources().getString(R.string.md_sample_data);
		markdownEditText.setText(text);
		updateMarkdownView();

		// Setup text changed listener
		markdownEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				updateMarkdownView();
			}
		});
	}

	private void updateMarkdownView() {
		// Reload markdown view
		mdv.loadMarkdown(markdownEditText.getText().toString());
	}
}