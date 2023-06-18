package com.example.mishasampletask

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mishasampletask.databinding.FragmentDescDetailBinding
import java.text.SimpleDateFormat
import java.util.*


class DescDetailFragment : Fragment() {

    private var title = ""
    private var desc = ""
    private var date = 0L

    private lateinit var binding: FragmentDescDetailBinding


    companion object {

        private const val REQUEST_SELECT_IMAGE = 1001
        private const val PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 2001

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments
        if (bundle != null) {
            title = bundle.getString("title")!!
            desc = bundle.getString("desc")!!
            date = bundle.getLong("date")!!
            // Use the data as needed
        }
        binding = FragmentDescDetailBinding.inflate(inflater, container, false)
        setUI()
        setImage()
        return binding!!.root
    }


    private fun setUI() {
        binding.recordTitleDesc.text = title
        binding.recordDescDesc.text = desc
        binding.recordDate.text = convertMillisToDate(date)
    }

    private fun convertMillisToDate(milliseconds: Long): String {
        val date = Date(milliseconds)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(date)

    }

    private fun setImage() {
        binding.userImage.setOnClickListener {
            if (checkPermissions()) {
                selectImageFromGallery()
            } else {
                requestPermissions()
            }

        }

    }

    private fun checkPermissions(): Boolean {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val result = ContextCompat.checkSelfPermission(requireContext(), permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(permission),
            PERMISSION_REQUEST_READ_EXTERNAL_STORAGE
        )
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_SELECT_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            selectedImageUri?.let {
                // Load the selected image into the ImageView
                binding.userImage.setImageURI(it)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImageFromGallery()
            }
        }
    }


}