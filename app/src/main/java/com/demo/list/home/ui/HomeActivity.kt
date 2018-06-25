package com.demo.list.home.ui

import android.Manifest
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.support.v4.app.ShareCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.widget.LinearLayoutManager
import com.demo.list.BR
import com.demo.list.R
import com.demo.list.binding.RecyclerViewConfiguration
import com.demo.list.binding.SnackbarConfiguration
import com.demo.list.binding.ToolbarConfiguration
import com.demo.list.databinding.ActivityHomeBinding
import com.demo.list.helper.observe
import com.demo.list.helper.showSnackBar
import com.demo.list.home.base.BaseActivity
import com.demo.list.home.model.ListItem
import com.demo.list.home.viewmodel.HomeViewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import javax.inject.Inject


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(), EasyPermissions.PermissionCallbacks {

    @Inject
    lateinit var viewFactory: ViewModelProvider.Factory

    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var itemsAdapter: ItemsAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var activityHomeBinding: ActivityHomeBinding

    private val recyclerViewConfiguration = RecyclerViewConfiguration()

    private val toolbarConfiguration = ToolbarConfiguration()

    override fun getViewModel() = ViewModelProviders.of(this@HomeActivity, viewFactory)
            .get(HomeViewModel::class.java).also {
                homeViewModel = it
            }

    override fun getLayoutId() = R.layout.activity_home

    override fun executePendingVariablesBinding() {
        getViewDataBinding().also {
            it.setVariable(BR.homeViewModel, homeViewModel)
            it.setVariable(BR.recyclerViewConfig, recyclerViewConfiguration)
            it.setVariable(BR.snackBarConfig, snackBarConfiguration)
            it.setVariable(BR.toolbarConfig, toolbarConfiguration)
            activityHomeBinding = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        homeViewModel.reload.postValue(true)
        subscribeToItemsLivedata()
        subscribeToShare()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this@HomeActivity)

    private fun initUI() {
        itemsAdapter.navigator = navigator
        recyclerViewConfiguration.setRecyclerConfig(itemsAdapter, linearLayoutManager)
        toolbarConfiguration.newState(getString(R.string.app_name))
                .setTitleColor(ContextCompat.getColor(this, R.color.white))
                .commit()
    }

    private fun subscribeToItemsLivedata() {
        homeViewModel.itemsLiveData.observe(this) {
            it?.let {
                if (it.isSuccess()) {
                    itemsAdapter.setData(it.value)
                } else {

                }
            }
        }

    }

    private val navigator = object : INavigator {
        override fun onShareClicked(listItem: ListItem) {
            if (seekExternalStoragePermission()) {
                homeViewModel.switchImageDownload.postValue(listItem)
            }
        }
    }

    private fun subscribeToShare() {
        homeViewModel.pathLiveData.observe(this) {
            it?.let {
                if (it.isSuccess()) {
                    shareImage(it.value)
                } else {

                }
            }
        }
    }

    private fun shareImage(path: String) {

        try {
            val uriToImage = FileProvider.getUriForFile(
                    this@HomeActivity, getString(R.string.file_provider_authority), File(path))

            val shareIntent = ShareCompat.IntentBuilder.from(this@HomeActivity)
                    .setType("image/png")
                    .setStream(uriToImage)
                    .setChooserTitle(getString(R.string.str_share_to_earn))
                    .setText(getString(R.string.str_share_to_earn))
                    .setSubject(getString(R.string.str_share_to_earn))
                    .intent
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)


            if (shareIntent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(shareIntent, getString(R.string.str_share_to_earn)))
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()

        }


    }

    @AfterPermissionGranted(RC_WRITE_EXT_PER)
    private fun seekExternalStoragePermission(): Boolean {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return try {
                if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
                    true
                } else {
                    snackBarConfiguration.showSnackBar(getString(R.string.str_cannot_save),
                            SnackbarConfiguration.Type.NEUTRAL,
                            Snackbar.LENGTH_SHORT)
                    false
                }
            } catch (execption: RuntimeException) {
                snackBarConfiguration.showSnackBar(getString(R.string.str_cannot_save),
                        SnackbarConfiguration.Type.NEUTRAL,
                        Snackbar.LENGTH_SHORT)
                false
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.perm_ext),
                    RC_WRITE_EXT_PER, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return false
        }
    }

    companion object {
        const val RC_WRITE_EXT_PER = 101

    }

}