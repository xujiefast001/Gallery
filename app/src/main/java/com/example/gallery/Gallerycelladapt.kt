package com.example.gallery

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.cell_layout.view.*


class Gallerycelladapt:ListAdapter<PhotoItem,MyViewHolder> (DIFFCALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val holder=MyViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_layout,parent,false)
        )
        holder.itemView.setOnClickListener {
            Bundle().apply {
                putParcelable("PHOTO",getItem(holder.adapterPosition)) //以PHOTO为关键字，将item存到bundle中
                holder.itemView.findNavController().navigate(R.id.action_galleryFragment_to_photoFragment,this)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.ShimmerLayout.apply {
            setShimmerColor(0x55FFFFFF) //闪动的颜色
            setShimmerAngle(0) //闪动的角度
            startShimmerAnimation() //开始闪动
        }
        Glide.with(holder.itemView)
            .load(getItem(position).previewUrl)
            .placeholder(R.drawable.ic_photo_black_24dp)
            .listener(object:RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false.also { holder.itemView.ShimmerLayout?.stopShimmerAnimation() }
                    /*加问号是因为在加载完成之前如果切换页面，会遇到空指针错误*/
                }

            })
            .into(holder.itemView.imageView)
    }
    //静态类
    object DIFFCALLBACK: DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem ===newItem
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.photoID ==newItem.photoID
        }
    }

}
class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)