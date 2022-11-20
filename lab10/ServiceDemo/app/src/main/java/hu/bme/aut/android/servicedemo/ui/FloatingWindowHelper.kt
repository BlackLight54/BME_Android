package hu.bme.aut.android.servicedemo.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.location.Location
import android.view.*
import android.widget.TextView
import hu.bme.aut.android.servicedemo.R

class FloatingWindowHelper(private val context: Context) {

    private var windowManager: WindowManager? = null
    private var floatingView: View? = null
    private var tvFloatLat: TextView? = null
    private var tvFloatLng: TextView? = null

    @SuppressLint("ClickableViewAccessibility")
    fun showFloatingWindow() {
        hideFloatingWindow()

        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val floatingView: View = LayoutInflater.from(context).inflate(R.layout.float_layout, null)
        tvFloatLat = floatingView.findViewById(R.id.tvFloatLat)
        tvFloatLng = floatingView.findViewById(R.id.tvFloatLng)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)

        params.gravity = Gravity.TOP or Gravity.START
        params.x = 0
        params.y = 100

        windowManager?.addView(floatingView, params)

        floatingView.setOnTouchListener(object : View.OnTouchListener {
            private var initialX: Int = 0
            private var initialY: Int = 0
            private var initialTouchX: Float = 0.toFloat()
            private var initialTouchY: Float = 0.toFloat()

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                    }
                    MotionEvent.ACTION_MOVE -> {
                        params.x = initialX + (event.rawX - initialTouchX).toInt()
                        params.y = initialY + (event.rawY - initialTouchY).toInt()
                        windowManager?.updateViewLayout(floatingView, params)
                    }
                }
                return false
            }
        })

        this.floatingView = floatingView
    }

    fun hideFloatingWindow() {
        if (floatingView != null) {
            windowManager?.removeView(floatingView)
            floatingView = null
            tvFloatLat = null
            tvFloatLng = null
        }
    }

    fun updateLocation(location: Location) {
        tvFloatLat?.let { it.text = "Lat: ${location.latitude}" }
        tvFloatLng?.let { it.text = "Lng: ${location.longitude}" }
    }

}