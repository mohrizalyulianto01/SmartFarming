package com.example.amanda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.amanda.data.LoginRepository
import com.example.amanda.data.model.Greenhouse
import com.example.amanda.databinding.ActivityMainBinding
import com.example.amanda.restapi.Greenhouses
import com.example.amanda.restapi.RestApiManager
import com.example.amanda.restapi.ServiceBuilder
import com.example.amanda.restapi.SessionManager
import com.example.amanda.ui.login.LoginActivity
import com.github.mikephil.charting.charts.Chart.LOG_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val TIME_DELAY = 2000
    private var back_pressed: Long = 0
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

//        refreshApp()

        val displayName = intent.getStringExtra("displayName")
        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        var token = "Bearer " + sharedPref.getString("token", "")

        Toast.makeText(
            applicationContext,
            "Welcome $displayName",
            Toast.LENGTH_LONG
        ).show()

        val menu: Menu = binding.bottomNavigation.menu
        var homeFragment = HomeFragment()

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> loadFragment(homeFragment)
                R.id.navigation_market -> loadFragment(MarketFragment())
                R.id.navigation_article -> loadFragment(ArticleFragment())
            }
            return@setOnNavigationItemSelectedListener true
        }

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true
            if(menuItem.itemId == R.id.menu_logout){
                val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    clear()
                    commit()
                }

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        sessionManager = SessionManager(this)
        val restApiManager = RestApiManager()
        loadFragment(homeFragment)
        binding.swipeContainer.setOnRefreshListener {
            restApiManager.getGreenhouses(token) {
                if(it?.isEmpty() == true){
                    Log.d("GREENHOUSE", "GH is empty")
                }
                else{
                    if (it != null) {
                        Log.d("GH", it.toString())
                        homeFragment.loadData(it)
                    }
                }
            }

            binding.swipeContainer.isRefreshing = false
        }

    }

//    private fun refreshApp() {
//        binding.swipeContainer.setOnRefreshListener {
//
//
//          Handler().postDelayed(Runnable {
//                binding.swipeContainer.isRefreshing = false
//            },3000)
//            // Signal SwipeRefreshLayout to start the progress indicator
//
//        }
//
//    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.frameContainer.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                finish()
            } else {
                Toast.makeText(baseContext, "Press back once again to exit!",
                    Toast.LENGTH_SHORT).show()
            }
            back_pressed = System.currentTimeMillis()
        }
    }
}