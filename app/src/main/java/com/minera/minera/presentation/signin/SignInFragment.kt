package com.minera.minera.presentation.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.minera.core.data.utils.Resource
import com.minera.core.domain.constant.Constants
import com.minera.core.domain.constant.RegistrationStatus
import com.minera.minera.databinding.FragmentSignInBinding
import com.minera.minera.presentation.home.MainActivity
import com.minera.minera.presentation.registration.first.FirstRegistrationActivity
import com.minera.minera.presentation.registration.second.SecondRegistrationActivity
import com.minera.minera.utils.AppLoadingListener
import com.minera.minera.utils.toast
import com.minera.minera.utils.validateEditText
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModel()
    private var appLoadingListener: AppLoadingListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if ((context is AppLoadingListener)) appLoadingListener = context
        else throw RuntimeException(AppLoadingListener.runtimeException(context))
    }

    override fun onDetach() {
        super.onDetach()
        appLoadingListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isUserExist()
        setActions()
    }

    private fun isUserExist() {
        viewModel.isUserExist().observe(viewLifecycleOwner) { exist ->
            if (exist) navigateRegist(MainActivity::class.java)
        }
    }

    private fun setActions() {
        binding.apply {
            btnSignIn.setOnClickListener {
                if (validateEditText(listOf(etEmail, etPassword))) {
                    return@setOnClickListener
                }

                signIn(etEmail.text.toString(), etPassword.text.toString())
            }
        }
    }

    private fun signIn(email: String, password: String) {
        viewModel.signIn(email, password).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    appLoadingListener?.isLoading(false)
                    toast(it.message ?: Constants.ERROR_MSG)
                }
                is Resource.Loading -> {
                    appLoadingListener?.isLoading(true)
                }
                is Resource.Success -> {
                    checkRegistrationStatus()
                }
            }
        }
    }

    private fun checkRegistrationStatus() {
        lifecycleScope.launch {
            viewModel.getRegistrationStatus().collect {
                when (it) {
                    is Resource.Error -> {
                        appLoadingListener?.isLoading(false)
                        toast(it.message ?: "")
                        viewModel.signOut()
                    }
                    is Resource.Success -> {
                        appLoadingListener?.isLoading(false)
                        it.data?.let { status -> handleRegist(status) }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun handleRegist(data: Int) {
        when (data) {
            RegistrationStatus.First.status -> {
                navigateRegist(FirstRegistrationActivity::class.java)
            }
            RegistrationStatus.Second.status -> {
                navigateRegist(SecondRegistrationActivity::class.java)
            }
            RegistrationStatus.Third.status -> {
                navigateRegist(MainActivity::class.java)
            }
        }
    }

    private fun navigateRegist(destination: Class<*>) {
        startActivity(Intent(requireContext(), destination).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}