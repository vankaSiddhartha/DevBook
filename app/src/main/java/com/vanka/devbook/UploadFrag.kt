package com.vanka.devbook

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.vanka.devbook.Model.UseerModel
import com.vanka.devbook.Model.VideoModel
import java.util.*


class UploadFrag : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_upload, container, false)
        var getvid:Uri?=null
        var getImg:Uri?=null
        var name:String?=null
        var profile:String?=null
        var video_view = view.findViewById<VideoView>(R.id.videoView)
        var getVideo = registerForActivityResult(ActivityResultContracts.GetContent()){
            var put = FirebaseStorage.getInstance().reference.child("Videos").child(FirebaseAuth.getInstance().currentUser!!.uid).child(UUID.randomUUID().toString())
            put.putFile(it!!).addOnSuccessListener {

                put.downloadUrl.addOnSuccessListener {vid->
                    getvid = vid
                }
            }
        }
         video_view.setOnClickListener {
             getVideo.launch("video/*")
         }
        var getImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            var put = FirebaseStorage.getInstance().reference.child("TumbNails").child(FirebaseAuth.getInstance().currentUser!!.uid).child(UUID.randomUUID().toString())
            put.putFile(it!!).addOnSuccessListener {

                put.downloadUrl.addOnSuccessListener {vid->
                    getImg = vid
                }
            }
        }
        view.findViewById<Button>(R.id.tumbnail).setOnClickListener {
            getImage.launch("image/*")
        }
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var data = snapshot.getValue(UseerModel::class.java)
                    name = data!!.userName
                    profile = data!!.imgLink
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        var titile = view.findViewById<EditText>(R.id.title_up)

        var up_video = view.findViewById<Button>(R.id.up_video).setOnClickListener {
                UploadFrag(titile,getvid,getImg,name,profile)

        }
        return view
    }


    private fun UploadFrag(titile: EditText?, getvid: Uri?, img: Uri?, name: String?, profile: String?) {
         var data = VideoModel(name,titile!!.text.toString(),img.toString(),profile,getvid.toString())
        FirebaseDatabase.getInstance().getReference("VideosList").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .push().setValue(data)
    }


}