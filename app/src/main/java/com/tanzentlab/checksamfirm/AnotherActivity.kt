package com.tanzentlab.checksamfirm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.util.Objects

class AnotherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(this))
        Toast.makeText(applicationContext, getString(R.string.abc_plz_insert_right_model_and_region), Toast.LENGTH_LONG).show()
        restart(this, 0)
    }

    companion object {

        fun restart(activity: Activity, delay: Int) {
            var delay = delay
            if (delay == 0) {
                delay = 1
            }
            val restartIntent = activity.packageManager.getLaunchIntentForPackage(activity.packageName)
            restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_NO_ANIMATION and Intent.FLAG_ACTIVITY_NO_HISTORY)
            val intent = PendingIntent.getActivity(activity, 0, restartIntent, PendingIntent.FLAG_ONE_SHOT)
            val manager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            Objects.requireNonNull(manager).set(AlarmManager.RTC, System.currentTimeMillis() + delay, intent)
            finishAffinity(activity)
        }

        private fun finishAffinity(activity: Activity) {
            activity.setResult(Activity.RESULT_CANCELED)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.finishAffinity()
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                activity.runOnUiThread { activity.finishAffinity() }
            }
        }
    }
}
