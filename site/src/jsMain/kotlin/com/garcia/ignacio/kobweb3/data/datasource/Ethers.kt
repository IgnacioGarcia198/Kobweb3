
package com.garcia.ignacio.kobweb3.data.datasource

import kotlinx.coroutines.await
import kotlin.js.Promise


@JsModule("ethers")
@JsNonModule
@JsName("ethers")
external object Ethers {
    @JsName("providers")
    object Providers {
        open class JsonRpcProvider(url: String) {
            open fun getBalance(address: String): Promise<dynamic>

            open fun getCode(address: String): Promise<dynamic>
            open fun listAccounts(): Promise<dynamic>
        }
        open class Web3Provider(ethereum: Ethereum) {
            open fun getSigner(index: Int): dynamic
            open fun getCode(address: String): Promise<dynamic>
            open fun listAccounts(): Promise<dynamic>
        }

        open class TransactionResponse {
            open fun wait(): Promise<TransactionReceipt>
        }

        open class Log {
            open val blockNumber: Int
            open val blockHash: String
            open val removed: Boolean
            open val address: String
            open val data: String
            open val topics: Array<String>
            open val transactionHash: String
            open val transactionIndex: Int
            open val logIndex: Int
        }

        open class TransactionReceipt {
            open val to: String
            open val from: String
            open val contractAddress: String
            open val transactionIndex: String
            open val type: Int
            open val root: String
            open val gasUsed: String // BigNumber
            open val effectiveGasPrice: String // BigNumber
            open val logsBloom: String
            open val blockHash: String
            open val transactionHash: String
            open val logs: Array<Log>
            open val blockNumber: Int
            open val confirmations: Int
            open val cumulativeGasUsed: String // BigNumber
            open val byzantium: Boolean
            open val status: Int
        }
    }

    open class Contract(address: String, abi: String, signerOrProvider: dynamic) {
       open fun <T> on(event: String, block: (T) -> Unit)
    }

    /**
     * A class like this one is needed per each contract.
     * Then it can be instantiated with its address, abi and selected provider.
     */
    @JsName("Contract")
    open class TestContract(address: String, abi: String, signerOrProvider: dynamic): Contract {
        open fun GetCounter(): Promise<Int>
        open fun IncrementCounter(): Promise<Providers.TransactionResponse>
    }

    /**
     * Example of a second contract.
     */
    @JsName("Contract")
    open class TestContract2(address: String, abi: String, signerOrProvider: dynamic): Contract {
        open fun GetCounter(): Promise<Int>
        open fun IncrementCounter(): Promise<Providers.TransactionResponse>
    }
}

class EthersApi(
    ethereum: Ethereum,
    private val rpcUrl: String = "http://127.0.0.1:8545",
    val jsonRpcProvider: Ethers.Providers.JsonRpcProvider = Ethers.Providers.JsonRpcProvider(rpcUrl),
    val web3Provider: Ethers.Providers.Web3Provider = Ethers.Providers.Web3Provider(ethereum),
) {
    suspend fun listHardHatAccounts(): Array<String>? = jsonRpcProvider.listAccounts().await().unsafeCast<Array<String>?>()
    suspend fun listAccounts(): Array<String>? = web3Provider.listAccounts().await().unsafeCast<Array<String>?>()
    suspend fun getCode(address: String): dynamic = web3Provider.getCode(address).await()
}
