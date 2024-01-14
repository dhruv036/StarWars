package io.dhruv.starwars.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.dhruv.starwars.R
import io.dhruv.starwars.StarWarsApplication
import io.dhruv.starwars.modal.Filter
import io.dhruv.starwars.viewModel.HomeFragmentViewModel

class BottomSheetFragment(private val homeViewModel: HomeFragmentViewModel) : BottomSheetDialogFragment() {

    lateinit var listView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as StarWarsApplication).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.filter_dialog,container,false)

        listView = view.findViewById(R.id.sort_filter_recyclerView)

        listView.layoutManager = LinearLayoutManager(activity)

        homeViewModel.updatedFilter.observe(viewLifecycleOwner, Observer {list->
            listView.adapter = SortAdapter(list, SortAdapter.SortClickedListener {
                list.forEach {
                    if (it.isSelected) {
                        it.isSelected =false
                    }
                }
                it.isSelected =  !it.isSelected
                homeViewModel.sort(it)
                dismiss()
            })
        })

        return view
    }

}