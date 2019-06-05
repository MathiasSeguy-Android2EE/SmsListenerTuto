package com.android2ee.formation.service.smslisteners.smslistenertuto;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	
	/**
	 * As explained by MarkMurphy in http://commonsware.com/blog/2011/07/13/boot-completed-regression-confirmed.html
	 * You can not enable a BroadCastReceiever after HoneyComb if the application hasn't launched before an activity
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}
