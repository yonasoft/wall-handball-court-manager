package com.yonasoft.handballcourtmanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.yonasoft.handballcourtmanager.databinding.ActivityMainBinding

//Change colors for results, matches, players, and list items.

class MainActivity : AppCompatActivity() {

    private var binding:ActivityMainBinding?=null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setup data binding
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        //Sets up navigation
        setupNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    //Sets up navigation
    private fun setupNavigation() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setSupportActionBar(binding!!.toolbar)
        //Instantiate app ba
        appBarConfiguration = AppBarConfiguration(setOf(R.id.rosterFragment,R.id.matchesFragment,R.id.resultsFragment))
        //Navigation for tool bar
        binding!!.toolbar.setupWithNavController(navController,appBarConfiguration)
        //Navigation for bottom nav bar
        binding!!.bottomNav.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tool_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  item.onNavDestinationSelected(findNavController(binding!!.navHostFragment.id))||super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }
}




