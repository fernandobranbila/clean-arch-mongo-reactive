package com.aegro.application.plot.entrypoint.resolver

import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class PlotResolver(

) : GraphQLQueryResolver, GraphQLMutationResolver {


    suspend fun findPartner(id: String) =
        PlotDto.fromDomain()

}
