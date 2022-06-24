package com.example.chatmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var userrecylerview: RecyclerView
    private lateinit var userlist:ArrayList<user>
    private lateinit var adapter: UserAdapter
    private lateinit var mauth: FirebaseAuth
    private lateinit var mDbref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mauth= FirebaseAuth.getInstance()
        mDbref= FirebaseDatabase.getInstance().getReference()

        userlist= ArrayList()
        adapter=UserAdapter(this,userlist)


        userrecylerview=findViewById(R.id.userrecyler)
        userrecylerview.layoutManager= LinearLayoutManager(this)

        userrecylerview.adapter=adapter


        mDbref.child("user").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for(postSnapshot in snapshot.children){
                    val currenuser=postSnapshot.getValue(user::class.java)

                    if(mauth.currentUser?.uid!=currenuser?.uid) {
                        userlist.add(currenuser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {

            }


        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.Logout){

         mauth.signOut()
            val intent = Intent(this@MainActivity,Login::class.java)
            finish()
            startActivity(intent)

            return true;

        }
        return true
    }
}