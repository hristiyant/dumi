package com.dumi.ui.activity.main

import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.dumi.R
import com.dumi.databinding.ActivityMainBinding
import com.dumi.ui.activity.BaseActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<ActivityMainBinding, MainVM>(),
    NavigationView.OnNavigationItemSelectedListener {

    override val layoutId: Int = R.layout.activity_main
    override val viewModelClass = MainVM::class

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavigation()
    }

    private fun setupNavigation() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        navController = Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {

        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onBackPressed() {
        val navDestination = navHostFragment.findNavController().currentDestination
        if (navDestination!!.id == R.id.nav_game) {
            super.onBackPressed()
        }

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true
        drawerLayout.closeDrawers()

        when (menuItem.itemId) {
            R.id.nav_home -> navController.navigate(R.id.homeFragment)
            R.id.nav_game -> navController.navigate(R.id.gameFragment)
            R.id.nav_profile -> navController.navigate(R.id.profileFragment)
        }

        return true
    }
}