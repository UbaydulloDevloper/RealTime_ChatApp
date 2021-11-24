package adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.realtime_chatapp.databinding.ItemFromBinding
import com.developer.realtime_chatapp.databinding.ItemToBinding
import models.MassageUsers

class MassageAdapter(val list: List<MassageUsers>, var id: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class FromVh(var itemRv: ItemFromBinding) : RecyclerView.ViewHolder(itemRv.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(user: MassageUsers) {
            itemRv.messageTv.text = user.massage
            //itemRv.dateTv.text = user.date
        }
    }

    inner class ToVh(var itemRv: ItemToBinding) : RecyclerView.ViewHolder(itemRv.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(user: MassageUsers) {
            itemRv.messageTv.text = user.massage
            // itemRv.dateTv.text = user.date

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return FromVh(
                ItemFromBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return ToVh(
                ItemToBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 1) {
            val fromVh = holder as FromVh
            fromVh.onBind(list[position])
        } else {
            val toVh = holder as ToVh
            toVh.onBind(list[position])
        }
    }

    override fun getItemCount(): Int = list.size
    override fun getItemViewType(position: Int): Int {
        if (list[position].myId == id) {
            return 1
        } else {
            return 2
        }
    }


}