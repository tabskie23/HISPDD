package com.example.hispdd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginPage : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button
    private lateinit var etUsername: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var tvUsername: TextView
    private lateinit var tilPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // Constant used to identify the sign-in request
        auth = FirebaseAuth.getInstance()
        // Sign out any previously signed in users
        //auth.signOut()

        // Initialize UI components
        btnLogin = findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.btnSignup)
        tvUsername = findViewById(R.id.tvUsername)
        etUsername = findViewById(R.id.etUsername)
        tilPassword = findViewById(R.id.tvInPassword)

        // Set input type of the password field to be hidden
        tilPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

        // Set input type of the password field to be hidden
        btnLogin.setOnClickListener(){
            loginUser()
        }

        // Start the RegisterPage activity
        btnSignup.setOnClickListener(){
            Intent(this, RegisterPage::class.java).also {
                startActivity(it)
            }
        }
    }

    // Function to log in the user with email and password credentials
    private fun loginUser() {
        val email = etUsername.text.toString()
        val password = tilPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@LoginPage, "Successfully logged in", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@LoginPage, MainPage::class.java))
                    }
                } catch (e: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@LoginPage, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Please fill up the credentials", Toast.LENGTH_SHORT).show()
        }
    }
}