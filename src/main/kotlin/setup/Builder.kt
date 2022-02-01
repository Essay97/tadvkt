package setup

import com.fasterxml.jackson.databind.ObjectMapper



interface JacksonBuilder {
    val mapper: ObjectMapper
    val fileName: String
}

interface Builder<T> {
    fun build(): T
}