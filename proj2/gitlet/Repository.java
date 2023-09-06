package gitlet;

import javax.print.attribute.standard.JobImpressions;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File STAGING_DIR = join(GITLET_DIR, "staging");
    public static final File INDEX_FILE = join(GITLET_DIR, "INDEX");
    public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");
    public static final File REF_DIR = join(GITLET_DIR, "refs");
    public  static final File HEADS_DIR = join(REF_DIR, "heads");
    public static final File HEAD = join(GITLET_DIR, "HEAD");
    private static Index stageAdded = new Index();
    private static Index stageRemoval = new Index();
    private static Commit currCommit;

    public void init() {
        // 1. Create the current working directory.
        if (GITLET_DIR.exists() && GITLET_DIR.isDirectory()) {
            System.out.println("A Gitlet version-control system already exists in the current directory");
            System.exit(0);
        }
        GITLET_DIR.mkdir();
        STAGING_DIR.mkdir();
        OBJECTS_DIR.mkdir();
        REF_DIR.mkdir();
        HEADS_DIR.mkdir();
        Blob.BLOB_FOLDER.mkdir();
        Commit.COMMIT_FOLDER.mkdir();
        Index.INDEX_FOLDER.mkdir();

        // 2. make the initial commit.
        Commit initialCommit = new Commit();
        writeCommitToFile(initialCommit);
        String commitId = initialCommit.getCommitId();

        // 3. add branch master; update HEAD & REF_DIR
        String branchName = "master";
        File branch = join(HEADS_DIR,branchName);
        writeContents(branch, commitId);
        writeContents(HEAD, branchName);
    }


    /** Description: Adds a copy of the file as it currently exists to the staging area
     *  see the description of the commit command.
     *  For this reason, adding a file is also called staging the file for addition.
     *  Staging an already-staged file overwrites the previous entry in the staging area with the new contents.
     *  The staging area should be somewhere in .gitlet.
     *  If the current working version of the file is identical to the version in the current commit,
     *  do not stage it to be added, and remove it from the staging area if it is already there
     *  (as can happen when a file is changed, added, and then changed back to it’s original version).
     *  The file will no longer be staged for removal (see gitlet rm), if it was at the time of the command.
     */
    public void add(String fileName) {
        File file = join(CWD, fileName);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }

        Commit headCommit = getHead();
        String headBlobId = headCommit.getFileVersion(fileName);

        Index index = readIndex();
        String indexBlobId = index.getAdd().getOrDefault(fileName, "");

        Blob blobFile = new Blob(fileName);
        String blobId = blobFile.getBlobId();

        if (headBlobId.equals(blobId)) {
            // No need to add
            if (!indexBlobId.equals(blobId)) {
                // del the file from staging area
                join(STAGING_DIR,indexBlobId).delete();
                index.getRemove().remove(fileName);
                index.getAdd().remove(fileName);
                writeIndex(index);
            }

        }
        else if (!blobId.equals(indexBlobId)){
            if (!indexBlobId.equals("")) {
                join(STAGING_DIR, indexBlobId).delete();
            }
            index.addFile(fileName, blobId);
            writeIndex(index);
            writeObject((join(STAGING_DIR, blobId)), blobFile);
        }


    }

    /** The commit is said to be tracking the saved files.
     *  By default, each commit’s snapshot of files will be exactly the same as its parent commit’s snapshot of files;
     *  it will keep versions of files exactly as they are, and not update them.
     *  A commit will only update the contents of files it is tracking that
     *  have been staged for addition at the time of commit, in which case the commit
     *  will now include the version of the file that was staged instead of the version it got from its parent.
     *  A commit will save and start tracking any files that were staged for addition but weren’t tracked by its parent.
     *  Finally, files tracked in the current commit may be untracked in the new commit
     *  as a result being staged for removal by the rm command (below).
     *  TODO: By default a commit has the same file contents as its parent.
     *  TODO: Files staged for addition and removal are the updates to the commit.
     */

    public void commit(String msg) {

        // Read from my computer the parent Commit object and the staging area
        // Constructor: 1.Modify its message and timestamp according to user input
        //              2.Copy its parentCommit pathToBlobHash
        // Use the staging area in order to modify the files tracked by the new commit
        // Write back any new object made or any modified objects read earlier.
        if (msg.equals("")) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }

        Commit headCommit = getHead();
        Index index = readIndex();
        if (index.isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }

        Commit newCommit = new Commit(msg, headCommit, index);
        String newCommitId = newCommit.getCommitId();

        writeCommitToFile(newCommit);
        clearIndex(index);

        File branch = getBranchFile();
        writeContents(branch, newCommitId);

    }
    private String getHeadBranchName() {
        String name = Arrays.toString(readContents(HEAD));
        return name;
    }
    private File getBranchFile() {
        return join(HEADS_DIR, getHeadBranchName());
    }
    public void rm(String fileName) {
        File file = join(CWD, fileName);

        Commit headCommit = getHead();
        String headBlobId = headCommit.getFileVersion(fileName);

        Index index = readIndex();
        String indexBlobId = index.getAdd().getOrDefault(fileName, "");

        /** the file is neither staged nor tracked by the head commit. */
        if (headBlobId == null && indexBlobId.equals("")){
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }
        /** If the file is current staged for addition. */
        if (!indexBlobId.equals("")) {
            index.removeFile(fileName);
        } else {
            index.getRemove().add(fileName);
        }

        /** If the file is tracked in the current commit,
         *  stage it for removal and remove the file from the working directory
         *   if the user has not already done so. */
        Blob blobFile = new Blob(fileName);
        String blobId = blobFile.getBlobId();
        if (blobFile.exists() && blobId.equals(headBlobId)) {
            restrictedDelete(file);
        }
        writeIndex(index);
    }
    public void checkoutFromHead (String filename){
        Commit head = getHead();
        checkFileFromCommit(head, filename);

    }

    /** Takes the version of the file as it exists in the commit with the given id,
     *  and puts it in the working directory, overwriting the version of the file that’s already there if there is one.
     *  The new version of the file is not staged.
     */
    public void checkoutFromCommit (String id, String filename) {
        File file = join(Commit.COMMIT_FOLDER, id);
        Commit commit = getCommitFromBranchFile(file);
        checkFileFromCommit(commit, filename);

    }
    public void checkoutFromBranch(String branchName) {
        // to be done

    }
    public void log() {
        Commit head = getHead();
        getCommitLogPrint(head);
        if (head.getParentId() != null) {
            head = getCommitFromId(head.getParentId());
            getCommitLogPrint(head);
        }

    }
    private void getCommitLogPrint(Commit c) {
        System.out.println("===");
        System.out.println("commit " + c.getCommitId());
        System.out.println("Date: " + c.gteDate());
        System.out.println(c.getMessage());
    }

    private void checkFileFromCommit(Commit c, String filename) {
        String blobId = c.getFileVersion(filename);
        Blob b = getBlobFromId(blobId);
        File workingPath = join(CWD, b.getFilePath());
        writeContents(workingPath, b.getFileContent());

    }

    private Blob getBlobFromId(String blobId) {
        File file = join(Blob.BLOB_FOLDER,blobId);
        return readObject(file, Blob.class);
    }
    private Commit getCommitFromId(String commitId) {
        File file = join(Commit.COMMIT_FOLDER, commitId);
        if (commitId == null || !file.exists()) {
            return null;
        }
        return readObject(file, Commit.class);
    }
    private String getCommitIdFromBranchFile(File file) {
        return readContentsAsString(file);
    }
    private Commit getCommitFromBranchFile(File file) {
        return getCommitFromId(getCommitIdFromBranchFile(file));
    }
    /** write a commit (serialized) to the file under OBJECT_DIR named by its id. */
    private void writeCommitToFile(Commit c) {
        File file = join(Commit.COMMIT_FOLDER, c.getCommitId());
        writeObject(file, c);
    }
    private Commit getHead() {
        File file = getBranchFile();
        return getCommitFromBranchFile(file);
    }
    private Index readIndex() {
        return readObject(INDEX_FILE, Index.class);
    }
    private void writeIndex(Index i) {
        writeObject(INDEX_FILE, i);
    }
    private void clearIndex(Index i) {
        i.getAdd().clear();
        i.getRemove().clear();
    }

}

