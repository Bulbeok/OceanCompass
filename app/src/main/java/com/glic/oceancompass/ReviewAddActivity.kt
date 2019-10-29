package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.reviewadd.*


class ReviewAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reviewadd)

        send.setOnClickListener {
            when {
                titles.text.toString() == "" -> Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_LONG).show()
                explanation.text.toString() == "" -> Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_LONG).show()
                loca.text.toString() == "" -> Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_LONG).show()
                else -> {
                    val pref = this.getSharedPreferences("sessionCookie", Context.MODE_PRIVATE)
                    val sessionId = pref.getString("sessionCookie", null)
                    val request = object : StringRequest(
                        POST, "https://175.206.239.109:8443/oceancompass/AddMobilReviewServlet",
                        Response.Listener {
                            Toast.makeText(this, "작성되었습니다.", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this,ReviewActivity::class.java))
                            finish()
                        },
                        Response.ErrorListener {
                            Log.e("에러", "[${it.message}]")
                        }) {
                        // request 시 key, value 보낼 때
                        override fun getParams(): Map<String, String> {
                            val params = HashMap<String, String>()
                            params["title"] = titles.text.toString()
                            params["explanation"] = explanation.text.toString()
                            params["address"] = loca.text.toString()
                            return params
                        }

                        override fun getHeaders(): Map<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Cookie"] = sessionId!!
                            return headers
                        }
                    }
                    val queue = Volley.newRequestQueue(this)
                    queue.add(request)
                }
            }
        }
        loca.setOnClickListener {
            startActivityForResult(Intent(this, ReviewLocaActivity::class.java), 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1) {
            loca.text = data!!.extras!!.getString("Date")
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, ReviewActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
    }
}

/*
private fun getRealPathFromURI(contentURI: Uri): String {


    val proj = arrayOf(MediaStore.Images.Media.DATA)
    val c = managedQuery(contentURI, proj, null, null, null)
    val index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

    c.moveToFirst()

    return c.getString(index)




    *//*       if (contentURI.path!!.startsWith("/storage")) {
               return contentURI.path
           }
           val id =
               DocumentsContract.getDocumentId(contentURI).split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
           val columns = arrayOf(MediaStore.Files.FileColumns.DATA)
           val selection = MediaStore.Files.FileColumns._ID + " = " + id
           val cursor = contentResolver.query(
               MediaStore.Files.getContentUri("external"),
               columns,
               selection,
               null,
               null
           )
           try {
               val columnIndex = cursor!!.getColumnIndex(columns[0])
               if (cursor.moveToFirst()) {
                   return cursor.getString(columnIndex)
               }
           } finally {
               cursor!!.close()
           }
           return null*//*
}

private val GET_GALLERY_IMAGE = 200*/


/*if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {

    //getting the image Uri
    val imageUri = data.data
    try {
//                val test = imageUri?.let { getRealPathFromURI(it) }
        Log.e("테스트",getRealPathFromURI(imageUri!!))
        Toast.makeText(this, imageUri.toString(), Toast.LENGTH_SHORT).show()
        //getting bitmap object from uri
        *//*val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)*//*
        //calling the method uploadBitmap to upload image
    } catch (e: IOException) {
        e.printStackTrace()
    }
}*/
// private val GET_GALLERY_IMAGE = 200
//
// private var imageView: ImageView? = null
// private var imageView2: ImageView? = null
// private var imageView3: ImageView? = null
//
// private var i = 1
// private var temp = arrayListOf<Bitmap>()
//
// imageView.setOnClickListener(new View.OnClickListener(){
// @Override
// public void onClick(View v) {
// Intent intent = new Intent();
// intent.setType("image/*");
// intent.setAction(Intent.ACTION_GET_CONTENT);
// startActivityForResult(intent, REQUEST_CODE);
// }
// });
//
// override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
// super.onActivityResult(requestCode, resultCode, data)
// if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
//
// //getting the image Uri
// val imageUri = data.data
// try {
// //getting bitmap object from uri
// val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
//
// //displaying selected image to imageview
// when (i) {
// 1 -> {
// imageView?.setImageURI(imageUri)
// imageView2!!.isVisible = true
// }
// 2 -> {
// imageView2?.setImageURI(imageUri)
// imageView3!!.isVisible = true
// }
// 3 -> imageView3?.setImageURI(imageUri)
// }
// i++
//
// //calling the method uploadBitmap to upload image
// temp.add(bitmap)
// } catch (e: IOException) {
// e.printStackTrace()
// }
//
// }
// }
//
//
// */
// /*
// * The method is taking Bitmap as an argument
// * then it will return the byte[] array for the given bitmap
// * and we will send this array to the server
// * here we are using PNG Compression with 80% quality
// * you can give quality between 0 to 100
// * 0 means worse quality
// * 100 means best quality
// * *//*
//
// fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray {
// val byteArrayOutputStream = ByteArrayOutputStream()
// bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
// return byteArrayOutputStream.toByteArray()
// }
//
// private fun uploadBitmap(bitmap: ArrayList<Bitmap>) {
//
// val pref = this.getSharedPreferences("sessionCookie", Context.MODE_PRIVATE)
// val sessionId = pref.getString("sessionCookie", null)
//
// NukeSSLCerts().nuke()
//
// //our custom volley request
// val volleyMultipartRequest =
// object : VolleyMultipartRequest(POST,"https://175.206.239.109:8443/oceancompass/AddMobilReviewServlet",
// Response.Listener { response ->
// try {
// val obj = JSONObject(String(response.data))
// Toast.makeText(
// applicationContext,
// obj.getString("message"),
// Toast.LENGTH_SHORT
// ).show()
// } catch (e: JSONException) {
// e.printStackTrace()
// }
// },
// Response.ErrorListener { error ->
// Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show() }
// ) {
// override fun getHeaders(): Map<String, String> {
// val headers = HashMap<String, String>()
// headers["Cookie"] = sessionId!!
// return headers
// }
//
// */
// /*
// * If you want to add more parameters with the image
// * you can do it here
// * here we have only one parameter with the image
// * which is tags
// * *//*
//
// @Throws(AuthFailureError::class)
// override fun getParams(): Map<String, String> {
// val params = HashMap<String,String>()
// params["title"] = reviewtitle.text.toString()
// return params
// }
//
// */
// /*
// * Here we are passing image by renaming it with a unique name
// * *//*
//
// override fun getByteData(): Map<String, DataPart>? {
// val params = HashMap<String,DataPart>()
// val imagename = System.currentTimeMillis()
// params["test"] = DataPart(
// "$imagename.png",
// getFileDataFromDrawable(bitmap[0])
// )
// return params
// }
// }
//
// //adding the request to volley
// volleyMultipartRequest.retryPolicy = DefaultRetryPolicy(30000,
// DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
// DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
// Volley.newRequestQueue(this).add(volleyMultipartRequest)
// }