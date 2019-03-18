package com.test.monese.uimodules.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.test.monese.R
import com.test.monese.uimodules.helper.KotlinEpoxyHolder


@EpoxyModelClass(layout = R.layout.item_rocket_layout)
abstract class RocketViewItem : EpoxyModelWithHolder<RocketViewHolder>() {

    @EpoxyAttribute
    lateinit var rocketCountry: String
    @EpoxyAttribute
    var engineCount: Int = 0
    @EpoxyAttribute
    lateinit var rocketName: String
    @EpoxyAttribute
    lateinit var rocketImage: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    override fun bind(holder: RocketViewHolder) {

        holder.cardView.setOnClickListener(clickListener)


        if (rocketImage.isNotEmpty()) {
            Glide.with(holder.rocketImage.context)
                .load(rocketImage)
                .into(holder.rocketImage)
        }

        holder.rocketEngineCount.text = engineCount.toString()
        holder.rocketName.text = rocketName
        holder.rocketCountry.text = rocketCountry

    }
}

class RocketViewHolder : KotlinEpoxyHolder() {
    val rocketEngineCount by bind<TextView>(R.id.itemRocketLayoutTextViewEngineCount)
    val rocketName by bind<TextView>(R.id.itemRocketLayoutTextViewName)
    val rocketCountry by bind<TextView>(R.id.itemRocketLayoutTextViewCountry)
    val rocketImage by bind<ImageView>(R.id.itemRocketLayoutImageView)
    val cardView by bind<CardView>(R.id.itemRocketLayoutCardView)
}