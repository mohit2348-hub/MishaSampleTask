package com.example.mishasampletask

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mishasampletask.databinding.FragmentInfoBinding
import com.example.mishasampletask.model.UserData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        setData()
        navigation()
        return binding!!.root

    }

    private fun setData() {
        binding?.submitBtn?.setOnClickListener {
            val title = binding?.titleEditText?.text.toString()
            val desc = binding?.descEditText?.text.toString()
            database = FirebaseDatabase.getInstance().getReference("UserData")
            try {
                val userData = UserData(title, desc, "",true)
                database.child(title).setValue(userData).addOnCompleteListener {
                    binding.titleEditText.text?.clear()
                    binding.descEditText.text?.clear()
                    Toast.makeText(requireContext(), "DATA SUCCESSFULLY UPDATED", Toast.LENGTH_LONG)
                        .show()
                }.addOnFailureListener {

                    Toast.makeText(requireContext(), "FAILED TO ADD", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.d("FAILED", "getData:" + e.message)
            }

        }
    }

    private fun navigation() {
        binding.listBtn.setOnClickListener {
            initFragment()
        }
    }

    private fun initFragment() {
        val descFragment = DescFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, descFragment)
        transaction.addToBackStack(null) // Add to back stack if you want to navigate back
        transaction.commit()
    }
}