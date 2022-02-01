package setup

import com.fasterxml.jackson.databind.ObjectMapper

// docs at https://github.com/FasterXML/jackson-module-kotlin/blob/master/README.md
interface JacksonBuilder {
    val mapper: ObjectMapper
}

interface Builder<T> {
    fun build(): T
}