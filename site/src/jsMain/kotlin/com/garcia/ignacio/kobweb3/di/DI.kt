package com.garcia.ignacio.kobweb3.di

import com.garcia.ignacio.kobweb3.data.datasource.EthereumApi
import com.garcia.ignacio.kobweb3.data.datasource.EthersApi
import com.garcia.ignacio.kobweb3.data.repository.ContractInitializer
import com.garcia.ignacio.kobweb3.data.repository.Web3Repository
import com.garcia.ignacio.kobweb3.data.window.ethereum
import com.garcia.ignacio.kobweb3.presentation.viewmodel.CounterViewModel

object DI {
    private val ethereumApi = EthereumApi(ethereum)
    private val ethersApi = EthersApi(ethereum)
    private val contractInitializer = ContractInitializer(ethersApi)
    private val web3Repository : Web3Repository = Web3Repository(
        ethereumApi,
        ethersApi,
        contractInitializer
    )
    fun getCounterViewModel(): CounterViewModel = CounterViewModel(
        repository = web3Repository
    )
}