package com.birendra.lensdayshop.ui.notifications

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.birendra.lensdayshop.BottomActivity
import com.birendra.lensdayshop.R
import com.birendra.lensdayshop.api.RetrofitService
import com.birendra.lensdayshop.db.LensdaysDB
import com.birendra.lensdayshop.repository.UserRepository
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class NotificationsFragment : Fragment(),View.OnClickListener,PopupMenu.OnMenuItemClickListener {
    private lateinit var cvProfile:CircleImageView
    private lateinit var btnChange:Button
    private lateinit var tvName:TextView
    private lateinit var tvUsername:TextView
    private lateinit var tvEmail:TextView
    private lateinit var tvContact:TextView
    private lateinit var etFirstName:EditText
    private lateinit var etLastName:EditText
    private lateinit var etEmail:EditText
    private lateinit var etAddress:EditText
    private lateinit var etUsername:EditText
    private lateinit var btnEdit:Button
    var CAMERA_REQUEST_CODE = 1
    var GALLERY_REQUEST_CODE = 0
    var image_url:String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        binding(root)
        initialize()
        btnEdit.setOnClickListener(this)
        btnChange.setOnClickListener(this)
        cvProfile.setOnClickListener(this)
        return root
    }
    private fun initialize()
    {

        CoroutineScope(Dispatchers.IO).launch {
            var instance = LensdaysDB.getInstance(requireContext()).getUserDAO()
            var user = instance.retrieveUser()
            withContext(Dispatchers.Main)
            {
                if(RetrofitService.Online == true && user.profileImg!= "null" && user.profileImg!="no-img.jpg")
                {
                    var imgPath = RetrofitService.loadImgPath()+user.profileImg!!.replace("\\","/")
                    Glide.with(requireContext()).load(imgPath).into(cvProfile)
                }
                else
                {
                    cvProfile.setImageResource(R.drawable.lensdays)
                }
                tvName.text = user.fname+" "+user.lname
                tvUsername.text = user.username
                tvEmail.text = user.email
                tvContact.text = user.phone_number
                etAddress.setText(user.address)
                etEmail.setText(user.email)
                etFirstName.setText(user.fname)
                etLastName.setText(user.lname)
                etEmail.setText(user.email)
                etUsername.setText(user.username)
            }
        }
    }

    private fun binding(v:View)
    {
        cvProfile = v.findViewById(R.id.cvProfile)
        btnChange = v.findViewById(R.id.btnChange)
        tvName = v.findViewById(R.id.tvName)
        tvUsername = v.findViewById(R.id.tvUsername)
        tvEmail = v.findViewById(R.id.tvEmail)
        tvContact = v.findViewById(R.id.tvContact)
        etFirstName = v.findViewById(R.id.etFirstName)
        etLastName = v.findViewById(R.id.etLastName)
        etEmail = v.findViewById(R.id.etEmail)
        etAddress = v.findViewById(R.id.etAddress)
        etUsername = v.findViewById(R.id.etUsername)
        btnEdit = v.findViewById(R.id.btnEdit)
    }
    private fun validation():Boolean
    {
        if(TextUtils.isEmpty(etFirstName.text)){
            etFirstName.error = "Insert Firstname"
            etFirstName.requestFocus()
            return false
        }
        else if(TextUtils.isEmpty(etLastName.text))
        {
            etLastName.error = "Insert Lastname"
            etLastName.requestFocus()
            return false
        }
        else if(TextUtils.isEmpty(etEmail.text))
        {
            etEmail.error = "Insert Lastname"
            etEmail.requestFocus()
            return false
        }
        else if(TextUtils.isEmpty(etAddress.text))
        {
            etAddress.error = "Insert Address"
            etAddress.requestFocus()
            return false
        }
        else if(TextUtils.isEmpty(etUsername.text))
        {
            etUsername.error = "Insert Lastname"
            etUsername.requestFocus()
            return false
        }
        else
        {
            return true
        }

    }
    private fun editUserDetails()
    {
        if(validation())
        {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repo = UserRepository()
                    val response = repo.userEdit(etFirstName.text.toString(),etLastName.text.toString(),etEmail.text.toString(),etUsername.text.toString(),etAddress.text.toString())
                    if(response.success == true)
                    {
                        var instance = LensdaysDB.getInstance(requireContext()).getUserDAO()
                        instance.deleteUser()
                        instance.insertUser(response.data!!)
                        withContext(Dispatchers.Main)
                        {
                            var intent = Intent(requireContext(),BottomActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    else
                    {
                        withContext(Dispatchers.Main)
                        {
                            val snk = Snackbar.make(tvContact,"${response.message}", Snackbar.LENGTH_LONG)
                            snk.setAction("OK",View.OnClickListener {
                                snk.dismiss()
                            })
                            snk.show()
                        }
                    }
                }
                catch (ex:Exception)
                {
                    println(ex.printStackTrace())
                    withContext(Dispatchers.Main)
                    {
                        val snk = Snackbar.make(tvContact,"${ex.toString()}", Snackbar.LENGTH_LONG)
                        snk.setAction("OK",View.OnClickListener {
                            snk.dismiss()
                        })
                        snk.show()
                    }
                }
            }

        }
    }

    private fun showPopUp()
    {
        var popUp = PopupMenu(requireContext(),btnEdit)
        popUp.menuInflater.inflate(R.menu.menu_img,popUp.menu)
        popUp.setOnMenuItemClickListener(this)
        popUp.show()

    }

    private fun uploadImage()
    {
        if(image_url != "")
        {
            val file = File(image_url)
            val extension = MimeTypeMap.getFileExtensionFromUrl(image_url)
            val mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            val req_file = RequestBody.create(MediaType.parse(mimetype),file)
            val body = MultipartBody.Part.createFormData("profileImg",file.name,req_file)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repo = UserRepository()
                    val response = repo.editImage(body)
                    if(response.success == true)
                    {
                        var instance = LensdaysDB.getInstance(requireContext()).getUserDAO()
                        instance.deleteUser()
                        instance.insertUser(response.data!!)
                        withContext(Dispatchers.Main)
                        {
                            var intent = Intent(requireContext(),BottomActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    else
                    {
                        withContext(Dispatchers.Main)
                        {
                            val snk = Snackbar.make(cvProfile,"${response.message}",Snackbar.LENGTH_INDEFINITE)
                            snk.setAction("OK",View.OnClickListener {
                                snk.dismiss()
                            })
                            snk.show()
                        }
                    }
                }
                catch (ex:Exception)
                {
                    withContext(Dispatchers.Main)
                    {
                        println(ex.printStackTrace())
                        val snk = Snackbar.make(cvProfile,"Sorry! We are having some problem:(",Snackbar.LENGTH_INDEFINITE)
                        snk.setAction("OK",View.OnClickListener {
                            snk.dismiss()
                        })
                        snk.show()
                    }

                }
            }
        }
        else
        {
            val snk = Snackbar.make(tvContact,"Please Select image", Snackbar.LENGTH_LONG)
            snk.setAction("OK",View.OnClickListener {
                snk.dismiss()
            })
            snk.show()
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.btnEdit->{
                if(RetrofitService.Online == true)
                {
                    editUserDetails()
                }
                else
                {
                    val snk = Snackbar.make(tvContact,"No Internet", Snackbar.LENGTH_LONG)
                    snk.setAction("OK",View.OnClickListener {
                        snk.dismiss()
                    })
                    snk.show()
                }


            }

            R.id.btnChange->{
                uploadImage()
            }

            R.id.cvProfile ->{
                if(RetrofitService.Online == true)
                {
                   showPopUp()
                }
                else
                {
                    val snk = Snackbar.make(tvContact,"No Internet", Snackbar.LENGTH_LONG)
                    snk.setAction("OK",View.OnClickListener {
                        snk.dismiss()
                    })
                    snk.show()
                }
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item!!.itemId)
        {
            R.id.camera ->{
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent,CAMERA_REQUEST_CODE)
            }

            R.id.gallery ->{
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent,GALLERY_REQUEST_CODE)
            }
        }
        return true
    }
    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK)
        {
            if (requestCode == GALLERY_REQUEST_CODE && data != null) {
                //overall location of selected image
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = requireActivity().contentResolver
                //locator and identifier
                val cursor =
                    contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()//moveTONext // movetolast
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                image_url = cursor.getString(columnIndex)
                //image preview
                cvProfile.setImageBitmap(BitmapFactory.decodeFile(image_url))
                cursor.close()
            } else if (requestCode == CAMERA_REQUEST_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                image_url = file!!.absolutePath
                cvProfile.setImageBitmap(BitmapFactory.decodeFile(image_url))
            }
        }
    }
}