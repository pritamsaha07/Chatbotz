package com.example.chatmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var edtemail:EditText
    private lateinit var edtpassword:EditText
    private lateinit var edtlogin:Button
    private lateinit var edtsignup:Button

    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        mAuth= FirebaseAuth.getInstance()
        edtemail=findViewById(R.id.edt_email)
        edtpassword=findViewById(R.id.edt_password)
        edtlogin=findViewById(R.id.edt_login)
        edtsignup=findViewById(R.id.edt_signup)

        edtsignup.setOnClickListener{
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        edtlogin.setOnClickListener {
            val email=edtemail.text.toString();
            val password=edtpassword.text.toString();


            login(email,password)
        }
    }
    private fun login(email:String, password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent= Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@Login ,"User does'nt exists", Toast.LENGTH_SHORT).show()
                }
            }
    }
}