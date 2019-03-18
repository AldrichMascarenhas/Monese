package com.test.monese.ui.launchdetail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.db.chart.animation.Animation
import com.db.chart.model.LineSet
import com.db.chart.renderer.AxisRenderer
import com.db.chart.tooltip.Tooltip
import com.db.chart.util.Tools
import com.test.monese.R
import com.test.monese.extensions.tag
import com.test.monese.uimodules.controller.LaunchListAdapterController
import com.test.presentation.model.Launch
import com.test.presentation.model.Rocket
import com.test.presentation.resource.Resource
import com.test.presentation.viewmodel.LaunchDetailViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_launch_detail.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class LaunchDetailFragment : Fragment() {

    private val viewModel: LaunchDetailViewModel by viewModel()

    private val launchListAdapterController = LaunchListAdapterController()

    private lateinit var rocketId: String


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_launch_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rocketId = arguments?.getString("rocketId") ?: ""


        viewModel.setRocketID(rocketId)

        //Set LifecycleObserver
        lifecycle.addObserver(viewModel)

        //Observe State in ViewModel
        viewModel.launchList.observe(this,
                Observer {
                    handleLaunchListState(it)
                })

        //Observe State in ViewModel
        viewModel.rocket.observe(this,
                Observer {
                    handleRocketState(it)
                })

        //Setup RecyclerView
        initRecyclerView()

        //SetupSwipeRefreshLayout
        initSwipeRefreshLayout()

    }

    private fun initRecyclerView() {
        fragmentLaunchDetailRecyclerView.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        fragmentLaunchDetailRecyclerView.setHasFixedSize(true)
        fragmentLaunchDetailRecyclerView.itemAnimator = SlideInUpAnimator()
        fragmentLaunchDetailRecyclerView.itemAnimator?.apply {
            addDuration = 250
        }
        fragmentLaunchDetailRecyclerView.adapter = launchListAdapterController.adapter
    }

    private fun initSwipeRefreshLayout() {
        viewModel.swipeRefreshing.observe(this,
                Observer {
                    fragmentLaunchDetailSwipeRefreshLayout.isRefreshing = it
                })

        fragmentLaunchDetailSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun handleLaunchListState(resource: Resource<List<Launch>>) {
        when (resource) {

            is Resource.Loading -> {
                fragmentLaunchDetailProgressBar.visibility = View.VISIBLE
            }

            is Resource.Success -> {
                fragmentLaunchDetailProgressBar.visibility = View.INVISIBLE

                val launchData = viewModel.processLaunchData(resource.data)
                setChartData(launchData)
                launchListAdapterController.setContent(resource.data)
            }

            is Resource.Error -> {
                fragmentLaunchDetailProgressBar.visibility = View.INVISIBLE

                val launchData = viewModel.processLaunchData(resource.data)
                setChartData(launchData)
                launchListAdapterController.setContent(resource.data)
            }
        }

    }

    private fun handleRocketState(resource: Resource<Rocket>) {
        when (resource) {
            is Resource.Success -> {
                with(resource.data) {
                    fragmentLaunchDetailTextViewRocketName.text = rocketName
                    fragmentLaunchDetailTextViewDescription.text = description
                }
            }
        }
    }


    private fun setChartData(pair: Pair<Array<String>, FloatArray>) {

        if (pair.first.isNotEmpty() && pair.second.isNotEmpty()) {

            fragmentLaunchDetailLineChartView.reset()


            val launchToolTip = Tooltip(context, R.layout.launch_chart_tooltip, R.id.launchChartToolTipTextView)
            launchToolTip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP)
            launchToolTip.setDimensions(Tools.fromDpToPx(58f).toInt(), Tools.fromDpToPx(25f).toInt())

            val launchDataSet = LineSet(pair.first, pair.second)
            launchDataSet.setColor(Color.parseColor("#b3b5bb"))
                    .setFill(Color.parseColor("#2d374c"))
                    .setDotsColor(Color.parseColor("#ffc755"))
                    .setThickness(4f)
                    .beginAt(0)
                    .endAt(pair.first.size)

            fragmentLaunchDetailLineChartView.addData(launchDataSet)
            fragmentLaunchDetailLineChartView.setTooltips(launchToolTip)
            fragmentLaunchDetailLineChartView.setYLabels(AxisRenderer.LabelPosition.NONE)
            fragmentLaunchDetailLineChartView.show(Animation().fromAlpha(0))
        } else {
            fragmentLaunchDetailTextViewNoLaunchError.visibility = View.VISIBLE
        }


    }
}
