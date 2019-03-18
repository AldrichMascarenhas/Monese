package com.test.monese

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.presentation.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Set LifecycleObserver
        lifecycle.addObserver(viewModel)

        viewModel.isFirstLaunch.observe(this, Observer {
            handleFirstLaunch(it)
        })


        //Set Toolbar
        setupToolBar()

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_activity_nav_host_fragment) as NavHostFragment? ?: return

        // Get Navigation Controller
        val navController = host.navController
        //Initialise AppBarConfiguration
        appBarConfiguration = AppBarConfiguration.Builder(setOf(R.id.rocketListFragment)).build()

        setupActionBar(navController, appBarConfiguration)

    }

    private fun setupToolBar() {
        val toolbar = findViewById<Toolbar>(R.id.main_activity_toolbar)
        setSupportActionBar(toolbar)
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        // This allows NavigationUI to decide what label to show in the action bar
        // By using appBarConfig, it will also determine whether to
        // show the up arrow or drawer menu icon
        setupActionBarWithNavController(navController, appBarConfig)
    }

    private fun handleFirstLaunch(value: Boolean) {
        if (value){
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.welcome_dialog_title)
                .setMessage(R.string.welcome_dialog_message)
                .setPositiveButton(R.string.welcome_dialog_action) { _, _ ->
                    viewModel.setData()
                }
                .setCancelable(false)
                .show()
        }
    }
}
