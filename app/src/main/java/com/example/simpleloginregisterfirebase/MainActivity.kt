package com.example.simpleloginregisterfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mAuth = FirebaseAuth.getInstance()
        getUserData().child("name").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tv_name.text=dataSnapshot.value!!.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        getUserData().child("bio").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.value!=null){
                    tv_bio.text=p0.value!!.toString()
                }
            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })
        b_logout.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        b_edit_bio.setOnClickListener {
            startActivity(Intent(this,AddBioActivity::class.java))
        }
    }

    fun getUserData(): DatabaseReference {
        val userid = FirebaseAuth.getInstance().currentUser!!.uid
        val mDatabaseReference = FirebaseDatabase.getInstance().reference
            .child("Users")
            .child(userid)
        return mDatabaseReference
    }
}
