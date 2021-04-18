package com.birendra.lensdayshop.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.birendra.lensdayshop.BookingPageActivity
import com.birendra.lensdayshop.R
import com.birendra.lensdayshop.api.RetrofitService
import com.birendra.lensdayshop.entity.Lensdays
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class LensdaysAdapter(val context: Context, var lstProducts: MutableList<Lensdays>) :
    RecyclerView.Adapter<LensdaysAdapter.FurnitureViewHolder>() {
    class FurnitureViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var tvSaving: TextView
        var ivItem: ImageView
        var tvCut: TextView
        var tvPrice: TextView
        var tvProductName: TextView
        var layoutProduct: LinearLayout

        init {
            tvSaving = view.findViewById(R.id.tvSaving)
            ivItem = view.findViewById(R.id.ivItem)
            tvCut = view.findViewById(R.id.tvCut)
            tvPrice = view.findViewById(R.id.tvPrice)
            tvProductName = view.findViewById(R.id.tvProductName)
            layoutProduct = view.findViewById(R.id.layoutProduct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FurnitureViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_furniture, parent, false)
        return FurnitureViewHolder(view)
    }

    private fun getAccumulatedStock(stock: Int): Int {
        var availableStock = stock
        while (availableStock > 25) {
            availableStock = (availableStock / 2)
        }
        return availableStock + 3
    }

    override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {
        var furniture = lstProducts[position]

        holder.tvProductName.text = furniture.pname
        if (furniture.discount > 0) {

            holder.tvCut.visibility = View.VISIBLE
            holder.tvSaving.text = "Save ${furniture.discount}%"
            holder.tvCut.text = "Rs ${furniture.pprice}"
            holder.tvPrice.text = "Rs ${furniture.newPrice}"
            holder.tvCut.paintFlags = holder.tvCut.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            var availableStock = furniture.availableStock
            if (availableStock >= 25) {
                availableStock = getAccumulatedStock(availableStock)
            }
            holder.tvPrice.text = "Rs ${furniture.pprice}"
            holder.tvSaving.text = "${availableStock} stocks remaining"
        }

        if (RetrofitService.Online == true) {
            var imgPath = RetrofitService.loadImgPath() + furniture!!.pimage!!.replace("\\", "/")
            Glide.with(context).load(imgPath).into(holder.ivItem)
        }


        holder.layoutProduct.setOnClickListener {
            if (RetrofitService.Online == true) {
                val intent = Intent(context, BookingPageActivity::class.java)
                intent.putExtra("furniture", furniture)
                context.startActivity(intent)
            } else {
                val snk =
                    Snackbar.make(holder.ivItem, "No Internet Connection", Snackbar.LENGTH_LONG)
                snk.setAction("OK", View.OnClickListener {
                    snk.dismiss()
                })
                snk.show()
            }

        }
    }

    override fun getItemCount(): Int {
        return lstProducts.size
    }
}