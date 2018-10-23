package adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import br.com.rafaelverginelli.scoutmyrepo.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import models.RepoModel


class RepoListAdapter(val data: MutableList<RepoModel?>, val context: Activity,
                      private val callback: IRepoCallback) :
    RecyclerView.Adapter<MediaHolder>() {

    private var lastPosition = -1

    interface IRepoCallback {
        fun onRepoClick(repo: RepoModel)
    }

    interface IRecyclerViewCallback {
        fun onBottomReached(position: Int)
    }

    var recyclerViewCallback: IRecyclerViewCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MediaHolder {
        return MediaHolder(LayoutInflater.from(context)
            .inflate(R.layout.repo_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MediaHolder, position: Int) {

        Glide.with(context)
            .load(if(data[position] != null) data[position]!!.owner.avatar_url else "")
            .apply(RequestOptions()
                .error(R.drawable.ic_profile)
                .placeholder(R.drawable.ic_profile))
            .into(holder.imgPortrait)

        holder.cardView.setOnClickListener {
            if(data[position] != null){ callback.onRepoClick(data[position]!!) }
        }

        holder.txtRepoName.text = data[position]?.name
        holder.txtLanguage.text = data[position]?.language
        holder.txtWatcherCount.text = data[position]?.watchers.toString()

        setAnimation(holder.itemView, position)

        if (position == data.size - 1){
            recyclerViewCallback?.onBottomReached(position)
        }

    }

    override fun onViewDetachedFromWindow(holder: MediaHolder) {
        holder.cardView.clearAnimation()
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}

class MediaHolder(view: View) : RecyclerView.ViewHolder(view) {

    val cardView: CardView = view.findViewById(R.id.cardView) as CardView
    val imgPortrait: ImageView = view.findViewById(R.id.imgPortrait) as ImageView
    val txtRepoName: TextView = view.findViewById(R.id.txtRepoName) as TextView
    val txtLanguage: TextView = view.findViewById(R.id.txtLanguage) as TextView
    val txtWatcherCount: TextView = view.findViewById(R.id.txtWatcherCount) as TextView

}