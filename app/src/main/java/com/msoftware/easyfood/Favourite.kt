package com.msoftware.easyfood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.msoftware.easyfood.adapters.FavouritesAdapter
import com.msoftware.easyfood.viewModel.HomeViewModel


class Favourite : Fragment() {
    lateinit var viewModel: HomeViewModel
    lateinit var favouritesAdapter: FavouritesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favourite, container, false)

        favouritesAdapter = FavouritesAdapter()
        val favRecycler = view?.findViewById<RecyclerView>(R.id.favRecycler)
        favRecycler?.layoutManager  = GridLayoutManager(activity,  2, GridLayoutManager.VERTICAL, false)
        favRecycler?.adapter = favouritesAdapter

        observerFavourite()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteFromDb(favouritesAdapter.differ.currentList[position])

                Snackbar.make(requireView(), "Item Deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo", View.OnClickListener {
                        viewModel.addToDb(favouritesAdapter.differ.currentList[position])
                    }
                ).show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(view.findViewById(R.id.favRecycler))

        return view
    }



    private fun observerFavourite() {
       viewModel.observedbLiveData().observe(viewLifecycleOwner, Observer {meals ->
            favouritesAdapter.differ.submitList(meals)

       })
    }
    //requiredActivity


}