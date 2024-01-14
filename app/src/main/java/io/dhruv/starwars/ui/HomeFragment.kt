package io.dhruv.starwars.ui

import android.os.Bundle
import android.security.ConfirmationCallback
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.dhruv.starwars.R
import io.dhruv.starwars.StarWarsApplication
import io.dhruv.starwars.db.CharacterDB
import io.dhruv.starwars.paging.CharacterAdapter
import io.dhruv.starwars.paging.LoaderAdapter
import io.dhruv.starwars.viewModel.HomeFragmentViewModel
import io.dhruv.starwars.viewModel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeFragment : Fragment() {
    @Inject
    lateinit var  characterDB : CharacterDB
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var recyclerView : RecyclerView
    lateinit var searchView : SearchView
    lateinit var filterView : ImageView

    lateinit var adapter: CharacterAdapter
    lateinit var viewModel: HomeFragmentViewModel

    var isSearching =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as StarWarsApplication).applicationComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.home_fragment, container, false)

        (activity?.application as StarWarsApplication).applicationComponent.inject(this)


        recyclerView = view.findViewById(R.id.recycleview)
        recyclerView.hasFixedSize()
        searchView = view.findViewById(R.id.searchBox)
        filterView = view.findViewById(R.id.filter)
        adapter = CharacterAdapter(requireActivity())
        recyclerView.layoutManager = GridLayoutManager(activity,2)
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        recyclerView.adapter =  adapter.withLoadStateHeaderAndFooter(
                      header = LoaderAdapter(),
                      footer = LoaderAdapter()
        )

        viewModel = ViewModelProvider(this, viewModelFactory)[HomeFragmentViewModel::class.java]

//        recyclerView.adapter =adapter

        filterView.setOnClickListener {
            BottomSheetFragment(viewModel).show(childFragmentManager,"")
        }


        adapter.onItemSelected {
            Log.e("item", "onItemClick: ${it.name}", )
            val action = HomeFragmentDirections.actionCharacterHomeFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }

        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                if(!newText.isNullOrBlank() && newText.isNotEmpty()){
                    Log.e("CHAR", "$newText ", )
                    viewModel.isSearching.value = true
                    isSearching = true
                    viewModel.sequence.value = newText.lowercase().trim()
                    lifecycleScope.launch {
                        Log.e("TAG", "Cleared", )
                        adapter.submitData(PagingData.empty())
                        viewModel.getSpecificCharacter().debounce(500).collectLatest{
                            if (isSearching) {
                                Log.e("TAG", "getCharacter: ----1", )
                                adapter.submitData(lifecycle,it)
                            }
                        }
                    }.onJoin
                }else{
                    Log.e("CHAR", "else ", )
                    viewModel.isSearching.value = false
                    viewModel.sequence.value = ""
                    isSearching = false
                    CoroutineScope(Dispatchers.IO).launch {
                        adapter.submitData(PagingData.empty())
                    }
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })
        searchView.setOnCloseListener{
            Log.e("TAG", "setOnCloseListener: ", )
            isSearching = false
            CoroutineScope(Dispatchers.IO).launch {
                adapter.submitData(PagingData.empty())
                getCharacter()
            }
            true
        }
        getCharacter()
        getSortedCharacter()

        return view
    }
    fun getCharacter(){
        lifecycleScope.launch {
            viewModel.list.collectLatest{
                if (!isSearching && viewModel.isSorting.value == false){
                    Log.e("TAG", "getCharacter: ----2", )
                    adapter.submitData(lifecycle,it)
                }
            }
        }
    }

    fun getSortedCharacter(){
        viewModel.isSorting.observe(viewLifecycleOwner, Observer {
            if (it){
                lifecycleScope.launch {
                    adapter.submitData(PagingData.empty())
                    viewModel.getSortedCharacter().collectLatest{
                        if (!isSearching){
                            Log.e("TAG", "getCharacter: ----2", )
                            adapter.submitData(lifecycle,it)
                        }
                    }
                }
            }
        })
    }
}