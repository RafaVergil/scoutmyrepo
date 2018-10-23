package requests

import models.RepoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import utils.Constants

interface IRepoRequest {

    @Headers(
        "Accept: application/json",
        "Accept-Language: en",
        "User-Agent: ${Constants.USER_AGENT}"
    )
    @GET(Constants.GIT_API_GET_GOOGLE_REPOS)
    fun getRepos(
        @Query(Constants.GIT_API_KEY_PER_PAGE) perPage: Int,
        @Query(Constants.GIT_API_KEY_PAGE) page: Int
    ): Call<List<RepoModel?>?>

}