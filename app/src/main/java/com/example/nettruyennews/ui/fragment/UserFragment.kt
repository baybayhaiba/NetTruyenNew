package com.example.nettruyennews.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.custombottomsheet.BottomSheet.OnImageSelectedListener
import com.example.custombottomsheet.BottomSheetImage
import com.example.nettruyennews.BookApp
import com.example.nettruyennews.R
import com.example.nettruyennews.databinding.FragmentUserBinding
import com.example.nettruyennews.extension.instanceStorage
import com.example.nettruyennews.extension.show
import com.google.firebase.ktx.Firebase

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private val animation: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.anim_test)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val ref = Firebase.instanceStorage().reference
        val imageRef = ref.child("${BookApp.getInstance().uuid}.png")
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false)
        binding.imgUser.startAnimation(animation)
        binding.imgUser.setOnClickListener {
            BottomSheetImage.with(activity)
                .setPeekHeight(800)
                .setTitleDone("Finish")
                .onClickOnceImage(object : OnImageSelectedListener {
                    override fun onImageSelected(uri: Uri) {
                        imageRef.putFile(uri)
                            .addOnSuccessListener { show("Update finish !") }
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val downloadUri = task.result
                                    Glide.with(requireActivity())
                                        .load(downloadUri.uploadSessionUri)
                                        .into(binding.imgUser)
                                }
                            }
                    }
                })
        }
        return binding.root
    }
}