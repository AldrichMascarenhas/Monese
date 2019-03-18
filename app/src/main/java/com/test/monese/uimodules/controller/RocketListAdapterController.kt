package com.test.monese.uimodules.controller

import com.airbnb.epoxy.EpoxyController
import com.test.monese.uimodules.viewholder.rocketViewItem
import com.test.presentation.model.Rocket

class RocketListAdapterController(private val itemClickCallback: (Rocket) -> Unit) : EpoxyController() {

    private var rocketList= listOf<Rocket>()

    fun setContent(db: List<Rocket>) {
        rocketList = db
        requestModelBuild()
    }

    override fun buildModels() {

        rocketList.forEach {
            rocketViewItem {
                id(it.id)
                rocketCountry(it.country)
                engineCount(it.engineCount)
                rocketName(it.rocketName)
                rocketImage(it.flickrImages.first())
                clickListener { _, _, _, position ->
                   itemClickCallback(rocketList[position])
                }
            }
        }
    }


}