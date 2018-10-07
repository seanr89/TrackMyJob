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
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider



class GoogleSignInActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "GoogleSignInActivity"
    private var mAuth: FirebaseAuth? = null
    private var mGoogleSignInClient : GoogleSignInClient? = null
    private val RC_SIGN_IN = 2

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        //updateUI(account)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_sign_in)

       mAuth = FirebaseAuth.getInstance()

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        this.findViewById<Button>(R.id.btn_sign_in).setOnClickListener(this);
    }

    /**
     *
     */
    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    /**
     *
     */
    private fun signOut()
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)
        // sign out Firebase
        mAuth!!.signOut()
        FirebaseAuth.getInstance().signOut()
        // sign out Google
        //Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback { updateUI(null) }
    }

    /**
     *
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

    fun firebaseAuthWithGoogle(account : GoogleSignInAccount?)
    {
        Log.d(TAG, object{}.javaClass.enclosingMethod.name)

        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful)
                    {
                        //todo
                        Log.w(TAG, "Sign in successful")
                        Toast.makeText(this, "Firebase Auth Complete", Toast.LENGTH_LONG).show()
                    } else
                    {
                        Log.w(TAG, "SignInWithCredential:failure", task.exception)
                        //Toast.makeText(this, "Firebase Auth Failed", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    /**
     * handle the button click events across the view
     * @param v :
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_sign_in -> signIn()
        }// ...
    }
}
