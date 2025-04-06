package org.acme.inventory.client

import io.quarkus.grpc.GrpcClient
import io.quarkus.logging.Log
import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain
import org.acme.inventory.model.InsertCarRequest
import org.acme.inventory.model.InventoryService
import org.acme.inventory.model.RemoveCarRequest

@QuarkusMain
class InventoryCommand(
    @GrpcClient("inventory")
    private val inventoryService: InventoryService,
) : QuarkusApplication {
    private fun add(
        licensePlateNumber: String,
        manufacturer: String,
        model: String,
    ) {
        val request =
            InsertCarRequest
                .newBuilder()
                .setLicensePlateNumber(licensePlateNumber)
                .setManufacturer(manufacturer)
                .setModel(model)
                .build()

        val response = inventoryService.add(request).await().indefinitely()
        Log.info("Added car: $response")
    }

    private fun remove(licensePlateNumber: String) {
        val request =
            RemoveCarRequest
                .newBuilder()
                .setLicensePlateNumber(licensePlateNumber)
                .build()

        val response = inventoryService.remove(request).await().indefinitely()
        Log.info("Removed car: $response")
    }

    override fun run(vararg args: String): Int {
        if (args.isEmpty()) {
            Log.error("Please provide a command: add or remove")
            return 1
        }

        return when (val command = args[0]) {
            "add" -> {
                if (args.size != 4) {
                    Log.error("Usage: add <licensePlateNumber> <manufacturer> <model>")
                    1
                } else {
                    add(args[1], args[2], args[3])
                    0
                }
            }

            "remove" -> {
                if (args.size != 2) {
                    Log.error("Usage: remove <licensePlateNumber>")
                    1
                } else {
                    remove(args[1])
                    0
                }
            }

            else -> {
                Log.error("Unknown command: $command")
                1
            }
        }
    }
}
