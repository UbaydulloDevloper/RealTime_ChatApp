package adapters

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.realtime_chatapp.databinding.ItemTotleBinding
import models.MassageUsers

class totleAdapter(val list: List<MassageUsers>, var uid: String) :
    RecyclerView.Adapter<totleAdapter.Vh>() {


    inner class Vh(var itemRv: ItemTotleBinding) : RecyclerView.ViewHolder(itemRv.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(user: MassageUsers, position: Int) {
            if (uid == user.myId) {
                itemRv.rootGravity.gravity = Gravity.END
                itemRv.textMessage.text = user.massage
            } else {
                itemRv.rootGravity.gravity = Gravity.START
                itemRv.textMessage.text = user.massage
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemTotleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size


}