package com.example.simpleloginregisterfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(){

    lateinit var mAuth: FirebaseAuth
    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth= FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference.child("Users")
        b_register.setOnClickListener {
            registerUser()
        }
    }

    fun saveUserData(uid : String, username: String, email:String){
        val current_user_db = mDatabase.child(uid)
        current_user_db.child("name").setValue(username)
        current_user_db.child("email").setValue(email)
    }

    fun registerUser(){
        var email = et_email_reg.text.toString()
        var password = et_password_reg.text.toString()
        var confirmPassword = et_conf_password_reg.text.toString()
        var username = et_username.text.toString()
        if(email.isEmpty()&&password.isEmpty()&&confirmPassword.isEmpty()&&username.isEmpty()){
            Toast.makeText(this,"Tidak boleh ada kolom kosong", Toast.LENGTH_SHORT).show()
        }
        else{
            if (confirmPassword != password){
                Toast.makeText(this,"Password yang diinputkan tidak sama", Toast.LENGTH_SHORT).show()
            }
            else{
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful){
                        val uid = mAuth.currentUser!!.uid
                        saveUserData(uid,username,email)
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this, "Register Problem", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}