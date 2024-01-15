package io.dhruv.starwars.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.dhruv.starwars.StarWarsApplication
import io.dhruv.starwars.databinding.HomeFragmentBinding
import io.dhruv.starwars.db.CharacterDB
import io.dhruv.starwars.adapter.LoaderAdapter
import io.dhruv.starwars.paging.CharacterAdapter
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
    lateinit var binding : HomeFragmentBinding
    lateinit var characterAdapter: CharacterAdapter
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
        binding = HomeFragmentBinding.inflate(layoutInflater, container, false)

        (activity?.application as StarWarsApplication).applicationComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeFragmentViewModel::class.java]
        initialization()
        setClickListerener()

        getCharacter()
        getSortedCharacter()

        return binding.root
    }

    fun onQuerySubmited(newText :String?){
        if(!newText.isNullOrBlank() && newText.isNotEmpty()){
            viewModel.isSearching.value = true
            isSearching = true
            viewModel.sequence.value = newText.lowercase().trim()
            lifecycleScope.launch {
                characterAdapter.submitData(PagingData.empty())
                viewModel.getSpecificCharacter().debounce(500).collectLatest{
                    if (isSearching) {
                        characterAdapter.submitData(lifecycle,it)
                    }
                }
            }.onJoin
        }else{
            viewModel.isSearching.value = false
            viewModel.sequence.value = ""
            isSearching = false
            CoroutineScope(Dispatchers.IO).launch {
                characterAdapter.submitData(PagingData.empty())
            }
        }
    }


    fun initialization(){
        characterAdapter = CharacterAdapter(requireActivity())
        characterAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.recycleview.apply {
            this.layoutManager = GridLayoutManager(activity,2)
            this.hasFixedSize()
            this.adapter =  characterAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter()
            )
        }
        characterAdapter.onItemSelected {
            Log.e("item", "onItemClick: ${it.name}", )
            val action = HomeFragmentDirections.actionCharacterHomeFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }
    }

    fun setClickListerener(){
        binding.searchBox.setOnCloseListener{
            binding.searchBox.isFocusable = false
            isSearching = false
            CoroutineScope(Dispatchers.IO).launch {
                characterAdapter.submitData(PagingData.empty())
                getCharacter()
            }
            true
        }
        binding.filter.setOnClickListener {
            BottomSheetFragment(viewModel).show(childFragmentManager,"")
        }


        binding.searchBox.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                onQuerySubmited(newText)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }

    fun getCharacter(){
        lifecycleScope.launch {
            viewModel.list.collectLatest{
                if (!isSearching && viewModel.isSorting.value == false){
                    characterAdapter.submitData(lifecycle,it)
                }
            }
        }
    }

    fun getSortedCharacter(){
        viewModel.isSorting.observe(viewLifecycleOwner, Observer {
            if (it){
                lifecycleScope.launch {
                    characterAdapter.submitData(PagingData.empty())
                    viewModel.getSortedCharacter().collectLatest{
                        if (!isSearching){
                            characterAdapter.submitData(lifecycle,it)
                        }
                    }
                }
            }
        })
    }
}