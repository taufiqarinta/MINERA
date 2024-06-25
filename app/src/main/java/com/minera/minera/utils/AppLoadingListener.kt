package com.minera.minera.utils

import android.content.Context

interface AppLoadingListener {
    fun isLoading(isLoading: Boolean)

    companion object {
        fun runtimeException(context: Context): String {
            return "$context must implement AppLoadingListener"
        }
    }
}