package com.example.ffhyou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        //auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null){
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        val textViewRegister = findViewById<TextView>(R.id.textViewRegister)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val edEmail = findViewById<TextView>(R.id.edEmail)
        val edPassword = findViewById<TextView>(R.id.edPassword)

        textViewRegister.setOnClickListener{
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()

        }

        btnLogin.setOnClickListener {

            if(edEmail.text.isNotEmpty() || edPassword.text.isNotEmpty()){

                auth.signInWithEmailAndPassword(edEmail.text.toString(), edPassword.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@MainActivity,"Registration fail, try again!", Toast.LENGTH_LONG).show()
                    }
                }

            }

        }

    }
}