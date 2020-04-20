package com.example.simpleloginregisterfirebase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(){

    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        b_login.setOnClickListener {
            startLogin()
        }
    }

    fun startLogin(){
        pb_login.visibility= View.VISIBLE
        b_login.isEnabled=false
        b_login.text=""
        if (et_email.text.isEmpty()||et_password.text.isEmpty()){
            pb_login.visibility= View.GONE
            b_login.isEnabled=true
            b_login.text="LOGIN"
            Toast.makeText(this, "Fields are Empty", Toast.LENGTH_SHORT).show()
        }
        else{
            mAuth.signInWithEmailAndPassword(et_email.text.toString(),et_password.text.toString()).addOnCompleteListener {
                if(!it.isSuccessful){
                    pb_login.visibility= View.GONE
                    b_login.isEnabled=true
                    b_login.text="LOGIN"
                    Toast.makeText(this, "Login Problem", Toast.LENGTH_SHORT).show()
                }
                else{
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

}