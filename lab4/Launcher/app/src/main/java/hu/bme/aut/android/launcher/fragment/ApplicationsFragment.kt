package hu.bme.aut.android.launcher.fragment

import android.content.ComponentName
import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import hu.bme.aut.android.launcher.adapter.ApplicationsAdapter
import hu.bme.aut.android.launcher.databinding.FragmentApplicationsBinding
import hu.bme.aut.android.launcher.model.AppInfo

class ApplicationsFragment : Fragment(), ApplicationsAdapter.OnApplicationClickedListener {

    private lateinit var binding: FragmentApplicationsBinding;

    private var applications: List<AppInfo> = emptyList()

    private fun loadApplications() {
        val packageManager = requireActivity().packageManager

        // creating a list of every application we want to display
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val apps = packageManager.queryIntentActivities(mainIntent, 0)

        // sorting by name, mapping to AppInfo
        applications = apps
            .sortedWith(ResolveInfo.DisplayNameComparator(packageManager))
            .map { app ->
                AppInfo(
                    title = app.loadLabel(packageManager),
                    icon = app.activityInfo.loadIcon(packageManager),
                    className = ComponentName(app.activityInfo.applicationInfo.packageName, app.activityInfo.name)
                )
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadApplications()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplicationsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    private fun setupRecyclerView() {
        val adapter = ApplicationsAdapter()
        adapter.listener = this
        binding.rvApplications.layoutManager = GridLayoutManager(context, 4)
        binding.rvApplications.adapter = adapter
        adapter.setApps(applications)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }
    override fun onApplicationClicked(appInfo: AppInfo) {
        requireContext().startActivity(appInfo.intent)
    }
}