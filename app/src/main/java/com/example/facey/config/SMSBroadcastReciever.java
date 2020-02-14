package com.example.facey.config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSBroadcastReciever extends BroadcastReceiver {

    private static String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SMS", "onReceive: ");

        if(intent.getAction() == SMS_RECEIVED)
        {
            Bundle bundle = intent.getExtras();
            if(bundle != null)
            {
                Object[] pduses = (Object[]) bundle.get("pdus");
                SmsMessage[] smsMessages = new SmsMessage[pduses.length];

                for(int i = 0 ; i < pduses.length ; i++)
                {
                    smsMessages[i] = SmsMessage.createFromPdu((byte[]) pduses[i]);
                }

                if(smsMessages[0] != null)
                {
                    String otp = smsMessages[0].getDisplayMessageBody().replaceAll("[^0-9]","");
                    context.sendBroadcast(new Intent()
                            .setAction(Constants.SEND_SMS)
                            .putExtra("sign_otp", otp));

                }
            }
        }

    }
}
