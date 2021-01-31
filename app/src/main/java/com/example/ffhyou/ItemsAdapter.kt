package com.example.ffhyou

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.raw_item.view.*
import com.example.ffhyou.ItemsAdapter.ItemsAdapterVH

class ItemsAdapter
    (var clickListener: ClickListener)
    : RecyclerView.Adapter<ItemsAdapterVH>() {

    //lateinit var auth:  FirebaseAuth.getInstance()

    var userModelList = ArrayList<UserModel>();
    fun setData(userModelList: ArrayList<UserModel>){
        this.userModelList = userModelList;

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapterVH {

        return ItemsAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.raw_item, parent, false));
    }

    override fun onBindViewHolder(holder: ItemsAdapterVH, position: Int) {
        val usersModel = userModelList[position]
        holder.name.text = usersModel.name
        holder.country.text = usersModel.country

        //holder.image.setImageDrawable(R.drawable.ic_launcher_background)

        holder.itemView.setOnClickListener{
            clickListener.ClickedItem(usersModel)
        }
    }

    override fun getItemCount(): Int {
        return userModelList.size
    }

    class ItemsAdapterVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.tvName
        val country = itemView.tvCountry
        //val image = itemView.imageView

    }

    interface ClickListener{
        fun ClickedItem(itemsModel: UserModel)
    }

}

