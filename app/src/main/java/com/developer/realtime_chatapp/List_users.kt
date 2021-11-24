package com.developer.realtime_chatapp

import adapters.MyAdapters
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.developer.realtime_chatapp.databinding.FragmentListUsersBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import models.Users

class List_users : Fragment() {
    lateinit var binding: FragmentListUsersBinding
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var auth: FirebaseAuth
    lateinit var reference: DatabaseReference
    lateinit var arrayList: ArrayList<Users>
    lateinit var myAdapters: MyAdapters
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListUsersBinding.inflate(layoutInflater)

        firebaseDatabase = FirebaseDatabase.getInstance()

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        reference = firebaseDatabase.getReference("Users")

        val email = currentUser?.email
        val name = currentUser?.displayName
        val image = currentUser?.photoUrl.toString()
        val idtoken = currentUser?.uid

        val users = Users(idtoken, name, image, email)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList = ArrayList()

                val children = snapshot.children
                val filterList = arrayListOf<Users>()
                for (child in children) {
                    val value = child.getValue(Users::class.java)
                    if (value != null && idtoken != value.idToken) {
                        arrayList.add(value)
                    }
                    if (value != null && value.idToken == idtoken) {
                        filterList.add(value)
                    }
                }

                if (filterList.isEmpty()) {
                    reference.child(idtoken!!).setValue(users)
                }

                myAdapters = MyAdapters(arrayList, object : MyAdapters.Click {
                    override fun itemClick(user: Users) {
                        findNavController().navigate(R.id.message2, bundleOf("user" to user))
                    }
                })
                binding.rv.adapter = myAdapters
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return binding.root
    }
}