package com.example.tasktrack

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.tasktrack.databinding.ActivityHomeBinding
import com.example.tasktrack.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding;
    private lateinit var drawerNavView: NavigationView;
    private lateinit var drawerLayout: DrawerLayout;
    private lateinit var bottomNavigationView: BottomNavigationView;
    private lateinit var mAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(this.layoutInflater);
        setContentView(binding.root);
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase variables
        mAuth = Firebase.auth;

        // Set up navigation drawer
        drawerNavView = binding.navigationViewHome;
        drawerLayout = binding.main;

        binding.imageViewMenuHome.setOnClickListener {
            drawerLayout.open();
        }

        drawerNavView.setNavigationItemSelectedListener {  menuItem ->
            when(menuItem.itemId) {
                R.id.navAccountSettings -> {

                }
                R.id.navLogout -> {
                    mAuth.signOut();
                    startActivity(Intent(this, MainActivity::class.java));
                    finish();
                }
            }
            drawerLayout.close();
            true;
        }



    }


}