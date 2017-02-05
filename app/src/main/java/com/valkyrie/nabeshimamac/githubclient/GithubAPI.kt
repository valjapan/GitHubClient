package com.valkyrie.nabeshimamac.githubclient

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by NabeshimaMAC on 2017/01/08.
 */
interface GithubAPI {
    @GET("/repositories?sort=stars&order=desc")
    fun newRepositories(): Observable<List<Repositories>>

    @GET("/repos/{owner}/{repo}/readme")
    fun getReadMe(@Path("owner") owner: String, @Path("repo") repo: String): Observable<ReadMe>
}