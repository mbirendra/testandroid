package com.birendra.lensdayshop.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.birendra.lensdayshop.R
import com.birendra.lensdayshop.ui.MapsActivity
import com.birendra.lensdayshop.ui.fragment.ChairFragment
import com.birendra.lensdayshop.ui.fragment.CupboardFragment
import com.birendra.lensdayshop.ui.fragment.OthersFragment
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment(),BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var slider:ImageSlider
    private lateinit var nvItems:BottomNavigationView
    private lateinit var mapp: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        slider = root.findViewById(R.id.slider)
        nvItems = root.findViewById(R.id.nvItems)
        mapp = root.findViewById(R.id.mapp)
        mapp.setOnClickListener {
            startActivity(Intent(context, MapsActivity::class.java))
        }
        nvItems.setOnNavigationItemSelectedListener(this)
        toggleItem(CupboardFragment())
        initializeCarousel()
        return root
    }

    private fun initializeCarousel()
    {
        var sliders : MutableList<SlideModel> = mutableListOf(
            SlideModel("https://fulhameyecentre.co.uk/wp-content/uploads/sites/15/2020/02/woow.jpg","Normal Opticals",
            ),
            SlideModel("https://www.stylegods.com/wp-content/uploads/2017/01/home_slide_2.jpg","Sunglass"),
            SlideModel("https://images.pexels.com/photos/1054777/pexels-photo-1054777.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","Eyewear")
        )
        slider.setImageList(sliders, ScaleTypes.FIT)
    }

    private fun toggleItem(fragment:Fragment)
    {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.linearLay,fragment)
            addToBackStack(null)
            commit()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.nav_chair ->{
                toggleItem(ChairFragment())
            }

            R.id.nav_cupboard ->{
                toggleItem(CupboardFragment())
            }
            R.id.nav_others->{
                toggleItem(OthersFragment())
            }
        }
        return true

    }


}