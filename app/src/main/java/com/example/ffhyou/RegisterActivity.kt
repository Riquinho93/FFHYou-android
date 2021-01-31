package com.example.ffhyou

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("user")

        val textViewLogin = findViewById<TextView>(R.id.textViewLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val edName = findViewById<TextView>(R.id.edName)
        val edEmail = findViewById<TextView>(R.id.edEmail)
        val edNeighborhood = findViewById<TextView>(R.id.edNeighborhood)
        val edPassword = findViewById<TextView>(R.id.edPassword)
        val edConfirmPassword = findViewById<TextView>(R.id.edConfirmPassword)
//        val edCountry:TextView
//        val edInstitution:TextView

        var optionCountry: Spinner
        var optionInstitution: Spinner
        //var result: TextView

        optionCountry = findViewById(R.id.tvCountry) as Spinner
        optionInstitution = findViewById(R.id.tvInstitution) as Spinner
//        var resultCountry = findViewById(R.id.tvCountry) as TextView //
//        var resultInstitution = findViewById(R.id.tvInstitution) as TextView

        val optionsCountry = arrayOf("Brazil", "England", "Portugal")
        val optionsInstitutions = arrayOf("Havard", "IPB", "UniEvangelica")

        optionCountry.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsCountry)
        optionInstitution.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsInstitutions)

        /*optionCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
               resultCountry.text = optionsCountry.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                resultCountry.text = "Please select an Country"
            }
        }

        optionInstitution.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                resultInstitution.text = optionsInstitutions.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                resultInstitution.text = "Please select an Country"
            }
        }*/



        textViewLogin.setOnClickListener{
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

         btnRegister.setOnClickListener{
             val textCountry: String = optionCountry.getSelectedItem().toString()
             val textInstitution: String = optionInstitution.getSelectedItem().toString()
             if(edName.text.isNotEmpty() || edEmail.text.isNotEmpty() || edPassword.text.isNotEmpty() || edConfirmPassword.text.isNotEmpty()){
                 //Toast.makeText(this, "Proved", Toast.LENGTH_LONG).show()
                Log.d("Country", textCountry)
                 Log.d("MYInstitution", textInstitution)
                 Log.d("Email", edEmail.text.toString())
                 Log.d("name", edName.text.toString())
                 Log.d("Password", edPassword.text.toString())
                 Log.d("Confir Password", edConfirmPassword.text.toString())
                 auth.createUserWithEmailAndPassword(edEmail.text.toString(), edPassword.text.toString()).addOnCompleteListener{
                     Log.d("IT", it.toString())
                     if(it.isSuccessful){
                         val currentUser = auth.currentUser
                         val currentUserDb = databaseReference?.child(((currentUser?.uid!!)))
                         currentUserDb?.child("Name")?.setValue(edName.text.toString())
                         currentUserDb?.child("Country")?.setValue(textCountry)
                         currentUserDb?.child("Institution")?.setValue(textInstitution)
                         currentUserDb?.child("Neighborhood")?.setValue(edNeighborhood.text.toString())
                         Toast.makeText(this@RegisterActivity, "Registration Success!", Toast.LENGTH_LONG).show()

                     }else{
                         Toast.makeText(this@RegisterActivity, "Registration fail, try again!", Toast.LENGTH_LONG).show()
                     }
                 }

             }else{
                 Toast.makeText(this, "input required", Toast.LENGTH_LONG).show()
             }
        }

    }
}