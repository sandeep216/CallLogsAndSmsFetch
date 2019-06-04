package com.example.readtransactions

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import java.util.regex.Pattern



class MainActivity : AppCompatActivity() {

    lateinit var messageBtn : Button
    lateinit var callLogBtn : Button

    companion object {
        const val CAMERA_PERMISSION = 1001
        const val amountReg = "(?i)(?:(?:INR)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)"
        const val merchantNameReg = "(?i)(?:\\sInfo.\\s*)([A-Za-z0-9*]*\\s?-?\\s?[A-Za-z0-9*]*\\s?-?\\.?)"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkForPermission()
        initView()
    }

    private fun initView() {
        messageBtn = findViewById(R.id.btn_message)
        callLogBtn = findViewById(R.id.btn_call)
        initOnClick()
    }

    private fun initOnClick() {
        messageBtn.setOnClickListener {
            startActivity(SmsActivity.getIntent(this))
        }
        callLogBtn.setOnClickListener {
            startActivity(CallLogActivity.getIntent(this))
        }
    }

    private fun checkForPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_SMS, Manifest.permission.READ_CALL_LOG),
                CAMERA_PERMISSION
            )
        } else {
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
    }
}
