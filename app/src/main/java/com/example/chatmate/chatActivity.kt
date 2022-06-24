package com.example.chatmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class chatActivity : AppCompatActivity() {

    private lateinit var msgrecyler:RecyclerView
    private lateinit var msgbox:EditText
    private lateinit var sendbutton:ImageView
    private lateinit var adapter: msg_adapter
    private lateinit var msglist:ArrayList<message>
    private lateinit var mdbref:DatabaseReference

    var receiverrroom:String?=null
    var senderroom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name=intent.getStringExtra("name")
        val receiveruid=intent.getStringExtra("uid")

        val senderuid=FirebaseAuth.getInstance().currentUser?.uid
        mdbref=FirebaseDatabase.getInstance().getReference()

        senderroom=receiveruid+ senderuid

        receiverrroom=senderuid+receiveruid

       supportActionBar?.title=name

        msgrecyler=findViewById(R.id.chatrecyler)
        msgbox=findViewById(R.id.messagebox)
        sendbutton=findViewById(R.id.sentbutton)
         msglist= ArrayList()
         adapter= msg_adapter(this,msglist)

        msgrecyler.layoutManager=LinearLayoutManager(this)
        msgrecyler.adapter=adapter

        mdbref.child("chats").child(senderroom!!).child("message")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    msglist.clear()
                    for(postSnapshot in snapshot.children){
                        val msg=postSnapshot.getValue(message::class.java)
                        msglist.add(msg!!)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }


            })

        sendbutton.setOnClickListener {

            val message=msgbox.text.toString()
            val messageobj=message(message,senderuid)

            mdbref.child("chats").child(senderroom!!).child("message").push().setValue(messageobj).addOnSuccessListener {
                mdbref.child("chats").child(receiverrroom!!).child("message").push().setValue(messageobj)
            }
            msgbox.setText("")
        }
    }
}