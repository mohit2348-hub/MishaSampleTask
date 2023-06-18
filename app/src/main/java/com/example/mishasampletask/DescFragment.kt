package com.example.mishasampletask

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mishasampletask.adapter.DescAdapter
import com.example.mishasampletask.databinding.FragmentDescBinding
import com.example.mishasampletask.model.UserData
import com.google.firebase.database.*


class DescFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentDescBinding
    private var dataList: List<UserData>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDescBinding.inflate(inflater, container, false)
        setUi()
        return binding!!.root
    }


    private fun setUi() {
        getListFromFireBase()
        slideToRemove()
        setProgress()
    }

    private fun setProgress() {
        binding.progressId.bringToFront()
        binding.progressId.visibility = View.VISIBLE


    }

    private fun getListFromFireBase() {
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("UserData")
        dataList = mutableListOf()
        val adapter = DescAdapter(dataList!!, requireContext())
        adapter.listener = this
        binding.rvDesc.adapter = adapter
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                (dataList as MutableList<UserData>).clear()
                for (snapshot in dataSnapshot.children) {
                    val dataObject = snapshot.getValue(UserData::class.java)
                    if (dataObject != null || snapshot.child("checked").value is Boolean) {
                        (dataList as MutableList<UserData>).add(dataObject!!)
                    }

                }
                binding.progressId.visibility = View.GONE
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database errors
            }
        })

    }

    private fun slideToRemove() {
        val swipeToDeleteCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // Handle drag events if needed
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedItem = dataList?.get(position)
                val databaseReference: DatabaseReference =
                    FirebaseDatabase.getInstance().getReference("UserData")

                // Remove the item from the database
                databaseReference.child(deletedItem?.title!!).removeValue().addOnCompleteListener {
                    Toast.makeText(requireContext(), "REMOVED", Toast.LENGTH_LONG).show()

                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "ERROR IN REMOVING", Toast.LENGTH_LONG).show()
                }

                // Update the UI by removing the item from the RecyclerView
                (dataList as MutableList<UserData>).removeAt(position)
                binding.rvDesc.adapter?.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvDesc)
    }

    override fun onItemClick(position: Int, title: String, desc: String, date: Long) {
        Log.d("TAG", "onItemClick:" + title)
        Log.d("TAG", "onItemClick:" + desc)
        Log.d("TAG", "onItemClick:" + date)
        initFragment(title, desc, date)

    }


    private fun initFragment(title: String, desc: String, date: Long) {

        val descFragment = DescDetailFragment()

        //send data
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putString("desc", desc)
        bundle.putLong("date", date)
        descFragment.arguments = bundle

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, descFragment)
        transaction.addToBackStack(null) // Add to back stack if you want to navigate back
        transaction.commit()
    }


}