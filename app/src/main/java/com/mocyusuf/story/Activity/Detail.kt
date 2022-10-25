package com.mocyusuf.story.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mocyusuf.story.R
import com.mocyusuf.story.Remote.Model.Home.ListStory
import com.mocyusuf.story.databinding.ActivityDetailBinding

@Suppress("DEPRECATION")
class Detail : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fetchData()
    }
    private fun fetchData() {
        val i = intent.getParcelableExtra<ListStory>(EXTRA_ITEM)
        binding.apply {
            tvName.text = i?.name
            tvDescription.text = i?.description
            Glide.with(this@Detail)
                .load(i?.photoUrl)
                .into(imgPhotos)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_ITEM = "extra_item"
    }
}