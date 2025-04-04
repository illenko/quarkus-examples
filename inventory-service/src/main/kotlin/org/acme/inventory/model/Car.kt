package org.acme.inventory.model

import jakarta.json.bind.annotation.JsonbCreator
import jakarta.json.bind.annotation.JsonbProperty

data class Car @JsonbCreator constructor(
    @JsonbProperty("licensePlateNumber") val licensePlateNumber: String,
    @JsonbProperty("manufacturer") val manufacturer: String,
    @JsonbProperty("model") val model: String,
) {
    var id: Long? = null
}