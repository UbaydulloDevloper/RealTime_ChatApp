package com.developer.realtime_chatapp

import adapters.MassageAdapter
import adapters.totleAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.developer.realtime_chatapp.databinding.FragmentMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import models.MassageUsers
import models.Users
import java.text.SimpleDateFormat
import java.util.*

class Message : Fragment() {
    lateinit var binding: FragmentMessageBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var massageAdapter: MassageAdapter
    lateinit var totleAdapter: totleAdapter

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("massage")
        val user = arguments?.getSerializable("user") as Users


        binding.send.setOnClickListener {
            val m = binding.massageText.text.toString()
            val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
            val date = simpleDateFormat.format(Date())
            val massage = MassageUsers(firebaseAuth.currentUser?.uid, user.idToken, date, m)

            val key = reference.push().key
            reference.child("${firebaseAuth.currentUser?.uid}/messages/${user.idToken!!}/$key")
                .setValue(massage)

            reference.child("${user.idToken}/messages/${firebaseAuth.currentUser?.uid}/$key")
                .setValue(massage)


            binding.massageText.text = null

        }
        reference.child("${firebaseAuth.currentUser?.uid}/messages/${user.idToken}")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = arrayListOf<MassageUsers>()
                    val children = snapshot.children
                    for (child in children) {
                        val value = child.getValue(MassageUsers::class.java)
                        if (value != null) {
                            list.add(value)
                        }
                    }
                    totleAdapter = totleAdapter(list,firebaseAuth.currentUser?.uid!!)
                    binding.massageRv.adapter = totleAdapter
                    /*massageAdapter = MassageAdapter(list, firebaseAuth.currentUser?.uid!!)
                    binding.massageRv.adapter = massageAdapter*/
                    binding.massageRv.scrollToPosition(list.size - 1);

                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

        return binding.root
    }
}