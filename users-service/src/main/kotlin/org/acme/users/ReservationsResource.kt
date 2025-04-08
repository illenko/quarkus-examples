package org.acme.users

import io.quarkus.qute.CheckedTemplate
import io.quarkus.qute.TemplateInstance
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.SecurityContext
import org.jboss.resteasy.reactive.RestQuery
import java.time.LocalDate

@Path("/")
class ReservationsResource(
    private val client: ReservationsClient,
    private val securityContext: SecurityContext,
) {
    @CheckedTemplate
    object Templates {
        external fun index(
            startDate: LocalDate?,
            endDate: LocalDate?,
            name: String?,
        ): TemplateInstance
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    fun index(
        @RestQuery startDate: LocalDate? = LocalDate.now().plusDays(1L),
        @RestQuery endDate: LocalDate? = LocalDate.now().plusDays(7),
    ): TemplateInstance =
        Templates.index(
            startDate,
            endDate,
            securityContext.userPrincipal.name,
        )
}
