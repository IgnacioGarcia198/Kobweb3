package com.garcia.ignacio.kobweb3.data.repository

import com.garcia.ignacio.kobweb3.data.datasource.EthereumApi
import com.garcia.ignacio.kobweb3.data.datasource.Ethers
import com.garcia.ignacio.kobweb3.data.datasource.EthersApi
import kotlinx.coroutines.await

class Web3Repository(
    private val ethereumApi: EthereumApi,
    private val ethersApi: EthersApi,
    private val contractInitializer: ContractInitializer,
) {
    private var selectedAddress: String? = null
    private lateinit var testContract: Ethers.TestContract
    private lateinit var testContract2: Ethers.TestContract2

    suspend fun selectMetamaskAccount(): String? {
        selectedAddress = ethereumApi.requestAccounts()?.firstOrNull()
        return selectedAddress
    }

    suspend fun getContractCode(address: String): Result<String> {
        return kotlin.runCatching { ethersApi.jsonRpcProvider.getCode(address).await().unsafeCast<String>() }
    }

    fun initializeContracts(): Result<Unit> {
        return kotlin.runCatching {
            val contracts = contractInitializer.initializeContracts()
            testContract = contracts.testContract
            testContract2 = contracts.testContract2
        }
    }

    fun startListeningToEvents(block1: (newCount: Int) -> Unit, block2: (newCount: Int) -> Unit) {
        testContract.on("CounterIncreased") { newCount: Int ->
            block1(newCount)
        }
        testContract2.on("CounterIncreased") { newCount: Int ->
            block2(newCount)
        }
    }

    suspend fun incrementCounter(): Result<Ethers.Providers.TransactionReceipt> {
        return kotlin.runCatching {
            val transactionResponse = testContract.IncrementCounter().await()
            transactionResponse.wait().await()
        }
    }

    suspend fun incrementCounter2(): Result<Ethers.Providers.TransactionReceipt> {
        return kotlin.runCatching {
            val transactionResponse = testContract2.IncrementCounter().await()
            transactionResponse.wait().await()
        }
    }

    suspend fun getCounter1(): Result<Int> {
        return kotlin.runCatching { testContract.GetCounter().await() }
    }

    suspend fun getCounter2(): Result<Int> {
        return kotlin.runCatching { testContract2.GetCounter().await() }
    }

}