package com.garcia.ignacio.kobweb3.data.datasource

import kotlinx.coroutines.await
import kotlin.js.Promise

const val ETH_REQUEST_ACCOUNTS = "{ method: 'eth_requestAccounts' }"

external interface Ethereum {
    fun request(params: dynamic): Promise<dynamic>
}

class EthereumApi(private val ethereum: Ethereum) {
    suspend fun requestAccounts(): Array<String>? =
        ethereum.request(js(ETH_REQUEST_ACCOUNTS)).await().unsafeCast<Array<String>?>()
}