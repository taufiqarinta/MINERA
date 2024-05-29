package com.minera.minera.presentation.onboarding

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minera.minera.databinding.ViewpagerContainerBinding

class OnBoardingAdapter: RecyclerView.Adapter<OnBoardingAdapter.OnboardingViewHolder>() {

    private var onBoardingItems: MutableList<OnBoardingItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = ViewpagerContainerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OnboardingViewHolder(binding)
    }

    override fun getItemCount(): Int = onBoardingItems.size

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        with(holder) {
            with(onBoardingItems[position]) {
                binding.tvOnboardingTitle.text = this.title
                binding.tvOnboardingDescription.text = this.description
                binding.ivOnboarding.setImageResource(this.img)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewItem(data: List<OnBoardingItem>) {
        onBoardingItems.clear()
        onBoardingItems.addAll(data)
        notifyDataSetChanged()
    }

    inner class OnboardingViewHolder(val binding: ViewpagerContainerBinding): RecyclerView.ViewHolder(binding.root)
}