package com.telerik.TestPlugin;

import android.app.Activity;
import android.content.res.AssetManager;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class TestPlugin extends CordovaPlugin {

	private CordovaActivity cordovaActivity;

	private void copyFile(String filename) {
		AssetManager assetManager = this.cordovaActivity.getAssets();

		InputStream in = null;
		OutputStream out = null;
		try {
			in = assetManager.open(filename);
			File newFile = new File(this.cordovaActivity.getFilesDir(), filename);
			if (newFile.exists()) {
				return;
			}

			newFile.getParentFile().mkdirs();
			newFile.createNewFile();
			out = new FileOutputStream(newFile.getPath());

			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		} catch (Exception e) {
			this.showException(e);
		}

	}

	 @Override
	 public void initialize(CordovaInterface cordova, CordovaWebView webView) {
	 	super.initialize(cordova, webView);
	 	this.cordovaActivity = (CordovaActivity) cordova.getActivity();
		this.copyFile("www/dummy.html");
		this.copyFile("www/otherPage.html");
	 }

	/**
	 * Called when a message is sent to plugin.
	 *
	 * @param id
	 *            The message id
	 * @param data
	 *            The message data
	 * @return Object to stop propagation or null
	 */
	public Object onMessage(String id, Object data) {

		String url = data instanceof String ? (String) data : null;
		try {
			if (id == "onPageFinished" && url.equals(Config.getStartUrl())) {
				this.webView.stopLoading();
				Method loadUrlIntoView = CordovaWebView.class.getMethod("loadUrlIntoView", String.class, boolean.class);
				loadUrlIntoView.invoke(webView, "file://" + this.cordovaActivity.getFilesDir().getPath() + "/www/dummy.html", false);
			}
		} catch (Exception e) {
			this.showException(e);
		}

		return super.onMessage(id, data);
	}

	private void showException(Throwable e) {
		e.printStackTrace();
	}
}
