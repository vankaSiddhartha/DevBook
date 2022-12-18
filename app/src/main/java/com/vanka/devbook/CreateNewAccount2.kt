package com.vanka.devbook

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.vanka.devbook.Model.UseerModel

class CreateNewAccount2 : AppCompatActivity() {


    private fun UploadData(img: Uri?, name: EditText?, role: EditText?, experence: EditText?, id: String) {
      var data = UseerModel(name!!.text.toString(),id.toString(),role!!.text.toString(),experence!!.text.toString(),img.toString())
        FirebaseDatabase.getInstance().getReference("Users").child(id).setValue(data).addOnSuccessListener {
            startActivity(Intent(this,MainScreen::class.java))
        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var k:Uri?=null
        setContentView(R.layout.activity_create_new_account2)
        var name = findViewById<EditText>(R.id.name)
        var role = findViewById<EditText>(R.id.role)
        var experence = findViewById<EditText>(R.id.ex)
        var id = FirebaseAuth.getInstance().currentUser!!.uid
        val contract = registerForActivityResult(ActivityResultContracts.GetContent()){
            findViewById<ImageView>(R.id.profile).setImageURI(it)

            var put = FirebaseStorage.getInstance().reference.child("Files").child(FirebaseAuth.getInstance().currentUser!!.uid)
            put.putFile(it!!).addOnSuccessListener {
                Toast.makeText(this, "OM", Toast.LENGTH_SHORT).show()
                put.downloadUrl.addOnSuccessListener {img->
                 k = img
                }
            }
        }
        findViewById<ImageView>(R.id.profile).setOnClickListener {


            contract.launch("image/*")

        }
        findViewById<Button>(R.id.btn_signup).setOnClickListener {
            UploadData(k, name, role, experence, id)
        }
    }
}