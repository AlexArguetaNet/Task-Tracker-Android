package com.example.tasktrack

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tasktrack.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputEditText


class LoginFragment : Fragment() {

    lateinit var mListener: ILogin;
    lateinit var emailInput: TextInputEditText;
    lateinit var passwordInput: TextInputEditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentLoginBinding.inflate(inflater, container, false);
        emailInput = binding.textInputEmail;
        passwordInput = binding.textInputPassword;

        // Login button
        binding.buttonLogin.setOnClickListener {

            val email = emailInput.text.toString();
            val password = passwordInput.text.toString();

            if (email.isEmpty() || password.isEmpty()) {
                val mAlert = AlertDialog.Builder(context)
                    .setTitle("Empty Input")
                    .setMessage("Enter a username and password")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->  })
                    .create()
                    .show();
            } else {
                mListener.login(email, password);
            }

        }

        // Sign up clickable
        binding.textViewSignUp.setOnClickListener {
            mListener.goToSignup();
        }

        // Inflate the layout for this fragment
        return binding.root;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ILogin) {
            mListener = context;
        } else {
            throw RuntimeException("MainActivity must implement ILogin");
        }
    }

    interface ILogin {
        fun login(email: String, password: String)
        fun goToSignup()
    }

}