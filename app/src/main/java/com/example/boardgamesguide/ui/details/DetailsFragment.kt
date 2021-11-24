package com.example.boardgamesguide.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.boardgamesguide.R
import com.example.boardgamesguide.databinding.FragmentDetailsBinding
import com.example.boardgamesguide.util.Constants.Companion.ROUNDED_CORNERS
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding: FragmentDetailsBinding by viewBinding()
    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var glide: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setGameView()
    }

    private fun setGameView() {
        with(binding)
        {
            val game = args.game
            Glide.with(ivGame).load(game.image_url).apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_search)
                    .error(R.drawable.ic_search)
                    .transform(RoundedCorners(ROUNDED_CORNERS))
            ).into(ivGame)
            tvTitle.text = game.name
            tvRating.text = String.format("%.3f", game.average_user_rating)
        }
    }
}