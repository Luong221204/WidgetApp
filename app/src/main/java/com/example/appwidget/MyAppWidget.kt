package com.example.appwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Implementation of App Widget functionality.
 */
class MyAppWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        if(intent?.action == Constant.TEXT_NEED_TO_CHANGE)
        updateAppWidgetText(context)
    }
    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val count = context.getSharedPreferences(Constant.PREF_NAME, MODE_PRIVATE).getInt(Constant.APP_COUNT, 0)
    val time = context.getSharedPreferences(Constant.PREF_NAME, MODE_PRIVATE).getLong(Constant.TIME, 0)
    val formattedTime = if (time > 0) {
        val formatter = SimpleDateFormat("hh:mm a", Locale.US)
        formatter.format(Date(time))
    } else {
        "--:--" // Trường hợp chưa có dữ liệu lưu trữ
    }
    val views = RemoteViews(context.packageName, R.layout.my_app_widget)
    views.setTextViewText(R.id.tv_last_updated, "$count @${formattedTime.toString()}")
    val intent = Intent(context, MainActivity::class.java).apply {
        action = Constant.TEXT_NEED_TO_CHANGE
    }
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    views.setOnClickPendingIntent(R.id.btn_update, pendingIntent)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
internal fun updateAppWidgetText(
    context: Context
) {
    val count = context.getSharedPreferences(Constant.PREF_NAME, MODE_PRIVATE).getInt(Constant.APP_COUNT, 0)
    val time = context.getSharedPreferences(Constant.PREF_NAME, MODE_PRIVATE).getLong(Constant.TIME, 0)
    val formattedTime = if (time > 0) {
        val formatter = SimpleDateFormat("hh:mm a", Locale.US)
        formatter.format(Date(time))
    } else {
        "--:--" // Trường hợp chưa có dữ liệu lưu trữ
    }
    val views = RemoteViews(context.packageName, R.layout.my_app_widget)
    views.setTextViewText(R.id.tv_last_updated, "$count @${formattedTime.toString()}")
    AppWidgetManager.getInstance(context).updateAppWidget(
        ComponentName(context, MyAppWidget::class.java), views
    )
}