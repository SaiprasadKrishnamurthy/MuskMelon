//package org.sai.muskmelon.service;
//
//import org.sai.muskmelon.entity.Changelist;
//import org.sai.muskmelon.entity.ChangelistType;
//import org.sai.muskmelon.entity.Commit;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.tmatesoft.svn.core.SVNException;
//import org.tmatesoft.svn.core.SVNLogEntry;
//import org.tmatesoft.svn.core.SVNLogEntryPath;
//import org.tmatesoft.svn.core.SVNURL;
//import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
//import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
//import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
//import org.tmatesoft.svn.core.io.SVNRepository;
//import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
//import org.tmatesoft.svn.core.wc.SVNWCUtil;
//
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by IntelliJ IDEA. User: Sai Date: 28/01/12 Time: 23:48 To change this
// * template use File | Settings | File Templates.
// */
//@Qualifier("subversionService")
//@Service
//public class SubversionService implements org.sai.muskmelon.domain.Service {
//
//    private final String svnUsername;
//    private final String svnPassword;
//    private final SourceRepository sourceRepository;
//    private final org.sai.muskmelon.domain.Service jiraService;
//    private final static Pattern pattern = Pattern.compile("(?i)\\s*(QAT\\s*-\\s*[0-9]*).*");
//
//    @Autowired
//    public SubversionService(@Value("skrishnamurthy") final String svnUsername, @Value("63133604") final String svnPassword, final SourceRepository sourceRepository,
//	    @Qualifier("jiraService") final org.sai.muskmelon.domain.Service jiraService) {
//	this.svnUsername = svnUsername;
//	this.svnPassword = svnPassword;
//	this.sourceRepository = sourceRepository;
//	this.jiraService = jiraService;
//    }
//
//    public void load(final ServiceRequest request) {
//	try {
//	    SVNRepositoryFactoryImpl.setup();
//	    DAVRepositoryFactory.setup();
//	    List<Changeset> changesetListMAster = new ArrayList<Changeset>();
//
//	    for (String url : request.getSourceRepoUrls()) {
//		SVNURL svnUrl = SVNURL.parseURIEncoded(url);
//		SVNRepository repository = SVNRepositoryFactory.create(svnUrl, null);
//		// System.out.println(repository);
//
//		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(svnUsername, svnPassword);
//		repository.setAuthenticationManager(authManager);
//
//		long from = repository.getDatedRevision(request.getFromDate());
//		long to = repository.getDatedRevision(request.getToDate());
//
//		// System.out.println("--------- FROM -- TO " + from + " - " +
//		// to);
//
//		// System.out.println("-------" + to);
//
//		Collection logEntries = repository.log(new String[] { "" }, null, from, to, true, true);
//		// System.out.println(logEntries.size());
//		Commit commit = parseEntries(request,logEntries, url);
//
//		sourceRepository.save(commit);
//	    }
//
//	    System.out.println("Master list size --- " + changesetListMAster.size());
//
//	    sourceRepository.saveOrUpdateAll(changesetListMAster);
//
//	    List<Changeset> changeSets = sourceRepository.findAll();
//	    System.out.println(" ************** changeSets.size() --- " + changeSets.size());
//
//	} catch (SVNException e) {
//	    e.printStackTrace();
//	    throw new RuntimeException(e);
//	    // To change body of catch statement use File | Settings | File
//	    // Templates.
//	}
//    }
//
//    private Commit parseEntries(ServiceRequest request,Collection logEntries, String url) {
//	Commit commit = new Commit();
//	for (Iterator entries = logEntries.iterator(); entries.hasNext();) {
//	    StringBuilder changedEntries = new StringBuilder();
//	    SVNLogEntry logEntry = (SVNLogEntry) entries.next();
//	    // System.out.println("---------------------------------------------");
//	    // System.out.println("revision: " + logEntry.getRevision());
//	    // System.out.println("author: " + logEntry.getAuthor());
//	    // System.out.println("date: " + logEntry.getDate());
//	    // System.out.println("log message: " + logEntry.getMessage());
//
//	    commit.setRevision(logEntry.getRevision());
//	    commit.setAuthor(logEntry.getAuthor());
//	    commit.setLastModified(logEntry.getDate());
//	    commit.setMessage(logEntry.getMessage());
//
//	    List<Changelist> changes = new ArrayList<Changelist>();
//	    if (logEntry.getChangedPaths().size() > 0) {
//		Set changedPathsSet = logEntry.getChangedPaths().keySet();
//		for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths.hasNext();) {
//		    SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry.getChangedPaths().get(changedPaths.next());
//		    Changelist change = new Changelist();
//		    if (entryPath.getType() == 'M') {
//			change.setChangelistType(ChangelistType.MODIFIED);
//		    } else if (entryPath.getType() == 'A') {
//			change.setChangelistType(ChangelistType.ADDED);
//		    } else if (entryPath.getType() == 'D') {
//			change.setChangelistType(ChangelistType.DELETED);
//		    }
//		    change.setResourcePath(entryPath.getPath());
//		    change.setResourceUrl(url);
//		    changes.add(change);
//		}
//	    }
//	    commit.setChangeList(changes);
//	    parseFiles(changedEntries.toString());
//	}
//	return commit;
//    }
//
//    private String getStoryNumber(String message) {
//
//	if (message != null) {
//	    message = message.replace("\n", "");
//	    Matcher matcher = pattern.matcher(message);
//	    if (matcher.matches()) {
//		return (matcher.group(1));
//	    }
//	}
//	return "NOT FOUND";
//    }
//
//    private void parseFiles(String string) {
//	System.out.println(string);
//
//    }
//
//    // private List<Changeset> _parseEntries(Collection logEntries) {
//    // List<Changelist> changesetList = new ArrayList<Changelist>();
//    // for (Iterator entries = logEntries.iterator(); entries.hasNext();) {
//    //
//    //
//    // StringBuilder changedEntries = new StringBuilder();
//    // Changeset changeset = new Changeset();
//    // SVNLogEntry logEntry = (SVNLogEntry) entries.next();
//    //
//    // // System.out.println("---------------------------------------------");
//    // // System.out.println("revision: " + logEntry.getRevision());
//    // // System.out.println("author: " + logEntry.getAuthor());
//    // // System.out.println("date: " + logEntry.getDate());
//    // // System.out.println("log message: " + logEntry.getMessage());
//    //
//    //
//    // changeset.setRevision(logEntry.getRevision());
//    // changeset.setAuthor(logEntry.getAuthor());
//    // changeset.setLastModified(logEntry.getDate());
//    // changeset.setMessage(logEntry.getMessage());
//    // if (logEntry.getChangedPaths().size() > 0) {
//    //
//    // Set changedPathsSet = logEntry.getChangedPaths().keySet();
//    //
//    // for (Iterator changedPaths = changedPathsSet.iterator();
//    // changedPaths.hasNext();) {
//    //
//    // SVNLogEntryPath entryPath = (SVNLogEntryPath)
//    // logEntry.getChangedPaths().get(changedPaths.next());
//    // changedEntries.append(" " + entryPath.getType() + " " +
//    // entryPath.getPath()
//    // + ((entryPath.getCopyPath() != null) ? " (from " +
//    // entryPath.getCopyPath() + " revision " + entryPath.getCopyRevision() +
//    // ")" : ""));
//    // }
//    // }
//    // changeset.setFileNames(changedEntries.toString());
//    // changesetList.add(changeset);
//    // }
//    // return changesetList;
//    // }
//
//}
