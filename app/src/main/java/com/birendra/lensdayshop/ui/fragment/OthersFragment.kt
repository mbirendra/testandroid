package com.birendra.lensdayshop.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.birendra.lensdayshop.R
import com.birendra.lensdayshop.adapter.LensdaysAdapter
import com.birendra.lensdayshop.api.RetrofitService
import com.birendra.lensdayshop.db.LensdaysDB
import com.birendra.lensdayshop.entity.Lensdays
import com.birendra.lensdayshop.repository.LensdaysRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class OthersFragment : Fragment() {
    var lstLensdays:MutableList<Lensdays> = mutableListOf()
    private lateinit var adapter: LensdaysAdapter
    private lateinit var recycler: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_others, container, false)
        recycler = view.findViewById(R.id.recycler)
        if(RetrofitService.Online == true) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repository = LensdaysRepository()
                    val response = repository.retrieveLensdays("Others")
                    if (response.success == true) {
                        lstLensdays = response.data!!
                        withContext(Dispatchers.Main)
                        {
                            LensdaysDB.getInstance(requireContext()).getLensdaysDAO()
                                .deleteLensdays("Others")
                            LensdaysDB.getInstance(requireContext()).getLensdaysDAO()
                                .insertLensdays(lstLensdays)
                            adapter = LensdaysAdapter(requireContext(), lstLensdays)
                            recycler.adapter = adapter
                            recycler.layoutManager = GridLayoutManager(requireContext(), 2)
                        }
                    } else {
                        withContext(Dispatchers.Main)
                        {
                            Toast.makeText(
                                requireContext(),
                                "${response.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main)
                    {
                        Toast.makeText(requireContext(), "${ex.toString()}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        else
        {
            CoroutineScope(Dispatchers.IO).launch {

                lstLensdays = LensdaysDB.getInstance(requireContext()).getLensdaysDAO()
                    .retrieveLensdays("Others")
                withContext(Dispatchers.Main) {
                    adapter = LensdaysAdapter(requireContext(), lstLensdays)
                    recycler.adapter = adapter
                    recycler.layoutManager = GridLayoutManager(requireContext(), 2)
                }
            }


        }
        return view
    }


}