package com.birendra.lensdayshop

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.birendra.lensdayshop.api.RetrofitService
import com.birendra.lensdayshop.entity.BookingLensdays
import com.birendra.lensdayshop.entity.Lensdays
import com.birendra.lensdayshop.repository.BookingRepository
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class BookingPageActivity : AppCompatActivity(),View.OnClickListener {
    private var lensdays:Lensdays? =null
    private lateinit var tvPrice:TextView
    private lateinit var tvOriginalPrice:TextView
    private lateinit var tvDiscount:TextView
    private lateinit var tvProduct:TextView
    private lateinit var tvDescription:TextView
    private lateinit var btnAdd:Button
    private lateinit var ivFurniture:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        supportActionBar?.hide()
        binding()
        initialize()
        btnAdd.setOnClickListener(this)
    }

    private fun binding()
    {
        tvPrice = findViewById(R.id.tvPrice)

        tvDescription = findViewById(R.id.tvDescription)
        tvProduct = findViewById(R.id.tvProduct)
        btnAdd = findViewById(R.id.btnAdd)
        ivFurniture = findViewById(R.id.ivFurniture)
        lensdays = intent.getParcelableExtra("furniture")
    }

    private fun initialize()
    {
        if(lensdays!!.newPrice > 0)
        {
            tvPrice.text = "Rs "+lensdays!!.newPrice.toString()
            tvOriginalPrice.text = "Rs "+lensdays!!.pprice.toString()
            tvDiscount.text = "-"+lensdays!!.discount.toString()+"%"
            tvOriginalPrice.visibility = View.VISIBLE
            tvOriginalPrice.paintFlags = tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            tvDiscount.visibility = View.VISIBLE
        }
        else
        {
            tvPrice.text = "Rs "+lensdays!!.pprice.toString()
            tvOriginalPrice.visibility = View.GONE
            tvDiscount.visibility = View.GONE
        }

        tvDescription.text = lensdays!!.pdesc
        tvProduct.text = lensdays!!.pname

        var imgPath = RetrofitService.loadImgPath()+lensdays!!.pimage!!.replace("\\","/")
        Glide.with(this@BookingPageActivity).load(imgPath).into(ivFurniture)

    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.btnAdd->{
                if(RetrofitService.Online == true)
                {
                    var booking = BookingLensdays(product_id = lensdays,quantity = 1,delivery_address = "Not Added",delivery_number = "Not Added")
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val bookingRepository = BookingRepository()
                            val response = bookingRepository.bookFurniture(booking)
                            if(response.success == true)
                            {
                                withContext(Dispatchers.Main)
                                {
                                    val snk = Snackbar.make(tvProduct,"Product Added to Cart",Snackbar.LENGTH_LONG)
                                    snk.setAction("Go to Cart",View.OnClickListener {
                                        snk.dismiss()
                                    })
                                    snk.show()
                                }
                            }
                            else
                            {
                                withContext(Dispatchers.Main)
                                {
                                    if(response.message == "Item already exists in cart.")
                                    {
                                        val snk = Snackbar.make(tvProduct,"${response.message}",Snackbar.LENGTH_LONG)
                                        snk.setAction("Go to Cart",View.OnClickListener {
                                            snk.dismiss()
                                        })
                                        snk.show()
                                    }
                                    else
                                    {
                                        val snk = Snackbar.make(tvProduct,"${response.message}",Snackbar.LENGTH_LONG)
                                        snk.setAction("OK",View.OnClickListener {
                                            snk.dismiss()
                                        })
                                        snk.show()
                                    }

                                }
                            }

                        }
                        catch (ex:Exception)
                        {
                            withContext(Dispatchers.Main)
                            {
                                val snk = Snackbar.make(tvProduct,"${ex.toString()}",Snackbar.LENGTH_LONG)
                                snk.setAction("OK",View.OnClickListener {
                                    snk.dismiss()
                                })
                                snk.show()
                                println(ex.printStackTrace())
                            }

                        }
                    }
                }
                else
                {

                }
            }
        }
    }
}