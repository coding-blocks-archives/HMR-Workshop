package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val key = intent.getStringExtra("KEY")

        button2.setOnClickListener {
            var msg = editText4.text.toString()
            var email = FirebaseAuth.getInstance().currentUser?.email
            FirebaseDatabase.getInstance().reference.child(key)
                .push()
                .setValue(
                    Message(
                        email.toString(),
                        msg

                    )
                )
        }
        val query = FirebaseDatabase.getInstance().reference
            .child(key)

        val options = FirebaseListOptions.Builder<Message>()
            .setLayout(android.R.layout.simple_expandable_list_item_1)
            .setQuery(query,Message::class.java)
            .build()

        val adapter = object : FirebaseListAdapter<Message>(options){
            override fun populateView(v: View, model: Message, position: Int) {
                (v as TextView).text = model.email + "\n"+model.msg.toString()
            }

        }

        adapter.startListening()

        message.adapter = adapter




    }
}
