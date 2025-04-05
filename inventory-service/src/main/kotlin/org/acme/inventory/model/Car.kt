package org.acme.inventory.model

import jakarta.json.bind.annotation.JsonbCreator
import jakarta.json.bind.annotation.JsonbProperty

data class Car
    @JsonbCreator
    constructor(
        @JsonbProperty val licensePlateNumber: String,
        @JsonbProperty val manufacturer: String,
        @JsonbProperty val model: String,
    ) {
        var id: Long? = null

        constructor(
            id: Long,
            licensePlateNumber: String,
            manufacturer: String,
            model: String,
        ) : this(licensePlateNumber, manufacturer, model) {
            this.id = id
        }
    }
