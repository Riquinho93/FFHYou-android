package com.example.ffhyou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import androidx.appcompat.widget.SearchView

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity(), ItemsAdapter.ClickListener {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

   /* val imageName = arrayOf(
            UserModel("Henrique das Virgens", "Image2", "image2", "Brazil", "Uni", "Anapolis",R.drawable.braganca),
            UserModel("joao paulo", "Image2", "image2", "Portugal", "Uni", "Anapolis",R.drawable.joao),
            UserModel("Boreloi Indi", "Image2", "image2", "Guine Bissau", "Uni", "Anapolis",R.drawable.eloy),
            UserModel("Jorge Humberto", "Image2", "image2", "Portugal", "Uni", "Anapolis",R.drawable.jorge),
            UserModel("Cristiano Ronaldo", "Image2", "image2", "Portugal", "Uni", "Anapolis",R.drawable.image1),
            UserModel("Anitta", "Image2", "image2", "Brazil", "Uni", "Anapolis",R.drawable.image5),
            UserModel("Donald Trump", "Image2", "image2", "Estados Unidos", "Uni", "Anapolis",R.drawable.image3)

    )*/

    var userModelList :ArrayList<UserModel>?=null
    var itemsAdapter : ItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        userModelList = ArrayList<UserModel>()
        val result= getAllUsers()




        itemsAdapter = ItemsAdapter(this)
        if (result != null) {
            itemsAdapter!!.setData(result)
        }

       /* for(item in imageName){
            itemModelList.add(item)
        }*/

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.setHasFixedSize(true)
        recycleView.adapter = itemsAdapter


        //val btnLogout = findViewById<Button>(R.id.btnLogout)
        //startActivity(Intent(this@HomeActivity, MainActivity::class.java))
        //finish()
        /*btnLogout.setOnClickListener {
            auth.signOut()

        }*/

    }

    override fun ClickedItem(itemsModel: UserModel) {
        Log.e("TAG", itemsModel.name)
    }

    fun getAllUsers(): ArrayList<UserModel>? {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val fUser = auth.currentUser
        databaseReference = database?.reference!!.child("user")
        val postListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userModelList?.clear()
                for (e in snapshot.children){
                    val post = e.getValue(UserModel::class.java)
                    userModelList?.add(post!!)

                    //itemsAdapter = ItemsAdapter()
                    //itemsAdapter!!.setData(userModelList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        databaseReference!!.addValueEventListener(postListener)
        return userModelList
        //val userReference = databaseReference?.child(fuser?.uid!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val menuItem = menu!!.findItem(R.id.searchView)
        val searchView = menuItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
        return true
    }
}