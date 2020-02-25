package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            var email = editText.text.toString()
            var pass = editText2.text.toString()
            var value = editText3.text.toString()
            var key = if(value.isNullOrEmpty()){
                "messages"
            }else{
                value
            }

            val i = Intent(this,ChatActivity::class.java)
            i.putExtra("KEY",key)

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnFailureListener {
                if(it.localizedMessage.contains("another")){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).addOnSuccessListener {
                        startActivity(i)
                    }.addOnFailureListener {
                        Toast.makeText(this,it.localizedMessage,Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_SHORT).show()
                }
            }.addOnSuccessListener {
                startActivity(i)
            }

        }
    }
}
