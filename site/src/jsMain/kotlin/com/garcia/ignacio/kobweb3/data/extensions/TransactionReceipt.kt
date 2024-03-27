package com.garcia.ignacio.kobweb3.data.extensions

import com.garcia.ignacio.kobweb3.data.datasource.Ethers

fun Ethers.Providers.TransactionReceipt.getDataAsString(): String {
    val receipt = this
    return "receipt: " +
            "\ntransaction hash: ${receipt.transactionHash}" +
            "\ntransaction index: ${receipt.transactionIndex}" +
            "\nblock number: ${receipt.blockNumber}" +
            "\nblock hash: ${receipt.blockHash}" +
            "\ncontract address: ${receipt.contractAddress}" +
            "\nconfirmations: ${receipt.confirmations}" +
            "\nfrom: ${receipt.from}" +
            "\nto: ${receipt.to}" +
            "\nstatus: ${receipt.status}" +
            "\ntype: ${receipt.type}" +
            "\nroot: ${receipt.root}" +
            "\ngas used: ${receipt.gasUsed}" +
            "\nlogs bloom: ${receipt.logsBloom}" +
            "\nlogs: ${receipt.logs.map { it.logIndex }}"
}