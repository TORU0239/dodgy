package dodgy.com.dodgydetector.init.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dodgy.com.dodgydetector.R
import dodgy.com.dodgydetector.init.model.InstagramMediaModel
import kotlinx.android.synthetic.main.adapter_init.view.*

/**
 * Created by toruchoi on 18/11/2017.
 */
class MediaAdapter(val dataList:List<InstagramMediaModel>): RecyclerView.Adapter<MediaViewHolder>() {
    override fun onBindViewHolder(holder: MediaViewHolder?, position: Int) {
        holder?.bindData(dataList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MediaViewHolder {
        return MediaViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.adapter_init, parent, false))
    }

    override fun getItemCount(): Int = dataList.size
}

class MediaViewHolder(view: View):RecyclerView.ViewHolder(view){
    fun bindData(model:InstagramMediaModel){
        itemView.img_caption
    }
}