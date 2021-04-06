package com.aegro.application.config;

import graphql.GraphQLError
import graphql.kickstart.execution.error.GraphQLErrorHandler
import org.springframework.stereotype.Component

@Component
class CustomGraphqlErrorHandler : GraphQLErrorHandler {

    override fun processErrors(errors: MutableList<GraphQLError>): MutableList<GraphQLError> {
        return errors
    }


}




