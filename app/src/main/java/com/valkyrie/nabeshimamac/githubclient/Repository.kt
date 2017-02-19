package com.valkyrie.nabeshimamac.githubclient

import java.io.Serializable

data class Repository(val id: Int,
                      val name: String,
                      val description: String,
                      val full_name: String,
                      val owner: Owner) : Serializable
