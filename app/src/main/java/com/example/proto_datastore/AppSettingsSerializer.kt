package com.example.proto_datastore

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream


object AppSettingsSerializer : Serializer<AppSettings>{
    override val defaultValue: AppSettings
        get() = TODO("Not yet implemented")

    override suspend fun readFrom(input: InputStream): AppSettings {
        TODO("Not yet implemented")
    }

    override suspend fun writeTo(t: AppSettings, output: OutputStream) {
        TODO("Not yet implemented")
    }


}