package scratchpad;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClientFactory;

import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: Sai
 * Date: 29/01/12
 * Time: 09:28
 * To change this template use File | Settings | File Templates.
 */
public class JiraRestClient1 {

    public static void main(String[] args) throws Exception {

        final JerseyJiraRestClientFactory factory = new JerseyJiraRestClientFactory();
        final URI jiraServerUri = new URI("http://57.5.179.12");
        final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, "skrishnamurthy", "Hobart2$");
        final NullProgressMonitor pm = new NullProgressMonitor();
        final Issue issue = restClient.getIssueClient().getIssue("QAT-1724", pm);

        System.out.println(issue.getReporter().getName());
        System.out.println(issue.getAssignee().getName());
        System.out.println(issue.getStatus().getName());
        System.out.println(issue.getSummary());




    }
}
