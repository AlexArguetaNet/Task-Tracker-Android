package com.example.tasktrack

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tasktrack.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity(), LoginFragment.ILogin, SignUpFragment.ISignUp {

    private val TAG = "demoos";
    private lateinit var binding: ActivityMainBinding;
    private lateinit var mAuth: FirebaseAuth;
    private lateinit var db: FirebaseFirestore;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater);
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initializing Firebase instances
        mAuth = Firebase.auth;
        db = Firebase.firestore;


        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainerViewMain.id, LoginFragment())
            .commit();


    }

    override fun login(email: String, password: String) {

    }

    override fun goToSignup() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerViewMain.id, SignUpFragment())
            .addToBackStack(null)
            .commit();
    }

    override fun goBack() {
        supportFragmentManager.popBackStack();
    }

    override fun createAccount(username: String, email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                Log.d(TAG, "createAccount: $it")

            }
            .addOnFailureListener {
                createAlert("Sign Up Error", it.message.toString());
            }

    }


    fun createAlert(title: String, msg: String) {
        val mAlert = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->  })
            .create()
            .show();
    }

}