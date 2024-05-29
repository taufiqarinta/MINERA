package com.minera.minera.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.minera.minera.R
import com.minera.minera.databinding.ActivityOnBoardingBinding
import com.minera.minera.presentation.auth.AuthActivity

class OnBoardingActivity : AppCompatActivity() {

    private var _binding: ActivityOnBoardingBinding? = null
    private val binding get() = _binding!!
    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        _binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupOnBoardingItems()
        binding.viewPager2.adapter = onBoardingAdapter

        binding.btnNext.setOnClickListener {
            if (binding.viewPager2.currentItem + 1 < onBoardingAdapter.itemCount) {
                binding.viewPager2.currentItem += 1
            } else {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
//                onBoardingFinished()
                finish()
            }
        }
    }

    private fun setupOnBoardingItems() {
        val onboardingItems: MutableList<OnBoardingItem> = ArrayList()

        val onboarding1 = OnBoardingItem(
            resources.getString(R.string.onboarding_1),
            resources.getString(R.string.onboarding_d_1),
            R.drawable.onboarding_1
        )
        val onboarding2 = OnBoardingItem(
            resources.getString(R.string.onboarding_2),
            resources.getString(R.string.onboarding_d_2),
            R.drawable.onboarding_2
        )
        onboardingItems.add(onboarding1)
        onboardingItems.add(onboarding2)
        onBoardingAdapter = OnBoardingAdapter()
        onBoardingAdapter.setNewItem(onboardingItems)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}