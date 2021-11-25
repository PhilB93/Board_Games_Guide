package com.example.boardgamesguide.ui.details


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.RequestManager
import com.example.boardgamesguide.R
import com.example.boardgamesguide.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding: FragmentDetailsBinding by viewBinding()
    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var glide: RequestManager

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(args.game.url)
            setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                        if (this.canGoBack())
                            this.goBack()
                        else
                            requireActivity().onBackPressed()
                }
                return@setOnKeyListener true
            }
        }

        // setGameView()


    }
//    @SuppressLint("SetTextI18n")
//    private fun setGameView() {
//        with(binding)
//        {
//
//            val game = args.game
//            Glide.with(ivGamePic).load(game.image_url).apply(
//                RequestOptions()
//                    .placeholder(R.drawable.ic_search)
//                    .error(R.drawable.ic_search)
//                    .transform(RoundedCorners(ROUNDED_CORNERS))
//            ).into(ivGamePic)
//            tvPlayersInfo.text = "${game.min_players} - ${game.max_players}"
//            tvAgesInfo.text = "${game.min_age}+"
//            tvPlaytimeInfo.text = "${game.min_playtime} - ${game.max_playtime} mins"
//            tvPriceInfo.text = game.price_text
//            tvRatingInfo.text = String.format("%.3f", game.average_user_rating)
//            tvNameInfo.text = game.name
//            (if (game.description_preview.isNullOrEmpty())
//                game.description else game.description_preview)
//                .also { tvDescription.text =
//                it}
//        }
//
//    }

}