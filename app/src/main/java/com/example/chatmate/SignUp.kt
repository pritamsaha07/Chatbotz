package com.example.chatmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var edtname: EditText
    private lateinit var edtemail: EditText
    private lateinit var edtpassword: EditText

    private lateinit var edtsignup: Button
    private lateinit var mDbref: DatabaseReference

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        mAuth= FirebaseAuth.getInstance()
        edtemail=findViewById(R.id.edt_email)
        edtpassword=findViewById(R.id.edt_password)
        edtname=findViewById(R.id.edt_name)
        edtsignup=findViewById(R.id.edt_signup)

        edtsignup.setOnClickListener {
            val name= edtname.text.toString()
            val email = edtemail.text.toString()
            val passwrod = edtpassword.text.toString()

            signup(name,email, passwrod)
        }

    }
    private fun signup(name:String,email:String, password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                     adduserdatabase(name, email, mAuth.currentUser?.uid!!)
                     val intent= Intent(this@SignUp, MainActivity::class.java)
                     finish()
                     startActivity(intent)

                }
                else {
                        Toast.makeText(this@SignUp,"Check your Network", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun adduserdatabase(name:String,email:String, uid:String){
        mDbref=FirebaseDatabase.getInstance().getReference()
        mDbref.child("user").child(uid).setValue(user(name,email,uid))
    }
}