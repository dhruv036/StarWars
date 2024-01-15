package io.dhruv.starwars.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.dhruv.starwars.R
import io.dhruv.starwars.StarWarsApplication
import io.dhruv.starwars.constant.ListConverter
import io.dhruv.starwars.constant.RandomColors
import io.dhruv.starwars.databinding.CharacterDetailFragmentBinding
import io.dhruv.starwars.db.entity.CharacterEntity
import io.dhruv.starwars.adapter.FilmDetailAdapter
import io.dhruv.starwars.viewModel.DetailFragmentViewModel
import io.dhruv.starwars.viewModel.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsFragment : Fragment() {

    val args: DetailsFragmentArgs by navArgs()
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var binding: CharacterDetailFragmentBinding
    lateinit var viewModel: DetailFragmentViewModel
    lateinit var filmAdapter : FilmDetailAdapter
    var navArgs : CharacterEntity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as StarWarsApplication).applicationComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterDetailFragmentBinding.inflate(layoutInflater, container, false)
        viewModel =  ViewModelProvider(this, viewModelFactory)[DetailFragmentViewModel::class.java]

        getArgumentsFromNavigation()
        declareValue()
        setClickListener()
        fetchCharacterDetails()

        return binding.root
    }

    fun fetchCharacterDetails(){
        lifecycleScope.launch {
            viewModel.moveDetail.observe(viewLifecycleOwner){
                it?.let { it1 ->
                    Log.e("TAG", "onCreateView: ${it1.title}", )
                    filmAdapter.addItem(it1)
                }
            }

        }
    }

    fun setClickListener(){
        binding.backBt.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_characterHomeFragment2)
        }
    }

    fun declareValue(){
        binding.characterName.text = navArgs?.name
        filmAdapter = FilmDetailAdapter()
        filmAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.filmList.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = filmAdapter
            this.hasFixedSize()
        }
        binding.parentLayout.setBackgroundColor(requireContext().getColor(RandomColors.getRandomColor()))

    }

    fun getArgumentsFromNavigation(){
        navArgs = args.character
        val filmLinks = ListConverter.fromString(navArgs!!.filmList)
        filmLinks.forEach {
            extractFilmIdFromUrl(it)?.let { it1 -> viewModel.getFilms(it1) }
        }
    }

    fun extractFilmIdFromUrl(url: String): Int? {
        val pattern = Regex("/films/(\\d+)/")
        val matchResult = pattern.find(url)
        return matchResult?.groups?.get(1)?.value?.toIntOrNull()
    }



}