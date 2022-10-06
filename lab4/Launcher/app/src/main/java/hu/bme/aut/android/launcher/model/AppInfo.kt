package hu.bme.aut.android.launcher.model

import android.content.ComponentName
import android.content.Intent
import android.graphics.drawable.Drawable

class AppInfo(
    val title: CharSequence,
    val icon: Drawable,
    className: ComponentName
) {

    val intent: Intent

    init {
        intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        intent.component = className
    }

}