package org.acme.users

import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.SecurityContext

@Path("/whoami")
class WhoAmIResource(
    private val whoami: Template,
    private val securityContext: SecurityContext,
) {
    @GET
    @Produces(MediaType.TEXT_HTML)
    fun get(): TemplateInstance = whoami.data("name", securityContext.userPrincipal?.name)
}
