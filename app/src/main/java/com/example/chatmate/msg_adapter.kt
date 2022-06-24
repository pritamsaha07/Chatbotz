package com.example.chatmate

import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class msg_adapter(val context:Context, val msglist: ArrayList<message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

   val ITEM_RECEIVED=1
    val ITEM_SENT=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1){
            val view:View= LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return ReceivedViewHolder(view)
        }
        else{
            val view:View= LayoutInflater.from(context).inflate(R.layout.send,parent,false)
            return sentViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentmsg= msglist[position]

        if(holder.javaClass == sentViewHolder::class.java){


              val viewHolder=holder as sentViewHolder
            holder.sentMessage.text = currentmsg.message
        }
        else{
            val viewHolder=holder as ReceivedViewHolder
            holder.receivedMessage.text = currentmsg.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentmsg=msglist[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentmsg.sender)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVED
        }
    }

    override fun getItemCount(): Int {
        return msglist.size
    }




    class sentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
             val sentMessage=itemView.findViewById<TextView>(R.id.txtsend_msg)
    }


    class ReceivedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val receivedMessage=itemView.findViewById<TextView>(R.id.txtrecived_msg)
    }
}