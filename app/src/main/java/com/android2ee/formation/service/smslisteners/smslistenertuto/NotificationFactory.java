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

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to display a notification according to the version of the system
 */
public class NotificationFactory {
	/**
	 * 
	 */
	private static final String NEW_SMS_INCOMING = "New SMS incoming from ";
	/**
	 * The notification manager
	 */
	NotificationManager notifManager;
	/**
	 * the context
	 */
	Context context;
	/**
	 * The notification manager name
	 */
	private static final String NOTIF_SRV_NAME = Context.NOTIFICATION_SERVICE;
	/**
	 * The string to display at the creation of the notification
	 */
	private String notif_ticker;
	/**
	 * The id of the notifications
	 */
	private final int simpleNotifPreJB = 0, notifPostJBInbox = 13;

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public NotificationFactory(Context context) {
		this.context = context;
		notifManager = (NotificationManager) context.getSystemService(NOTIF_SRV_NAME);
		notif_ticker = context.getString(R.string.sms_notif);
	}

	/**
	 * Create a notification to disaply a sms
	 * 
	 * @param from
	 *            who send it
	 * @param body
	 *            the sms body
	 * @param time
	 *            when it has been received
	 */
	public void createNoficiation(String from, String body, long time) {
		Toast.makeText(context, "New SMS receive [" + from + "] : " + body, Toast.LENGTH_LONG).show();
		Log.e("SMSService", "New SMS receive [" + from + "] : " + body);
		// buildNotificationPreHC();
		// now build the notification depending on the version of the system
		// find the system version
		boolean preHC = context.getResources().getBoolean(R.bool.preHC);
		boolean HC = context.getResources().getBoolean(R.bool.HoneyComb);
		// and display the notification
		// From March 2013 the support librairy has released
		// the notification for all the version of the system
		// no more need of the legacy stuff
		if (preHC) {
			// build a legacy notification
			buildLegacyNotification(from, body, time);
		} else if (HC) {
			// use compatLibrairy
			buildPreJBNotification(from, body, time);
		} else {
			// use native JellyBean Librairy
			buildPostJBNotification(from, body, time);
		}
	}

	/******************************************************************************************/
	/** Legacy Notification **************************************************************************/
	/******************************************************************************************/
	/**
	 * Display a legacy notification
	 */
	public void buildLegacyNotification(String from, String body, long time) {
		// Creation of the notification with the specified notification icon and text
		// That appears at the creation of the notification
		final Notification notification = new Notification(R.drawable.ic_launcher, NEW_SMS_INCOMING + from,
				System.currentTimeMillis());
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(time);
		String ticker = String.format(notif_ticker, from, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
		// Notification & Vibration
		// Redirect to our other activity
		// Redirect to our other activity
		final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, null, 0);
		notification.setLatestEventInfo(context, NEW_SMS_INCOMING + from, body, pendingIntent);
		notification.vibrate = new long[] { 0, 200, 100, 200, 100, 200 };
		// Set the ticker to be disaplayed when the notification is created
		notification.tickerText = ticker;
		// and display it
		notifManager.notify(simpleNotifPreJB, notification);
	}

	/******************************************************************************************/
	/** PRE_JB Notification **************************************************************************/
	/******************************************************************************************/
	/**
	 * Build a simple notification for preJellyBean devices
	 */
	public void buildPreJBNotification(String from, String body, long time) {
		Log.e("MySmsService", "buildSimpleNotificationPreJB");
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(time);
		String ticker = String.format(notif_ticker, from, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
		// define the notification's builder
		NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
		.setContentTitle(NEW_SMS_INCOMING)
				.setContentText(from)
				.setTicker(ticker)
				.setSmallIcon(R.drawable.ic_launcher)
				// .setLargeIcon(aBitmap)
				.setAutoCancel(true);
		// set this notification as a BigText notification
		Notification notif = new NotificationCompat.BigTextStyle(nBuilder).bigText(body).build();
		// Notification notif = nBuilder.build();
		// And display it : Be sure to set an unique identifier
		notifManager.notify(simpleNotifPreJB, notif);
		Toast.makeText(context, "Notification done", Toast.LENGTH_LONG).show();

	}

	/******************************************************************************************/
	/** POST_JB Notification **************************************************************************/
	/******************************************************************************************/
	/**
	 * For post JellyBean devices
	 * style:Inbox
	 */
	@SuppressLint("NewApi")
	public void buildPostJBNotification(String from, String body, long time) {
		Log.e("MySmsService", "buildContentNotificationPostJB");
		Notification noti = new Notification.Builder(context).setContentTitle("Inbox style")
				// .setContentText(summary)
				.setSmallIcon(R.drawable.ic_launcher)
				.setStyle(new Notification.InboxStyle().addLine("Line1").addLine("Line2").setSummaryText(body))

				.build();
		// then display the notification
		notifManager.notify(notifPostJBInbox, noti);
	}

}
