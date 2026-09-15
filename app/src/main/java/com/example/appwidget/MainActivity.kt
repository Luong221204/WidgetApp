package com.example.appwidget

import android.R.attr.action
import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    val time = 500
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var count = 0
        var time = 0L
        if(intent != null && intent.action == Constant.TEXT_NEED_TO_CHANGE){
            count = getSharedPreferences(Constant.PREF_NAME, MODE_PRIVATE).getInt(Constant.APP_COUNT, 0)
            time = getSharedPreferences(Constant.PREF_NAME, MODE_PRIVATE).getLong(Constant.TIME, 0)
        }else{
            count = getSharedPreferences(Constant.PREF_NAME, MODE_PRIVATE).getInt(Constant.APP_COUNT, 0)
            time = getSharedPreferences(Constant.PREF_NAME, MODE_PRIVATE).getLong(Constant.TIME, 0)
        }
        val textView = findViewById<TextView>(R.id.tvCounter)
        textView.text = count.toString()
        findViewById<Button>(R.id.btnIncrement).setOnClickListener {
            count++
            textView.text = count.toString()
            getSharedPreferences(Constant.PREF_NAME, MODE_PRIVATE).edit { putInt(Constant.APP_COUNT, count) }
        }
        findViewById<Button>(R.id.btnUpdateTime).setOnClickListener {
            time = System.currentTimeMillis()
            getSharedPreferences(Constant.PREF_NAME, MODE_PRIVATE).edit { putLong(Constant.TIME, time) }
        }
        findViewById<Button>(R.id.btnConfirm).setOnClickListener {
            val intent = Intent(this, MyAppWidget::class.java).apply {
            action = Constant.PREF_NAME
            `package` = packageName
        }
            sendBroadcast(intent)
        }
        Log.d("DUCLUONG","hahahahahahahahahahahaahahahahahaha")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun changeTaoName(users: MutableList<User>) {
        users.add(User("huhu"))
    }
}

data class User(var name: String)
