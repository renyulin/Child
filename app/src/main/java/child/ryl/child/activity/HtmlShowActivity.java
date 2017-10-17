package child.ryl.child.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import child.ryl.child.R;
import child.ryl.child.utils.Utils;

@SuppressLint("SetJavaScriptEnabled")
public class HtmlShowActivity extends Activity {
	private WebView webview;
	private String url;
	private String jty;
	private boolean redirect = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_html_show);
		initData();
		initView();
	}

	public void initData() {
		url = "http://m.fangxiaoer.com/about/about_us.html";
		jty = this.getIntent().getStringExtra("jty");
		redirect = this.getIntent().getBooleanExtra("redirect", false);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		initData();
	}

	public void initView() {
		webview = (WebView) findViewById(R.id.three_protect_webview);
		WebSettings ws = webview.getSettings();
		ws.setJavaScriptEnabled(true);
		if (jty == null) {
			webview.loadUrl(url);
		} else {
			if(url.contains("?")) {
				webview.loadUrl(url + "&jty=1");
			} else {
				webview.loadUrl(url + "?jty=1");
			}
		}
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if(redirect) {
					getIntent().putExtra("url", url);
					startActivity(getIntent());
				} else {
					view.loadUrl(url);
				}
				return true;
			}
		});
		
		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
									 JsResult result) {
				Utils.smallToast(HtmlShowActivity.this, message);
				result.confirm();
				return true;
			}
		});
	}
}
