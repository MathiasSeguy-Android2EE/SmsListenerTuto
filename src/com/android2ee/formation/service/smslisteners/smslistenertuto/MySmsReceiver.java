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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 * This class aims to listen for SMS reception and launch a service to display a notifiation (why a service ? just to show how it can work, it's a dummy app)
 */
public class MySmsReceiver extends BroadcastReceiver {
	public MySmsReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("MySmsReceiver", "SMS Received");
		//Launch the service:
		//Build the Intent
		Intent serviceStart = new Intent(context, MySmsService.class);
		//Set its action
		serviceStart.setAction(context.getString(R.string.sms_receiver_intent));
		//Add elements (the SMS data in fact)
		serviceStart.putExtras(intent.getExtras());
		//start the service
		context.startService(serviceStart);		
	}
}
