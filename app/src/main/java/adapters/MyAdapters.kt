package adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.realtime_chatapp.databinding.ItemRvBinding
import com.squareup.picasso.Picasso
import models.Users

class MyAdapters(val list: List<Users>, val click: Click) :
    RecyclerView.Adapter<MyAdapters.Vh>() {


    inner class Vh(var itemRv: ItemRvBinding) : RecyclerView.ViewHolder(itemRv.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(user: Users) {

            Picasso.get().load(user.imageUrl).into(itemRv.circleImage)
            itemRv.email.text = user.email
            itemRv.name.text = user.name
            itemRv.root.setOnClickListener {
                click.itemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    interface Click {
        fun itemClick(user: Users)
    }

    override fun getItemCount(): Int = list.size
}
