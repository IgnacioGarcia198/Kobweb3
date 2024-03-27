package com.garcia.ignacio.kobweb3.presentation.viewmodel

import com.garcia.ignacio.kobweb3.data.repository.TEST_CONTRACT_ADDRESS
import com.garcia.ignacio.kobweb3.data.repository.Web3Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CounterViewModel(
    private val repository: Web3Repository,
    val testContractFlow: MutableStateFlow<String> = MutableStateFlow("Nothing yet..."),
    val testContract2Flow: MutableStateFlow<String> = MutableStateFlow("Nothing yet..."),
    private val uiScope: CoroutineScope = CoroutineScope(Dispatchers.Main),
    private val workScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
) {
    init {
        initialize()
    }
    fun selectMetamaskAccount() {
        workScope.launch {
            repository.selectMetamaskAccount().also {
                testContractFlow.value = "Selected account: $it"
            }
        }
    }

    fun getCode() {
        workScope.launch {
            repository.getContractCode(TEST_CONTRACT_ADDRESS)
                .onFailure { testContractFlow.value = it.stackTraceToString() }
                .onSuccess { testContractFlow.value = it }
        }
    }

    fun incrementCounter() {
        workScope.launch {
            repository.incrementCounter().onFailure {
                testContractFlow.value = it.stackTraceToString()
            }.onSuccess {
                // use transaction receipt
                testContractFlow.value = "increment counter 1: status: ${it.status}\nLogs: ${it.logs.joinToString("\n\n")}"
            }
        }
    }

    fun incrementCounter2() {
        workScope.launch {
            repository.incrementCounter2().onFailure {
                testContract2Flow.value = it.stackTraceToString()
            }.onSuccess {
                // use transaction receipt
                testContract2Flow.value = "increment counter 2: status: ${it.status}"
            }
        }
    }

    fun getCounter() {
        workScope.launch {
            repository.getCounter1().onFailure {
                testContractFlow.value = it.stackTraceToString()
            }.onSuccess {
                testContractFlow.value = "Get counter 1: $it"
            }
        }
    }

    fun getCounter2() {
        workScope.launch {
            repository.getCounter2().onFailure {
                testContract2Flow.value = it.stackTraceToString()
            }.onSuccess {
                testContract2Flow.value = "Get counter 2: $it"
            }
        }
    }

    private fun initialize() {
        workScope.launch {
            repository.initializeContracts().onFailure {
                testContractFlow.value = it.stackTraceToString()
            }
            repository.startListeningToEvents(
                block1 = { testContractFlow.value = "Count from event: $it" },
                block2 = { testContract2Flow.value = "Count from event: $it" }
            )
        }
    }
}
