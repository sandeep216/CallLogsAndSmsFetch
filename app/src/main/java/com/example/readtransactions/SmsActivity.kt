package com.example.readtransactions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.util.regex.Pattern

/**
 * ================================================================================
 *
 * Created by Sandeep on 2019-06-03.
 *
 * ================================================================================
 */
class SmsActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) : Intent {
            return Intent(context, SmsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)
        readSms()
    }

    private fun readSms() {
        val cursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)

        val amountRegex = Pattern.compile(MainActivity.amountReg)
        val merchantNameRegex = Pattern.compile(MainActivity.merchantNameReg)
        if (cursor!!.moveToFirst()) { // must check the result to prevent exception
            do {
                var msgData = ""
                for (idx in 0 until cursor.columnCount) {
                    if (cursor.getColumnName(idx) == "body") {
                        msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx)
                        val m = amountRegex.matcher(cursor.getString(idx))
                        Log.e("MainActivity", msgData)
                        if (m.find()) {
                            Log.e("MainActivity", "amount_value= " + m.group(0))
                        }

                        if (msgData.contains("debited") ||
                            msgData.contains("purchasing") || msgData.contains("purchase") || msgData.contains("dr")
                        ) {
                            Log.e("MainActivity", "Debit")
                            // smsDto.setTransactionType("0");
                        } else if (msgData.contains("credited") || msgData.contains("cr")) {
                            // msgData.setTransactionType("1");
                            Log.e("MainActivity", "Credit")
                        }
                    }

                    if (cursor.getColumnName(idx) == "address") {
                        var merchantName = ""
                        merchantName += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx)
                        val m = merchantNameRegex.matcher(cursor.getString(idx))
                        Log.e("MainActivity", merchantName)
                        if (m.find()) {
                            var mMerchantName = m.group()
                            mMerchantName = mMerchantName.replace("^\\s+|\\s+$".toRegex(), "")//trim from start and end
                            mMerchantName = mMerchantName.replace("Info.", "")
                            Log.e("MainActivity", "merchant_name= " + mMerchantName)
                        }
                    }
                }
                // use msgData
            } while (cursor.moveToNext())
        } else {
            // empty box, no SMS
        }

        cursor.close()
    }

}