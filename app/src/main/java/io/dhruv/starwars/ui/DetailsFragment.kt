package io.dhruv.starwars.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.dhruv.starwars.R
import io.dhruv.starwars.StarWarsApplication
import io.dhruv.starwars.constant.ListConverter
import io.dhruv.starwars.constant.RandomColors
import io.dhruv.starwars.modal.Film
import io.dhruv.starwars.paging.FilmDetailAdapter
import io.dhruv.starwars.paging.LoaderAdapter
import io.dhruv.starwars.viewModel.DetailFragmentViewModel
import io.dhruv.starwars.viewModel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailsFragment : Fragment() {

    val args: DetailsFragmentArgs by navArgs()
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var viewModel: DetailFragmentViewModel
    lateinit var filmAdapter : FilmDetailAdapter
    lateinit var recyclerView : RecyclerView
    lateinit var backButton : ImageView
    lateinit var contraintLayout : ConstraintLayout
    lateinit var characterName : TextView
    private val dataList = mutableListOf<Film>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.character_detail_fragment,container,false)
        val value = args.character
        Log.e("selected", "$value ", )

        (activity?.application as StarWarsApplication).applicationComponent.inject(this)

        viewModel =  ViewModelProvider(this, viewModelFactory)[DetailFragmentViewModel::class.java]
        val filmLinks = ListConverter.fromString(value.filmList)
        Log.e("size", "${filmLinks.size} ", )
            filmLinks.forEach {
            extractFilmIdFromUrl(it)?.let { it1 -> viewModel.getFilms(it1) }
        }

        recyclerView = rootView.findViewById(R.id.filmList)
        characterName = rootView.findViewById(R.id.characterName)
        contraintLayout = rootView.findViewById(R.id.parentLayout)
        backButton = rootView.findViewById(R.id.backBt)
        characterName.text = value.name
        recyclerView.layoutManager = LinearLayoutManager(activity)
        filmAdapter = FilmDetailAdapter()
        recyclerView.adapter = filmAdapter
        contraintLayout.setBackgroundColor(requireContext().getColor(RandomColors.getRandomColor()))
        recyclerView.hasFixedSize()
        filmAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_characterHomeFragment2)
        }




        lifecycleScope.launch {
            viewModel.moveDetail.observe(viewLifecycleOwner){
                it?.let { it1 ->
                    Log.e("TAG", "onCreateView: ${it1.title}", )
                    filmAdapter.addItem(it1)
                }
            }

        }

        return rootView
    }

    fun extractFilmIdFromUrl(url: String): Int? {
        val pattern = Regex("/films/(\\d+)/")
        val matchResult = pattern.find(url)
        return matchResult?.groups?.get(1)?.value?.toIntOrNull()
    }


}