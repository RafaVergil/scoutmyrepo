package br.com.rafaelverginelli.scoutmyrepo

import abstractions.CustomAppCompatActivity
import adapters.RepoListAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import models.RepoModel
import requests.IRepoRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import utils.RetrofitClientInstance
import utils.Utils

class MainActivity : CustomAppCompatActivity() {

    val REPOS_TO_LOAD = 20
    val STARTING_PAGE = 1
    var currentPage = STARTING_PAGE

    var reposAdapter: RepoListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadRepos(currentPage)
    }

    fun loadRepos(page: Int){

        val iRepoRequest: IRepoRequest =
            RetrofitClientInstance.retrofitInstance.create(IRepoRequest::class.java)


        val reposCall = iRepoRequest.getRepos(REPOS_TO_LOAD, page)

        reposCall.enqueue(object: Callback<List<RepoModel?>?>{
            override fun onFailure(call: Call<List<RepoModel?>?>, t: Throwable) {
                if(reposCall.isCanceled) return

                configureNoRepos(true)
                Utils.debugLog(TAG, "onFailure")
            }

            override fun onResponse(
                call: Call<List<RepoModel?>?>, response: Response<List<RepoModel?>?>) {
                if(reposCall.isCanceled) return

                if(response.body() != null) {
                    configureRepoList(response.body()!!.toMutableList())
                }
            }
        })

    }

    private fun configureRepoList(list: MutableList<RepoModel?>) {
        if(reposAdapter != null){
            reposAdapter!!.data.addAll(list)
            reposAdapter!!.notifyDataSetChanged()
        }
        else {
            reposAdapter = RepoListAdapter(list, this, object: RepoListAdapter.IRepoCallback {
                override fun onRepoClick(repo: RepoModel) {
                    displayRepoDetails(repo)
                }
            })

            reposAdapter?.recyclerViewCallback = object : RepoListAdapter.IRecyclerViewCallback{
                override fun onBottomReached(position: Int) {
                    currentPage++
                    loadRepos(currentPage)
                }
            }
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = reposAdapter
        }
    }

    private fun configureNoRepos(noRepos: Boolean) {
        txtNoRepos.visibility = (if(noRepos) View.VISIBLE else View.GONE)
        recyclerView.visibility = (if(noRepos) View.GONE else View.VISIBLE)
    }

    private fun displayRepoDetails(repo: RepoModel){
        val intent = Intent(this@MainActivity, RepoDetailsActivity::class.java)
        intent.putExtra(RepoDetailsActivity.REPO_DETAIL_KEY, repo)
        startActivity(intent)
    }

}
