package com.garcia.ignacio.kobweb3.data.window

import com.garcia.ignacio.kobweb3.data.datasource.Ethereum

external val window: dynamic
val ethereum: Ethereum = window.ethereum.unsafeCast<Ethereum>()