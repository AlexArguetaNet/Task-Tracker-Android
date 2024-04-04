package com.example.tasktrack

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
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


        // Check if the user is still logged in
        if (mAuth.currentUser != null) {
            goToHome(mAuth.currentUser!!.uid);
        } else {
            supportFragmentManager.beginTransaction()
                .add(binding.fragmentContainerViewMain.id, LoginFragment())
                .commit();
        }



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

    override fun login(email: String, password: String) {

        // Sign in with Firebase Auth
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                goToHome(mAuth.currentUser!!.uid);

            }
            .addOnFailureListener { createAlert("Login Error", it.message.toString()) }

    }

    override fun createAccount(username: String, email: String, password: String) {

        // Register new account in Firebase auth
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                val id = mAuth.currentUser!!.uid;

                // Save new account in Firestore db
                db.collection("users").document(id)
                    .set(hashMapOf(
                        "username" to username,
                        "email" to email,
                        "id" to id
                    ))
                    .addOnSuccessListener {
                        goToHome(id);
                    }
                    .addOnFailureListener { createAlert("Sign Up Error", it.message.toString()) }

            }
            .addOnFailureListener { createAlert("Sign Up Error", it.message.toString()); }

    }


    fun createAlert(title: String, msg: String) {
        val mAlert = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->  })
            .create()
            .show();
    }

     private fun goToHome(userId: String) {

         // Find user in the database
         db.collection("users").document(userId)
             .get()
             .addOnSuccessListener { docSnapShot ->

                 // Convert document snap shot into user object
                 val user = docSnapShot.toObject(User::class.java);

                 // Create and launch Intent
                 val mIntent = Intent(this, HomeActivity::class.java);
                 mIntent.putExtra("USER", user);

                 startActivity(mIntent);
                 finish();

             }
             .addOnFailureListener {  }

    }

}