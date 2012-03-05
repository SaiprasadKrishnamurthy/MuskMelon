package org.sai.muskmelon.orchestration

import org.sai.muskmelon.domain.Orchestrator
import org.sai.muskmelon.domain.QueryContext
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.sai.muskmelon.domain.Visitable
import org.sai.muskmelon.domain.Connector
import org.sai.muskmelon.domain.Repository

/**
 * Created by IntelliJ IDEA.
 * User: Sai
 * Date: 05/03/12
 * Time: 00:17
 * To change this template use File | Settings | File Templates.
 */
@Component
class OrchestratorImpl implements Orchestrator {

    final List<Connector> connectors = []
    final Repository repository

    @Autowired
    OrchestratorImpl(final List<Connector> connectors, final Repository repository) {
        this.connectors = connectors
        this.repository = repository
    }

    void orchestrateFullImport(final QueryContext context) {
        connectors.each {
            def visitable =  it.getAll(context)
            repository.save(visitable)
        }
    }

}
