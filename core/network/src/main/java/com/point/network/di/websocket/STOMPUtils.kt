package com.point.network.di.websocket

object STOMPUtils {

    fun connect() = """
            CONNECT
            accept-version:1.1,1.2
            heart-beat:10000,10000
            
            ${"\u0000"}
        """.trimIndent()

    fun subscribe(destination: String, id: String) = """
        SUBSCRIBE
        id:sub-chat-$id
        destination:$destination
        
        ${"\u0000"}
        """.trimIndent()

    fun send(destination: String, json: String) = """
        SEND
        destination:$destination
        
        $json
        ${"\u0000"}
        """.trimIndent()
}