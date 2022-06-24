package com.example.chatmate

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserAdapter(val context:Context, val userlist:ArrayList<user>): RecyclerView.Adapter<UserAdapter.viewholder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
       val view:View=LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val cuurentuser=userlist[position]

        holder.txtname.text= cuurentuser.name

        holder.itemView.setOnClickListener {
            val intent= Intent(context,chatActivity::class.java)
            intent.putExtra("name",cuurentuser.name)
            intent.putExtra("uid",cuurentuser.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    class viewholder(itemView: View):RecyclerView.ViewHolder(itemView){
          val txtname=itemView.findViewById<TextView>(R.id.txt_name)

    }
}