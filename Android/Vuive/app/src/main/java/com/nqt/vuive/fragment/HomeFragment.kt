package com.nqt.vuive.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.graphics.Bitmap
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.nqt.vuive.R
import com.nqt.vuive.activity.MainActivity
import com.nqt.vuive.activity.OutData
import com.nqt.vuive.activity.OutDataPhotography
import com.nqt.vuive.activity.RvAdapterPhotography
import com.nqt.vuive.activity.RvAdapterPlantsTypes

import com.nqt.vuive.databinding.FragmentHomeBinding
import com.nqt.vuive.databinding.FragmentProfileBinding
import com.nqt.vuive.viewmodel.AuthViewModel

class HomeFragment : Fragment() {
    private val GALLERY_REQUEST_CODE = 2
    private val CAMERA_REQUEST_CODE = 1
    private lateinit var binding : FragmentHomeBinding

    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val ds= mutableListOf<OutData>()

        ds.add(OutData(R.drawable.plant_typle1,"Home Plants","68 types of plants"))
        ds.add(OutData(R.drawable.plant_typle1,"Home Plants","68 types of plants"))
        ds.add(OutData(R.drawable.plant_typle1,"Home Plants","68 types of plants"))
        ds.add(OutData(R.drawable.plant_typle1,"Home Plants","68 types of plants"))

        val recyclerView: RecyclerView =view.findViewById(R.id.rev_plant_type)

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        val itemAdapter = RvAdapterPlantsTypes(ds)
        recyclerView.adapter = itemAdapter


        val dsphotofraphy= mutableListOf<OutDataPhotography>()
        dsphotofraphy.add(OutDataPhotography(R.drawable.photography1,"Mini"))
        dsphotofraphy.add(OutDataPhotography(R.drawable.photography1,"Mini"))
        dsphotofraphy.add(OutDataPhotography(R.drawable.photography1,"Mini"))
        dsphotofraphy.add(OutDataPhotography(R.drawable.photography1,"Mini"))
        dsphotofraphy.add(OutDataPhotography(R.drawable.photography1,"Mini"))

        val recyclerView2: RecyclerView = view.findViewById(R.id.rev_photography)
        recyclerView2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        val itemAdapter2 = RvAdapterPhotography(dsphotofraphy)
        recyclerView2.adapter = itemAdapter2

        binding = FragmentHomeBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        //  val layoutIdentify : LinearLayout =view.findViewById(R.id.layout_identify)
        binding. layoutIdentify .setOnClickListener {

            cameraCheckPermission()
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData()
        viewModel.loadStatus.observe(viewLifecycleOwner, Observer { data ->
            binding.tvName.text = data?.name + ","
            context?.let { Glide.with(it).load(data?.avatar).error(R.drawable.avatar_default).into(binding.imgAvatarHome) };

        })
    }
    private fun cameraCheckPermission() {

        Dexter.withContext(requireActivity())
            .withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA).withListener(

                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {

                            if (report.areAllPermissionsGranted()) {
                                camera()
                            }

                        }
                    }
                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?) {
                        showRotationalDialogForPermission()
                    }

                }
            ).onSameThread().check()
    }
    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        contract.launch(intent)
    }



    @SuppressLint("ResourceType")
    private val contract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val resultCode = result.resultCode
        val data = result.data
        if (resultCode == Activity.RESULT_OK && data != null) {
            // Xử lý kết quả thành công và dữ liệu trả về từ Camera

            val imageBitmap: Bitmap? = data.extras?.get("data") as Bitmap?

//            // Tạo Bundle và truyền dữ liệu
            val bundle = Bundle()
            bundle.putParcelable("imageBitmap", imageBitmap)
//
//            // Chuyển đổi sang màn hình khác với Bundle dữ liệu
//            val intent = Intent(requireContext(), fragment1::class.java)
//            intent.putExtras(bundle)
//            startActivity(intent)
            //  Toast.makeText(requireContext(), "hello"+imageBitmap, Toast.LENGTH_SHORT).show()

            if (imageBitmap != null) {
                mainActivity.switchToFragmentTwo(imageBitmap)
            }
        } else {

        }
    }
    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(requireActivity())
            .setMessage("It looks like you have turned off permissions"
                    + "required for this feature. It can be enable under App settings!!!")

            .setPositiveButton("Go TO SETTINGS") { _, _ ->

                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireContext().packageName, null)
                    intent.data = uri
                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }

            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}