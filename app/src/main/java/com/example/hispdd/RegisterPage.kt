package com.example.hispdd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class RegisterPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword2: EditText
    private lateinit var btnSignup2: Button
    private lateinit var btnBackLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        auth = FirebaseAuth.getInstance()

        etFirstName = findViewById(R.id.etFirstname)
        etLastName = findViewById(R.id.etLastname)
        etEmail = findViewById(R.id.etUsername1)
        etPassword2 = findViewById(R.id.etPassword2)
        btnSignup2 = findViewById(R.id.btnSignup2)
        btnBackLogin = findViewById(R.id.btnBackLogin)

        btnSignup2.setOnClickListener {
            registerUser()
        }

        btnBackLogin.setOnClickListener{
            Intent(this, LoginPage::class.java).also {
                startActivity(it)
            }
        }

    }

    private fun registerUser() {
        val firstName = etFirstName.text.toString()
        val lastName = etLastName.text.toString()
        val email = etEmail.text.toString()
        val password = etPassword2.text.toString()

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Create the user in Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password).await()

                // Update the user's display name with their first and last name
                val currentUser = auth.currentUser
                val profileUpdates = userProfileChangeRequest {
                    displayName = "$firstName $lastName"
                }
                currentUser?.updateProfile(profileUpdates)?.await()

                withContext(Dispatchers.Main) {
                    // Show a success message and return to the login screen
                    Toast.makeText(
                        this@RegisterPage,
                        "Registration successful!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // Show an error message
                    Toast.makeText(
                        this@RegisterPage,
                        e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}



