package io.dhruv.starwars.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.dhruv.starwars.StarWarsApplication
import io.dhruv.starwars.adapter.SortAdapter
import io.dhruv.starwars.databinding.FilterDialogBinding
import io.dhruv.starwars.viewModel.HomeFragmentViewModel

class BottomSheetFragment(private val homeViewModel: HomeFragmentViewModel) : BottomSheetDialogFragment() {


    lateinit var binding: FilterDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as StarWarsApplication).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FilterDialogBinding.inflate(layoutInflater, container, false)

        binding.sortFilterRecyclerView.apply {
            this.layoutManager =  LinearLayoutManager(activity)
        }

        fetchFilmDetails()

        return binding.root
    }

    fun fetchFilmDetails(){
        homeViewModel.updatedFilter.observe(viewLifecycleOwner, Observer {list->
            binding.sortFilterRecyclerView.adapter = SortAdapter(list, SortAdapter.SortClickedListener {
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
    }

}