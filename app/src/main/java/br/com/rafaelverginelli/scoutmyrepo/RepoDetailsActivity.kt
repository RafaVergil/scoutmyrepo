package br.com.rafaelverginelli.scoutmyrepo

import abstractions.CustomAppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_repo_details.*
import models.RepoModel
import utils.Constants
import utils.Utils
import java.text.ParseException
import java.text.SimpleDateFormat


class RepoDetailsActivity : CustomAppCompatActivity() {

    companion object {
        const val REPO_DETAIL_KEY = "repo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)

        if(intent.hasExtra(REPO_DETAIL_KEY)){
            configureRepoDetails(intent.getSerializableExtra(REPO_DETAIL_KEY) as RepoModel)
        }
        else {
            Toast.makeText(this, R.string.error_loading_repo, Toast.LENGTH_LONG).show()
        }

    }

    private fun configureRepoDetails(repo: RepoModel){
        Glide.with(this)
            .load(repo.owner.avatar_url)
            .apply(
                RequestOptions()
                    .error(R.drawable.ic_profile)
                    .placeholder(R.drawable.ic_profile))
            .into(imgPortrait)

        txtRepoName.text = repo.name
        txtLanguage.text = repo.language

        val gitDateFormat = SimpleDateFormat(Constants.GIT_API_DATE_FORMAT)
        val appDateFormat = SimpleDateFormat(Constants.APP_DATE_FORMAT)
        var updatedDate = ""
        try {
            val date = gitDateFormat.parse(repo.updated_at)
            updatedDate = appDateFormat.format(date)
        } catch (e: ParseException) {
            Utils.debugLog(TAG, e)
        }


        txtLastUpdate.text = updatedDate
        txtWatcherCount.text = repo.watchers.toString()
        txtDescription.text = repo.description?: getString(R.string.no_description)
    }
}
