package org.acme.inventory.service

import io.quarkus.grpc.GrpcService
import io.smallrye.mutiny.Uni
import org.acme.inventory.database.CarInventory
import org.acme.inventory.model.Car
import org.acme.inventory.model.CarResponse
import org.acme.inventory.model.InsertCarRequest
import org.acme.inventory.model.InventoryService
import org.acme.inventory.model.RemoveCarRequest

@GrpcService
class GrpcInventoryService(
    private val inventory: CarInventory,
) : InventoryService {
    override fun add(request: InsertCarRequest): Uni<CarResponse> =
        Uni
            .createFrom()
            .item(
                inventory.add(
                    Car(
                        licensePlateNumber = request.licensePlateNumber,
                        manufacturer = request.manufacturer,
                        model = request.model,
                    ),
                ),
            ).map {
                CarResponse
                    .newBuilder()
                    .setId(it.id!!)
                    .setLicensePlateNumber(it.licensePlateNumber)
                    .setManufacturer(it.manufacturer)
                    .setModel(it.model)
                    .build()
            }.log()

    override fun remove(request: RemoveCarRequest): Uni<CarResponse> =
        Uni
            .createFrom()
            .item(
                inventory.remove(request.licensePlateNumber),
            ).map {
                CarResponse
                    .newBuilder()
                    .setId(it.id!!)
                    .setLicensePlateNumber(it.licensePlateNumber)
                    .setManufacturer(it.manufacturer)
                    .setModel(it.model)
                    .build()
            }.log()
}
