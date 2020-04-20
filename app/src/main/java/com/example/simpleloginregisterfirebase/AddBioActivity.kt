package com.example.simpleloginregisterfirebase

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_bio.*

class AddBioActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bio)

        val textRemaining = 20

        et_bio.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val textlength = p0.toString().length
                tv_count.text= (textRemaining - textlength).toString()
                if ((textRemaining - textlength)<0){
                    tv_count.setTextColor(resources.getColor(R.color.colorPrimary))
                }
                else{
                    tv_count.setTextColor(resources.getColor(R.color.black))
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        b_save.setOnClickListener {
            getUserData().setValue(et_bio.text.toString()).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this,"Save Bio Success", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,"Save Bio Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun getUserData(): DatabaseReference {
        val userid = FirebaseAuth.getInstance().currentUser!!.uid
        val mDatabaseReference = FirebaseDatabase.getInstance().reference
            .child("Users")
            .child(userid)
            .child("bio")
        return mDatabaseReference
    }
}