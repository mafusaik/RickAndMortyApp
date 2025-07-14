package com.mafusaik.core.network

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkClient @Inject constructor(
    private val retrofit: Retrofit
) {
    fun <T> create(service: Class<T>): T = retrofit.create(service)
}
