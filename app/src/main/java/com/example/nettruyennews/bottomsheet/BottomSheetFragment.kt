package com.example.custombottomsheet.BottomSheet

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfile.adapter.Photo
import com.example.myfile.adapter.PhotoAdapter
import com.example.myfile.adapter.PhotoType
import com.example.nettruyennews.R
import com.example.nettruyennews.extension.getImageUri
import com.example.nettruyennews.extension.toUriExtension
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


open class BottomSheetFragment : BottomSheetDialogFragment() {

    private val adapter: PhotoAdapter by lazy {
        PhotoAdapter(context) { onClick(it) }
    }

    lateinit var builder: BaseBuilder
    lateinit var recyclerView: RecyclerView
    lateinit var textViewSelected: TextView
    lateinit var btnDone: Button
    lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private val mBottomSheetBehaviorCallback: BottomSheetCallback = object : BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismissAllowingStateLoss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    private fun onClick(photo: Photo) {

        if (builder.imageListener is OnImageSelectedListener && builder.images.isNotEmpty()) {
            val photoFirst = builder.images.removeFirst()
            photoFirst.isSelected = false
            adapter.notifyItemChanged(photoFirst.index)
        }

        when (photo.type) {
            PhotoType.NORMAL -> {
                photo.isSelected = if (builder.images.contains(photo)) {
                    builder.images.remove(photo)
                    dismiss()
                    false
                } else {
                    builder.images.add(photo)
                    true
                }

                this.updateImageSelected()

            }

            PhotoType.CAMERA -> {
                chooseImageByIntent(PhotoType.CAMERA)
            }
            PhotoType.GALLARY -> {
                chooseImageByIntent(PhotoType.GALLARY)
            }
        }

        adapter.notifyItemChanged(photo.index)
    }

    private fun updateImageSelected() {
        textViewSelected.text = " ${builder.images.size} is selected"
    }

    private fun chooseImageByIntent(type: PhotoType) {

        val intent = if (type == PhotoType.CAMERA) {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        } else {
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        }
        resultLauncher.launch(intent)
    }


    private fun done() {
        if (builder.images.isEmpty()) return

        when (val listener = builder.imageListener) {
            is OnImageSelectedListener -> {
                listener.onImageSelected(builder.images.first().image.toUriExtension())
            }
            is OnMultiImageSelectedListener -> {
                listener.onImagesSelected(builder.images.map { it.image.toUriExtension() }.toList())
            }
        }

        dismiss()
    }


    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = View.inflate(context, R.layout.bottom_sheet, null)
        dialog.setContentView(view)
        initView(view)
        initRecyclerView()

        val layoutParams =
            (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams

        val behavior = layoutParams.behavior

        if (behavior is BottomSheetBehavior) {
            behavior.addBottomSheetCallback(mBottomSheetBehaviorCallback)
            behavior.peekHeight = builder.peekHeight
        }



        btnDone.apply {
            setOnClickListener { done() }
            text = builder.textDone
        }

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val data = result.data as? Bitmap
                    val uri = getImageUri(activity?.applicationContext, data, null, 1)
                    builder.images.add(Photo(uri.toString()))

                    this.updateImageSelected()
                }
            }


    }

    private fun initView(view: View) {
        recyclerView = view.findViewById(R.id.rcvPhoto)
        textViewSelected = view.findViewById(R.id.txt_selected)
        btnDone = view.findViewById(R.id.btnDone)
    }

    private fun initRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(activity, 3)
        recyclerView.adapter = adapter
    }
}