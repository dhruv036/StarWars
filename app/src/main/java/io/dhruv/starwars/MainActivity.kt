package io.dhruv.starwars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

//    @Inject
//    lateinit var  characterDB : CharacterDB
//    @Inject
//    lateinit var characterDB2 : CharacterDB
//    @Inject
//    lateinit var viewModelFactory: ViewModelFactory
//    lateinit var recyclerView : RecyclerView
//    lateinit var searchView : SearchView
//
//    lateinit var adapter: CharacterAdapter
//    lateinit var viewModel: CharacterViewModel
//    var isSearching =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        (application as StarWarsApplication).applicationComponent.inject(this)
//
////        val map = (application as StarWarsApplication).applicationComponent.getMap()
//
//        recyclerView = findViewById(R.id.recycleview)
//        recyclerView.hasFixedSize()
//        searchView = findViewById(R.id.searchBox)
//        adapter = CharacterAdapter()
//        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
//        viewModel = ViewModelProvider(this, viewModelFactory)[CharacterViewModel::class.java]
//
//        recyclerView.layoutManager = GridLayoutManager(this,2)
//        recyclerView.adapter =adapter
//
//
//        adapter.onItemSelected {
//            Log.e("item", "onItemClick: ${it.name}", )
//        supportFragmentManager
//            .beginTransaction().
//            addToBackStack(null).
//            replace(R.id.showCharacter, CharacterHomeFragment()).commit()
//            findViewById<FragmentContainerView>(R.id.showCharacter).visibility = View.VISIBLE
//        }

//        searchView.setOnQueryTextListener( object : OnQueryTextListener{
//            override fun onQueryTextSubmit(newText: String?): Boolean {
//                if(!newText.isNullOrBlank() && newText.isNotEmpty()){
//                    Log.e("CHAR", "$newText ", )
//                    viewModel.isSearcing.value = true
//                    isSearching = true
//                    viewModel.sequence.value = newText.lowercase().trim()
//                    lifecycleScope.launch {
//                        Log.e("TAG", "Cleared", )
//                        adapter.submitData(PagingData.empty())
//                        viewModel.getSpecificCharacter().debounce(500).collectLatest{
//                            if (isSearching) {
//                                Log.e("TAG", "getCharacter: ----1", )
//                                adapter.submitData(lifecycle,it)
//                            }
//                        }
//                    }.onJoin
//                }else{
//                    Log.e("CHAR", "else ", )
//                    viewModel.isSearcing.value = false
//                    viewModel.sequence.value = ""
//                    isSearching = false
//                    CoroutineScope(Dispatchers.IO).launch {
//                        adapter.submitData(PagingData.empty())
//                    }
//                }
//                return true
//            }
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//                return true
//            }
//        })
//        searchView.setOnCloseListener{
//            Log.e("TAG", "setOnCloseListener: ", )
//            isSearching = false
//            CoroutineScope(Dispatchers.IO).launch {
//                adapter.submitData(PagingData.empty())
//                getCharacter()
//            }
//            true
//        }
//        getCharacter()

    }
//
//    fun getCharacter(){
//        lifecycleScope.launch {
//
//            viewModel.list.collectLatest{
//                if (!isSearching){
//                Log.e("TAG", "getCharacter: ----2", )
//                    adapter.submitData(lifecycle,it)
//                }
//            }
//        }
//    }
}