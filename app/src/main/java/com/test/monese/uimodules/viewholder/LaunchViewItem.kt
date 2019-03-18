package com.test.monese.uimodules.viewholder

import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.test.monese.R
import com.test.monese.uimodules.helper.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.item_launch_layout)
abstract class LaunchViewItem : EpoxyModelWithHolder<LaunchViewHolder>() {

    @EpoxyAttribute
    lateinit var missionName: String
    @EpoxyAttribute
    lateinit var launchDateUnix: String
    @EpoxyAttribute
    var launchSuccess: Boolean = false
    @EpoxyAttribute
    lateinit var missionPatchImage: String

    override fun bind(holder: LaunchViewHolder) {

        if (missionPatchImage.isNotEmpty()) {
            Glide.with(holder.missionPatchImage.context)
                .load(missionPatchImage)
                .into(holder.missionPatchImage)
        }

        holder.missionName.text = missionName
        holder.missionDate.text = launchDateUnix.toString()
        holder.missionSuccess.text = if (launchSuccess) "Successful Launch" else "Failed Launch"
    }
}


class LaunchViewHolder : KotlinEpoxyHolder() {
    val missionName by bind<TextView>(R.id.itemLaunchLayoutTextViewMissionName)
    val missionDate by bind<TextView>(R.id.itemLaunchLayoutTextViewMissionDate)
    val missionSuccess by bind<TextView>(R.id.itemLaunchLayoutTextViewMissionSuccess)
    val missionPatchImage by bind<ImageView>(R.id.itemLaunchLayoutImageViewMissionImage)
}