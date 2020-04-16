package com.example.gallery

import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 * A simple [Fragment] subclass.
 */
class GalleryFragment : Fragment() {
    private lateinit var galleryViewmodel:GalleryViewmodel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }
    //添加菜单动作
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.swipelndicator->{
                SwipeRefreshLayout.isRefreshing=true
                Handler().postDelayed({galleryViewmodel.fetchData()},1000)//设置延迟，至少转动1s
                }
        }
        return super.onOptionsItemSelected(item)
    }
    //添加菜单
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.gallery_menu,menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)//加入菜单键
        val gallerycelladapt=Gallerycelladapt()
        recycleView.apply {
            adapter=gallerycelladapt
            layoutManager=GridLayoutManager(requireContext(),2)

        }
        galleryViewmodel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(GalleryViewmodel::class.java)
        galleryViewmodel.photolistlive.observe(this.viewLifecycleOwner, Observer {
            gallerycelladapt.submitList(it)
            SwipeRefreshLayout.isRefreshing=false
        })
        galleryViewmodel.photolistlive.value?:galleryViewmodel.fetchData()
        SwipeRefreshLayout.setOnRefreshListener {
            galleryViewmodel.fetchData()
        }
    }

}
