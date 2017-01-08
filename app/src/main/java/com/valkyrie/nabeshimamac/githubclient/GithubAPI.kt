package com.valkyrie.nabeshimamac.githubclient

import retrofit2.http.GET
import rx.Observable

/**
 * Created by NabeshimaMAC on 2017/01/08.
 */
interface GithubAPI {
    @GET("/repositories?sort=stars&order=desc")
    fun newRepositories(): Observable<List<Repositories>>
}