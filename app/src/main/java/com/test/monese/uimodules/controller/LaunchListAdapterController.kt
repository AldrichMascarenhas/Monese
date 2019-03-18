package com.test.monese.uimodules.controller

import com.airbnb.epoxy.EpoxyController
import com.test.monese.uimodules.viewholder.launchViewItem
import com.test.presentation.model.Launch

class LaunchListAdapterController : EpoxyController() {

    private var launchList = listOf<Launch>()

    fun setContent(db: List<Launch>) {
        launchList = db
        requestModelBuild()
    }

    override fun buildModels() {

        launchList.forEach {
            launchViewItem {
                id(it.flightNumber)
                missionName(it.missionName)
                launchDateUnix(it.launchDateUTC)
                launchSuccess(it.launchSuccess)
                missionPatchImage(it.missionPatchImage)
            }
        }
    }

}