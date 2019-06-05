/**<ul>
 * <li>SmsListenerTuto</li>
 * <li>com.android2ee.formation.service.smslisteners.smslistenertuto</li>
 * <li>15 oct. 2012</li>
 * 
 * <li>======================================================</li>
 *
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 *
 /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br> 
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 *  Belongs to <strong>Mathias Seguy</strong></br>
 ****************************************************************************************************************</br>
 * This code is free for any usage except training and can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * 
 * *****************************************************************************************************************</br>
 *  Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 *  Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br> 
 *  Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 *  <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */
package com.android2ee.formation.service.smslisteners.smslistenertuto;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 * This class aims to be a service that display notification according to the intent it received
 */
public class MySmsService extends Service {

	public MySmsService() {
		//unused
	}

	@Override
	public IBinder onBind(Intent intent) {
		//unused
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		Log.e("MySmsService", "Called");
		super.onCreate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//First find the action we need to handle
		String smsreceive = getString(R.string.sms_receiver_intent);
		if (intent.getAction().equals(smsreceive)) {
			/* The SMS-Messages are 'hiding' within the extras of the Intent. */
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				// Retrieve the data store in the SMS
				Object[] pdus = (Object[]) bundle.get("pdus");
				// Declare the associated SMS Messages
				SmsMessage[] smsMessages = new SmsMessage[pdus.length];
				// Rebuild your SMS Messages
				for (int i = 0; i < pdus.length; i++) {
					smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				// Parse your SMS Message
				SmsMessage currentMessage;
				String body = "null", from = "null";
				long time;
				for (int i = 0; i < smsMessages.length; i++) {
					currentMessage = smsMessages[i];
					body = currentMessage.getDisplayMessageBody();
					from = currentMessage.getDisplayOriginatingAddress();
					time = currentMessage.getTimestampMillis();
					Log.w("MySmsReceiver", "SMS :[" + from + "] " + body);
					//ask for the notification to display it
					new NotificationFactory(this).createNoficiation(from, body, time);
				}
				Log.e("MySmsService:onStartCommand", "SMS receive [" + from + "] : " + body);
			}
		}
		Log.e("MySmsService:onStartCommand", "Call finished");
		//and shut down the service
		stopSelf();
		return super.onStartCommand(intent, flags, startId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
