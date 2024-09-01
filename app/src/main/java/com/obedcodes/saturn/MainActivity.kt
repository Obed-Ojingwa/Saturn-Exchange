package com.obedcodes.saturn

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {


    /*
    *
    *
    * This activity is part of the security field, befpre the user accesses the application to avoid ...
    *
    * but I will be removing it during the beta test*/
    private lateinit var auth: FirebaseAuth
    private lateinit var googleApiClient: GoogleApiClient

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this) { Toast.makeText(this, "GoogleApiClient connection failed", Toast.LENGTH_SHORT).show() }
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        findViewById<Button>(R.id.btnGoogleSignIn).setOnClickListener {
            signInWithGoogle()
        }

        findViewById<Button>(R.id.signUpButton).setOnClickListener {
            signUpWithEmail()
        }

        findViewById<Button>(R.id.signInButton).setOnClickListener {
            signInWithEmail()
        }

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            signOut()
        }

        val miningButton = findViewById<Button>(R.id.BtnMining)
        miningButton.setOnClickListener {
            val intent = Intent(this, MiningActivity::class.java)
            startActivity(intent)
        }

        val marketDataButton: Button = findViewById(R.id.btnMarketDataButton)
        marketDataButton.setOnClickListener {
            val intent = Intent(this, MarketDataActivity::class.java)
            startActivity(intent)
        }


    }

    private fun signUpWithEmail() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Toast.makeText(this, "Sign Up Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInWithEmail() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                        val intent = Intent(this,MiningActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Sign In Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        auth.signOut()
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            updateUI(null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = data?.let { Auth.GoogleSignInApi.getSignInResultFromIntent(it) }
            if (result != null) {
                if (result.isSuccess) {
                    val account = result?.signInAccount
                    firebaseAuthWithGoogle(account)
                    val intent = Intent(this,MiningActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.w(TAG, "Google sign in failed")
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                    val intent = Intent(this,MiningActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "Signed in as ${user.email}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Not signed in", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val TAG = "MainActivity"
    }
}

