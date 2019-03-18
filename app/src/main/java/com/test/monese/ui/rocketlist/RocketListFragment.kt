package com.test.monese.ui.rocketlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.test.monese.R
import com.test.monese.uimodules.controller.RocketListAdapterController
import com.test.presentation.model.Rocket
import com.test.presentation.resource.Resource
import com.test.presentation.viewmodel.RocketListViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_rocket_list.*
import kotlinx.android.synthetic.main.fragment_rocket_list_content.*
import org.koin.android.viewmodel.ext.android.viewModel

class RocketListFragment : Fragment() {

    private val viewModel: RocketListViewModel by viewModel()

    private val rocketListAdapterController = RocketListAdapterController { onRocketItemClick(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rocket_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set LifecycleObserver
        lifecycle.addObserver(viewModel)

        //Observe State in ViewModel
        viewModel.rocket.observe(this,
            Observer {
                handleRocketListState(it)
            })

        //Setup RecyclerView
        initRecyclerView()

        //SetupSwipeRefreshLayout
        initSwipeRefreshLayout()

        //Setup BottomSheetBehavior
        initBottomSheet()

        initFab()

        setUpChipBehaviour()

    }

    private fun initRecyclerView() {
        fragmentRocketListRecyclerView.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        fragmentRocketListRecyclerView.setHasFixedSize(true)
        fragmentRocketListRecyclerView.itemAnimator = SlideInUpAnimator()
        fragmentRocketListRecyclerView.itemAnimator?.apply {
            addDuration = 250
        }
        fragmentRocketListRecyclerView.adapter = rocketListAdapterController.adapter
    }

    private fun initSwipeRefreshLayout() {
        viewModel.swipeRefreshing.observe(this,
            Observer {
                fragmentRocketListSwipeRefreshLayout.isRefreshing = it
            })

        fragmentRocketListSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

    }

    private fun initBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun initFab() {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        fragmentRocketListFloatingActionButton.setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)
            }
        }
    }

    private fun setUpChipBehaviour(){
        bottomSheetChipFilterActive.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.filterRocketList() else viewModel.getData()
        }

    }

    private fun handleRocketListState(resource: Resource<List<Rocket>>) {
        when (resource) {
            is Resource.Loading -> {
                fragmentRocketListProgress.visibility = View.VISIBLE
            }
            is Resource.Success -> {
                fragmentRocketListProgress.visibility = View.INVISIBLE
                rocketListAdapterController.setContent(resource.data)
            }
            is Resource.Error -> {
                fragmentRocketListProgress.visibility = View.INVISIBLE
                rocketListAdapterController.setContent(resource.data)
            }
        }
    }

    private fun onRocketItemClick(rocket: Rocket) {
        val action = RocketListFragmentDirections.actionRocketListFragmentToLaunchDetailFragment(rocket.rocketId)
        Navigation.findNavController(view!!).navigate(action)
    }

}
