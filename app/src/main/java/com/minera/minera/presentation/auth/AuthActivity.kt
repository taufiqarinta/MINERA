package com.minera.minera.presentation.auth

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.minera.minera.R
import com.minera.minera.core.ui.ViewPagerAdapter
import com.minera.minera.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabLayoutSetup()
        viewPagerSetup()
    }

    private fun viewPagerSetup() {
        val fragments = arrayListOf(
            SignInFragment(),
            SignUpFragment()
        )

        binding.viewPager2.isUserInputEnabled = false

        val viewPagerAdapter =
            ViewPagerAdapter(fragments, supportFragmentManager, lifecycle)
        binding.viewPager2.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Masuk"
                1 -> tab.text = "Daftar"
            }
        }.attach()
    }

    private fun tabLayoutSetup() {
        val tabBackgroundLeft = ResourcesCompat.getDrawable(resources, R.drawable.tab_item_left, theme)
        val tabBackgroundLeftSelected = ResourcesCompat.getDrawable(resources, R.drawable.tab_item_left_selected, theme)
        val tabBackgroundRight = ResourcesCompat.getDrawable(resources, R.drawable.tab_item_right, theme)
        val tabBackgroundRightSelected = ResourcesCompat.getDrawable(resources, R.drawable.tab_item_right_selected, theme)

        for (i in 0 until binding.tabLayout.tabCount) {
            val tab = binding.tabLayout.getTabAt(i)
            when (i) {
                0 -> tab?.view?.background = tabBackgroundLeftSelected
                1 -> tab?.view?.background = tabBackgroundRight
            }
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> tab.view.background = tabBackgroundLeftSelected
                    1 -> tab.view.background = tabBackgroundRightSelected
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> tab.view.background = tabBackgroundLeft
                    1 -> tab.view.background = tabBackgroundRight
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // Do nothing
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}