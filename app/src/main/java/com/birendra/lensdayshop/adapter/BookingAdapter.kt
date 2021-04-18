package com.birendra.lensdayshop.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.birendra.lensdayshop.R
import com.birendra.lensdayshop.api.RetrofitService
import com.birendra.lensdayshop.api.StaticCartModel
import com.birendra.lensdayshop.entity.BookingLensdays
import com.birendra.lensdayshop.interfaces.CartRefreshment
import com.birendra.lensdayshop.repository.BookingRepository
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.lang.Exception

class BookingAdapter(val context: Context, var lstCart:MutableList<BookingLensdays>, var rfr: CartRefreshment):
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {
    class BookingViewHolder(val view: View):RecyclerView.ViewHolder(view)
    {
        val tvProduct: TextView
        val tvBrand:TextView
        val tvPrice:TextView
        val ivMinus: ImageView
        val ivAdd:ImageView
        val tvQuantity:TextView
        val ivProduct:ImageView
        val cbCheck: CheckBox
        val totalStock:TextView
        val originalPrice:TextView
        val discountedPrice:TextView
        val progress : ContentLoadingProgressBar
        init {
            tvProduct = view.findViewById(R.id.tvProduct)
            tvBrand = view.findViewById(R.id.tvBrand)
            tvPrice = view.findViewById(R.id.tvPrice)
            ivMinus = view.findViewById(R.id.ivMinus)
            ivAdd = view.findViewById(R.id.ivAdd)
            tvQuantity = view.findViewById(R.id.tvQuantity)
            ivProduct = view.findViewById(R.id.ivProduct)
            cbCheck = view.findViewById(R.id.cbCheck)
            totalStock = view.findViewById(R.id.totalStock)
            originalPrice = view.findViewById(R.id.originalPrice)
            discountedPrice = view.findViewById(R.id.discountedPrice)
            progress = view.findViewById(R.id.progressBar)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_cart,parent,false)
        return BookingViewHolder(view)
    }
    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        var cart = lstCart[position]
        if(RetrofitService.Online == true)
        {
            holder.tvProduct.text = cart.product_id!!.pname
            holder.tvBrand.text = cart.product_id!!.pBrand
            holder.tvPrice.text = "Rs "+cart.price.toString()
            holder.tvQuantity.text = cart.quantity.toString()
            holder.totalStock.text = cart.product_id!!.availableStock.toString()+" stock available"
            //check with condition with discounts and perform
            if(cart.product_id!!.newPrice>0)
            {
                holder.originalPrice.text = "Rs ${cart.quantity*cart.product_id!!.pprice}"
                holder.discountedPrice.text = "-${cart.product_id!!.discount}%"
                holder.originalPrice.paintFlags = holder.originalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.originalPrice.visibility = View.VISIBLE
                holder.discountedPrice.visibility = View.VISIBLE
            }

            else
            {
                holder.originalPrice.visibility = View.GONE
                holder.discountedPrice.visibility = View.GONE
            }

            var imagePath = RetrofitService.loadImgPath()+cart.product_id!!.pimage!!.replace("\\","/")
            Glide.with(context).load(imagePath).into(holder.ivProduct)
            //comes from db

        }

        else
        {
            holder.tvProduct.text = cart.productName
            holder.tvBrand.text = cart.productBrand
            holder.tvPrice.text = "Rs "+cart.price.toString()
            holder.tvQuantity.text = cart.quantity.toString()
            holder.totalStock.text = cart.offlineAvailableStock.toString()+" stock available"
            //check with condition with discounts and perform
            if(cart.newPrice>0)
            {
                holder.originalPrice.text = "Rs ${cart.quantity*cart.pprice}"
                holder.discountedPrice.text = "-${cart.offlineDiscount}%"
                holder.originalPrice.paintFlags = holder.originalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.originalPrice.visibility = View.VISIBLE
                holder.discountedPrice.visibility = View.VISIBLE
            }

            else
            {
                holder.originalPrice.visibility = View.GONE
                holder.discountedPrice.visibility = View.GONE
            }
        }

        var newPrice = cart.price/cart.quantity
        var currentQuantity = cart.quantity

        //plus minus listener
        holder.ivAdd.setOnClickListener {
            if(RetrofitService.Online == true)
            {
                if(cart.product_id!!.availableStock>0)
                {
                    CoroutineScope(Dispatchers.Main).launch {
                        holder.progress.visibility = View.VISIBLE


                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val repo = BookingRepository()
                            var quantity = cart.quantity+1
                            val response = repo.updateBooking(cart._id,quantity)
                            if(response.success == true)
                            {
                                cart.product_id!!.availableStock-=1
                                cart.quantity+=1
                                cart.price = newPrice * cart.quantity
                                withContext(Dispatchers.Main)
                                {
                                    notifyDataSetChanged()
                                    rfr.refreshCartActivity()
                                    holder.progress.visibility = View.GONE
                                }


                            }
                            else
                            {
                                withContext(Dispatchers.Main)
                                {
                                    val snk = Snackbar.make(holder.tvBrand,"${response.message}", Snackbar.LENGTH_LONG)
                                    snk.setAction("Ok",View.OnClickListener {
                                        snk.dismiss()
                                    })
                                    snk.show()
                                    holder.progress.visibility = View.GONE

                                }
                            }
                        }
                        catch (ex:Exception)
                        {
                            withContext(Dispatchers.Main)
                            {
                                val snk = Snackbar.make(holder.tvBrand,"${ex.toString()}", Snackbar.LENGTH_LONG)
                                snk.setAction("Ok",View.OnClickListener {
                                    snk.dismiss()
                                })
                                snk.show()
                                println(ex.printStackTrace())
                            }
                        }
                    }
                }

            }
            else
            {

                    val snk = Snackbar.make(holder.tvBrand,"No Internet", Snackbar.LENGTH_LONG)
                    snk.setAction("Ok",View.OnClickListener {
                        snk.dismiss()
                    })
                    snk.show()


            }

        }
        holder.ivMinus.setOnClickListener {
            if(RetrofitService.Online == true)
            {
                if(cart.quantity > 1)
                {
                    CoroutineScope(Dispatchers.Main).launch {
                        holder.progress.visibility = View.VISIBLE


                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val repo = BookingRepository()
                            var quantity = cart.quantity-1
                            val response = repo.updateBooking(cart._id,quantity)
                            if(response.success == true)
                            {
                                cart.product_id!!.availableStock+=1
                                cart.quantity-=1
                                cart.price = newPrice*cart.quantity
                                withContext(Dispatchers.Main)
                                {
                                    notifyDataSetChanged()
                                    rfr.refreshCartActivity()
                                    holder.progress.visibility = View.GONE
                                }


                            }
                            else
                            {
                                withContext(Dispatchers.Main)
                                {
                                    val snk = Snackbar.make(holder.tvBrand,"${response.message}", Snackbar.LENGTH_LONG)
                                    snk.setAction("Ok",View.OnClickListener {
                                        snk.dismiss()
                                    })
                                    snk.show()
                                    holder.progress.visibility = View.GONE

                                }
                            }
                        }
                        catch (ex:Exception)
                        {
                            withContext(Dispatchers.Main)
                            {
                                val snk = Snackbar.make(holder.tvBrand,"${ex.toString()}", Snackbar.LENGTH_LONG)
                                snk.setAction("Ok",View.OnClickListener {
                                    snk.dismiss()
                                })
                                snk.show()
                                println(ex.printStackTrace())
                            }
                        }
                    }
                }
            }
            else
            {
                val snk = Snackbar.make(holder.tvBrand,"No Internet", Snackbar.LENGTH_LONG)
                snk.setAction("Ok",View.OnClickListener {
                    snk.dismiss()
                })
                snk.show()
            }






        }
        holder.cbCheck.setOnClickListener {
            if(holder.cbCheck.isChecked == true)
            {
                if(!StaticCartModel.myCart.contains(cart))
                {
                    StaticCartModel.myCart.add(cart)
                    rfr.refreshCartActivity()
                }
            }
            else
            {
                StaticCartModel.myCart.remove(cart)
                rfr.refreshCartActivity()
            }
        }


    }
    override fun getItemCount(): Int {
        return lstCart.size
    }

}