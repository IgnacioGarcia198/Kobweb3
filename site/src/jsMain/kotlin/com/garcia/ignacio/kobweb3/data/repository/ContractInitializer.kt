package com.garcia.ignacio.kobweb3.data.repository

import com.garcia.ignacio.kobweb3.data.datasource.Ethers
import com.garcia.ignacio.kobweb3.data.datasource.EthersApi

class ContractInitializer(private val ethersApi: EthersApi) {
    fun initializeContracts(): Contracts {
        val contract = Ethers.TestContract(
            address = TEST_CONTRACT_ADDRESS,
            abi = TEST_CONTRACT_ABI,
            signerOrProvider = ethersApi.web3Provider.getSigner(0)
        )
        val contract2 = Ethers.TestContract2(
            address = TEST_CONTRACT_ADDRESS,
            abi = TEST_CONTRACT_ABI,
            signerOrProvider = ethersApi.web3Provider.getSigner(0)
        )
        return Contracts(contract, contract2)
    }
}

data class Contracts(
    val testContract: Ethers.TestContract,
    val testContract2: Ethers.TestContract2,
)