package com.dicoding.picodiploma.hydros.view.detail

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.TextView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.hydros.R
import com.dicoding.picodiploma.hydros.databinding.ActivityAnalysisBinding
import com.dicoding.picodiploma.hydros.databinding.ActivityDetailBinding
import com.dicoding.picodiploma.hydros.view.main.MainActivity

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val tvDescription: TextView = findViewById(R.id.tv_description)
        val description = "<html><body style='text-align:justify'>" + getString(R.string.lorem) + "</body></html>"
        tvDescription.text = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val itemName = intent.getStringExtra(EXTRA_NAME)
        val itemStatus = intent.getStringExtra(EXTRA_STATUS)
        val itemDate = intent.getStringExtra(EXTRA_DATE)
        val itemDescription = intent.getStringExtra(EXTRA_DESCRIPTION)
        val itemPhotoUrl = intent.getStringExtra(EXTRA_PHOTOURL)

        binding.apply {
            tvName.text = itemName
            tvStatus.text = itemStatus
            tvDate.text = itemDate
//            tvDescription.text = itemDescription
            Glide.with(this@DetailActivity)
                .load(itemPhotoUrl)
                .into(imgPhoto)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        super.onBackPressed()
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_STATUS = "extra_status"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_PHOTOURL = "extra_photourl"
    }
}