package com.example.amanda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.amanda.adapter.ViewPagerAdapter
import com.example.amanda.databinding.ActivityGreenhouseBinding
import com.google.android.material.tabs.TabLayoutMediator

val fragmentArray = arrayOf(
    "Data",
    "Controller"
)

class GreenhouseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGreenhouseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGreenhouseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = fragmentArray[position]
        }.attach()

        binding.topAppBar.title = intent.getStringExtra("greenhouse")

    }

}