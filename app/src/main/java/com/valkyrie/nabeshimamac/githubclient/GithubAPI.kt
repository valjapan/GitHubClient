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
    fun newRepositories(): Observable<List<Repository>>

    @GET("/repos/{owner}/{repo}/readme")
    fun getReadMe(@Path("owner") owner: String, @Path("repo") repo: String): Observable<ReadMe>

    //@GET("/search/repositories?order=desc")
    @GET("/search/repositories?order=desc")
    fun searchRepositories(@Query("q") searchWord: String, @Query("sort") sort: String): Observable<Repositories>
}