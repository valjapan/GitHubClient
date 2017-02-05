package com.valkyrie.nabeshimamac.githubclient

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * Created by NabeshimaMAC on 2017/01/08.
 */
interface GithubAPI {
    @GET("/repositories?sort=stars&order=desc")
    fun newRepositories(): Observable<List<Repositories>>

    @GET("/repos/{owner}/{repo}/readme")
    fun getReadMe(@Path("owner") owner: String, @Path("repo") repo: String): Observable<ReadMe>

    @GET("https://api.github.com/search/repositories?q={search_word}&sort=stars&order=desc")
    fun searchRepositories(@Query("search_word") searchWord: String): Observable<Repositories>
}