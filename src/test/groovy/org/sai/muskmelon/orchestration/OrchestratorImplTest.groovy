package org.sai.muskmelon.orchestration;


import org.sai.muskmelon.domain.Connector
import org.sai.muskmelon.domain.QueryContext
import org.sai.muskmelon.domain.Repository
import org.sai.muskmelon.entity.Changelist
import org.sai.muskmelon.entity.ChangelistType
import org.sai.muskmelon.entity.Commit
import org.sai.muskmelon.entity.Document
import spock.lang.Specification

/**
 * Created by IntelliJ IDEA.
 * User: Sai
 * Date: 05/03/12
 * Time: 00:18
 * To change this template use File | Settings | File Templates.
 */
public class OrchestratorImplTest extends Specification {

    def "Orchestrate between subversion connector and jira connector for a full import"() {

        given:
        Connector svnConnector = Mock()
        Connector jiraConnector = Mock()
        Repository repository = Mock()
        def startDate = new Date()
        def endDate = new Date()
        def queryContext = new QueryContext(startDate: startDate, endDate: endDate)
        def changeList = new Changelist(id: 1L, changelistType: ChangelistType.ADDED, resourcePath: "/home/resourcepath", resourceUrl: "http://svnurl/path")
        def commit = new Commit(id: 1L, author: "Sai", changeList: [changeList], LastModified: new Date(), message: "My message", revision: 11L, documentIdentifier: "DOCID")
        def document = new Document(id: 1L, author: "Sai", created: new Date(), description: "Description", identifier: "DOCID", commits: [commit])
        def orchestrator = new OrchestratorImpl([svnConnector, jiraConnector], repository)

        when:
        orchestrator.orchestrateFullImport(queryContext)

        then:
        1 * svnConnector.getAll(queryContext) >> [commit]
        1 * repository.save([commit])
        1 * jiraConnector.getAll(queryContext) >> [document]
        1 * repository.save([document])
    }
}
