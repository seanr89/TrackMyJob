package com.example.sean.trackmyjob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import android.content.Intent
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import com.example.sean.trackmyjob.Models.Enums.UserStatus
import com.example.sean.trackmyjob.Repositories.UserRepository
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider



class GoogleSignInActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "GoogleSignInActivity"
    private var mAuth: FirebaseAuth? = null
    private var mGoogleSignInClient : GoogleSignInClient? = null
    private val RC_SIGN_IN = 2
    private lateinit var mAuthListener : FirebaseAuth.AuthStateListener

    /**
     * overriden method to first check if the user is already signed in
     * and to handle authentication state changes
     */
    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account != null)
        {
            signIn()
            //startActivity(Intent(this, MainActivity::class.java))
        }
        else
        {
            showSignInButton()
            mAuthListener = FirebaseAuth.AuthStateListener(object : FirebaseAuth.AuthStateListener, (FirebaseAuth) -> Unit {
                override fun invoke(p1: FirebaseAuth) {
                }

                override fun onAuthStateChanged(p0: FirebaseAuth) {
                    var user = p0.getCurrentUser()
                    if (user != null){
                        Log.i(TAG,"user logged in with email : ${user.email}")
                    }
                }
            })
            mAuth!!.addAuthStateListener(mAuthListener)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_sign_in)

        mAuth = FirebaseAuth.getInstance()

        showSignInButton()

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        this.findViewById<SignInButton>(R.id.btn_sign_in).setOnClickListener(this)
    }

    /**
     * handle the request by the user to sign in
     */
    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        hideSignInButton()
    }


    /**
     * trigger application sign the user out!
     */
    private fun signOut()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        // sign out Firebase
        mAuth!!.signOut()
        FirebaseAuth.getInstance().signOut()
        showSignInButton()
        // sign out Google
        //Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback { updateUI(null) }
    }

    /**
     * handle the sign-in/authentication event activity!
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            }catch (e : ApiException) {

            }
            //handleSignInResult(task)
        }
    }

    private fun hideSignInButton()
    {
        val progressBar = this.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        val signInButton = this.findViewById<SignInButton>(R.id.btn_sign_in)
        signInButton.visibility = View.INVISIBLE
    }

    /**
     * handle the displaying/visibility of the sign in button
     */
    private fun showSignInButton()
    {
        val progressBar = this.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE
        val signInButton = this.findViewById<SignInButton>(R.id.btn_sign_in)
        signInButton.visibility = View.VISIBLE
    }

//    /**
//     *
//     */
//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>)
//    {
//        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
//        try {
//            val account = completedTask.getResult(ApiException::class.java)
//            // Signed in successfully, show authenticated UI.
//            //updateUI(account)
//            firebaseAuthWithGoogle(account)
//        } catch (e: ApiException) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w("Yeoo", "signInResult:failed code=" + e.statusCode)
//            //updateUI(null)
//        }
//    }

    /**
     * Handle google authentication from firebase
     * @param account : google sign-in
     */
    fun firebaseAuthWithGoogle(account : GoogleSignInAccount?)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful)
                    {
                        Log.w(TAG, "Sign in successful")
                        Toast.makeText(this, "Firebase Auth Complete", Toast.LENGTH_LONG).show()
                        val userRepo = UserRepository()
                        userRepo.initCurrentUserIfFirstTime {
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                    } else
                    {
                        Log.w(TAG, "SignInWithCredential:failure", task.exception)
                        Toast.makeText(this, "Firebase Auth Failed", Toast.LENGTH_LONG).show()
                        //startActivity(Intent(this, MainActivity::class.java))
                    }
                }
    }

    /**
     * handle the button click events across the view
     * @param v : view item that was clicked!
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_sign_in -> signIn()
        }// ...
    }
}
