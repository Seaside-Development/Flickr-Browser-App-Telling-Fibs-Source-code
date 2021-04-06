package com.natebrate.flickrbrowser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnLinkToLoginScreen.setOnClickListener {
            onBackPressed()
            //startActivity(Intent(this@Register, LoginActivity::class.java))
        }

        btnRegister.setOnClickListener {
            when {
                TextUtils.isEmpty(register_email.text.toString().trim {
                    it <= ' '
                }) -> {
                    Toast.makeText(
                            this@Register,
                            "Please enter an email.",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(register_password.text.toString().trim {
                    it <= ' '
                }) -> {
                    Toast.makeText(
                            this@Register,
                            "Please enter a password.",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = register_email.text.toString().trim { it <= ' ' }
                    val password: String = register_password.text.toString().trim { it <= ' ' }

                    //Create an instance and create a register user with email and password
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(
                                    OnCompleteListener<AuthResult> { task ->

                                        //if the registration is successful
                                        if (task.isSuccessful) {
                                            //Firebase registered user
                                            val firebaseUser: FirebaseUser = task.result!!.user!!

                                            Toast.makeText(
                                                    this@Register,
                                                    "You are resisted user!",
                                                    Toast.LENGTH_SHORT
                                            ).show()

                                            /*
                                The users registration once successful we'll move unto this part
                                here
                                 */

                                            //Successful registration
                                            val intent = Intent(this@Register, MainActivity::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            intent.putExtra("user_id", firebaseUser.uid)
                                            intent.putExtra("email_id", email)
                                            startActivity(intent)
                                            finish()
                                        } else {
                                            //if registration not successful
                                            Toast.makeText(
                                                    this@Register,
                                                    task.exception!!.message.toString(),
                                                    Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    })
                }
            }
        }
    }
}