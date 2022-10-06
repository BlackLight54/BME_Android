package hu.bme.aut.android.launcher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.launcher.databinding.ItemApplicationBinding
import hu.bme.aut.android.launcher.model.AppInfo

class ApplicationsAdapter : RecyclerView.Adapter<ApplicationsAdapter.ViewHolder>() {

    private val applications: MutableList<AppInfo> = mutableListOf()

    private lateinit var binding: ItemApplicationBinding

    var listener: OnApplicationClickedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = applications[position]
        holder.name.text = app.title
        holder.icon.setImageDrawable(app.icon)
        holder.app = app
    }

    override fun getItemCount(): Int = applications.size

    fun setApps(apps: List<AppInfo>) {
        applications.clear()
        applications.addAll(0, apps)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = binding.tvName
        var icon: ImageView = binding.ivIcon

        var app: AppInfo? = null

        init {
            itemView.setOnClickListener {
                app?.let {
                    listener?.onApplicationClicked(it)
                }
            }
        }
    }

    interface OnApplicationClickedListener {
        fun onApplicationClicked(appInfo: AppInfo)
    }
}