package com.example.tasktrack

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tasktrack.databinding.FragmentSignUpBinding
import com.google.android.material.textfield.TextInputEditText


/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {

    lateinit var mListener: ISignUp;
    lateinit var usernameInput: TextInputEditText;
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
        // Inflate the layout for this fragment
        val binding = FragmentSignUpBinding.inflate(inflater, container, false);
        usernameInput = binding.textInputUsername;
        emailInput = binding.textInputEmail;
        passwordInput = binding.textInputPassword;

        // Submit button
        binding.buttonSubmit.setOnClickListener {

            val username = usernameInput.text.toString();
            val email = emailInput.text.toString();
            val password = passwordInput.text.toString();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                val mAlert = AlertDialog.Builder(context)
                    .setTitle("Empty Input")
                    .setMessage("Please fill out the entire form")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->  })
                    .create()
                    .show();
            } else {
                mListener.createAccount(username, email, password);
            }

        }

        // Back button clickable
        binding.imageViewBack.setOnClickListener {
            mListener.goBack();
        }

        return binding.root;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ISignUp) {
            mListener = context;
        } else {
            throw RuntimeException("MainActivity must implement ISignUp");
        }
    }

    interface ISignUp {
        fun goBack()
        fun createAccount(username: String, email: String, password: String)
    }

}