package scratchpad;

import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Sai
 * Date: 27/01/12
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 */
public class Svn1 {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub

        SVNRepositoryFactoryImpl.setup();
        DAVRepositoryFactory.setup();

        //SVNURL url = SVNURL.parseURIDecoded("svn://24.25.0.2/trunk/NewReportCenter");
        SVNURL url = SVNURL.parseURIDecoded("svn://24.25.0.2/trunk/NewReportCenter");
        SVNRepository repository = SVNRepositoryFactory.create(url, null);
        System.out.println(repository);

        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager("Sai", "sai123");
        repository.setAuthenticationManager(authManager);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 18);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 2012);


        long from = repository.getDatedRevision(cal.getTime());
        long to = repository.getDatedRevision(new Date());

        System.out.println("--------- FROM -- TO " + from + " - " + to);


        System.out.println("-------" + to);

        Collection logEntries = repository.log(new String[]{""}, null, from, to, true, true);
        System.out.println(logEntries.size());

        for (Iterator entries = logEntries.iterator(); entries.hasNext(); ) {
            SVNLogEntry logEntry = (SVNLogEntry) entries.next();
            System.out.println("---------------------------------------------");
            System.out.println("revision: " + logEntry.getRevision());
            System.out.println("author: " + logEntry.getAuthor());
            System.out.println("date: " + logEntry.getDate());
            System.out.println("log message: " + logEntry.getMessage());

            if (logEntry.getChangedPaths().size() > 0) {
                System.out.println();
                System.out.println("changed paths:");
                Set changedPathsSet = logEntry.getChangedPaths().keySet();

                for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths.hasNext(); ) {
                    SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry.getChangedPaths().get(changedPaths.next());
                    System.out.println(" "
                            + entryPath.getType()
                            + " "
                            + entryPath.getPath()
                            + ((entryPath.getCopyPath() != null) ? " (from "
                            + entryPath.getCopyPath() + " revision "
                            + entryPath.getCopyRevision() + ")" : ""));
                }
            }
        }
    }
}
