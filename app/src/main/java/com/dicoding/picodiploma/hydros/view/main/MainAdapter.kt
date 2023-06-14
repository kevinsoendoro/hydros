package com.dicoding.picodiploma.hydros.view.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.hydros.R
import com.dicoding.picodiploma.hydros.model.DataPlants
import com.dicoding.picodiploma.hydros.view.detail.DetailActivity
import java.util.ArrayList

class MainAdapter(private val plantList: ArrayList<DataPlants>) : RecyclerView.Adapter<MainAdapter.PlantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plantList[position]
        holder.bind(plant)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_NAME, plant.plant)
            intent.putExtra(DetailActivity.EXTRA_STATUS, plant.status)
            intent.putExtra(DetailActivity.EXTRA_DATE, plant.date)
            intent.putExtra(DetailActivity.EXTRA_DESCRIPTION, plant.description)
            intent.putExtra(DetailActivity.EXTRA_PHOTOURL, plant.photoUrl)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgPhoto: ImageView = itemView.findViewById(R.id.img_photo)
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)

        fun bind(plant: DataPlants) {
            Glide.with(itemView)
                .load(plant.photoUrl)
                .into(imgPhoto)
            tvName.text = plant.plant
            tvStatus.text = if (plant.status) itemView.context.getString(R.string.status_ok) else itemView.context.getString(R.string.status_no)
            tvDate.text = plant.date
        }
    }
}
