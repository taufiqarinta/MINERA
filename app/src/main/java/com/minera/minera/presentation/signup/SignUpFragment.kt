package com.minera.minera.presentation.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.minera.core.data.utils.Resource
import com.minera.core.domain.constant.Constants
import com.minera.core.domain.model.User
import com.minera.minera.databinding.FragmentSignUpBinding
import com.minera.minera.presentation.auth.AuthActivity
import com.minera.minera.utils.AppLoadingListener
import com.minera.minera.utils.hide
import com.minera.minera.utils.show
import com.minera.minera.utils.toast
import com.minera.minera.utils.validateEditText
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModel()
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
        _binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActions()
    }

    private fun setActions() {
        binding.apply {
            btnSignUp.setOnClickListener {
                if (validateEditText(listOf(etUsername, etEmail, etPassword))) {
                    return@setOnClickListener
                }

                val user = User(
                    username = etUsername.text.toString(),
                    email = etEmail.text.toString()
                )
                signUp(user, etPassword.text.toString())
            }
        }
    }

    private fun signUp(user: User, password: String) {
        viewModel.signUp(user, password).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    appLoadingListener?.isLoading(false)
                    toast(it.message ?: Constants.ERROR_MSG)
                }
                is Resource.Loading -> {
                    appLoadingListener?.isLoading(true)
                }
                is Resource.Success -> {
                    appLoadingListener?.isLoading(false)
                    toast(it.data ?: "")
                    (requireActivity() as AuthActivity).binding.viewPager2.currentItem = 0
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}