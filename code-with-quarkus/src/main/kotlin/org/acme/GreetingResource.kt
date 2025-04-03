package org.acme

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.SecurityContext

@Path("/hello")
class GreetingResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "Hello from Quarkus REST"

    @GET
    @Path("/whoami")
    @Produces(MediaType.TEXT_PLAIN)
    fun whoAmI(
        @Context securityContext: SecurityContext,
    ): String = securityContext.userPrincipal?.name ?: "anonymous"
}
