package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.Serializable;
import static gitlet.Utils.*;

import gitlet.Utils.*;

// TODO: You'll likely use this in this class

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *  Combinations of log messages, other metadata (commit date, author, etc.),
 *  a reference to a tree, and references to parent commits.
 *  The repository also maintains a mapping from branch heads to references to commits,
 *  so that certain important commits have symbolic names.
 *  In the case of commits, it means
 *  the same metadata, the same mapping of names to references,and the same parent reference.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     */
    public static final File COMMIT_FOLDER = join(Repository.OBJECTS_DIR, "commits");
    /** The message of this Commit. */
    private String message;
    /** The timestamp of this Commit. */
    private String timestamp;
    /** the parent commitId of the commit object. */
    private List<String> parentId;
    /** the tracked files of this commit. */
    private Map<String, String> fileNameToBlobId;
    private String commitId;
    private File commitSaveFileName;

    /** Make the initial Commit. */
    public Commit() {
        this.message = "initial commit";
        this.timestamp = dateToTimeStamp(new Date(0));
        commitId = sha1(message, timestamp);
        parentId = new ArrayList<String>();
        fileNameToBlobId = new HashMap<>();

    }
    public Commit(String message) {
        this.message = message;
        this.timestamp = dateToTimeStamp(new Date());
        fileNameToBlobId = new HashMap<>();
        parentId = new ArrayList<>(2);
    }
    public Commit(String msg, Commit c) {
        this.message = msg;
        this.timestamp = dateToTimeStamp(new Date());
        this.parentId = new ArrayList<>(2);
        for (String id : c.parentId) {
            this.parentId.add(new String(id));
        }
        this.fileNameToBlobId = new HashMap<>();
        for (Map.Entry<String, String> entry : c.fileNameToBlobId.entrySet()) {
            String fileName = entry.getKey();
            String blobId = entry.getValue();
            this.fileNameToBlobId.put(new String(fileName), new String(blobId));
        }
        this.commitId = getParentId();
    }
    public Commit(String msg, Commit c,Index i) {
        this.message = msg;
        this.timestamp = dateToTimeStamp(new Date());
        this.parentId = new ArrayList<>(2);
        String id = c.getCommitId();
        this.parentId.add(id);
        //need fixed
        this.fileNameToBlobId = c.getTrackedFiles();
        /** update the contents of files it is tracking that have been staged for addition. */
        this.fileNameToBlobId = new HashMap<>();
        for (Map.Entry<String, String> entry : i.getAdd().entrySet()) {
            String fileName = entry.getKey();
            String blobId = entry.getValue();
            this.fileNameToBlobId.put(new String(fileName), new String(blobId));
        }
        /** untracked the file in the remove set from index. */
        for (String filename : i.getRemove()) {
            fileNameToBlobId.remove(filename);
        }
        this.commitId = generateID();
    }
    public String dateToTimeStamp (Date date) {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        return dateFormat.format(date);
    }
    private String generateID() {
        return sha1(message, timestamp, parentId.toString(), fileNameToBlobId.toString());
    }
    /** Add parent commitId into the list and copy the whole parent hashMap. */
    public void addParent(Commit p) {
        parentId.add(p.commitId);
        fileNameToBlobId.putAll(p.fileNameToBlobId);
    }
    public String getCommitId() {
        return commitId;
    }
    public String gteDate() {
        return timestamp;
    }
    public String getMessage() {
        return message;
    }
    public String getParentId() {
        if (parentId.isEmpty()) {
            return null;
        }
        return parentId.get(0);
    }
    public Map<String, String> getTrackedFiles() {
        return fileNameToBlobId;
    }
    /** Search from BLOB_FOLDER to Get the content of tracked files. */
    public byte[] getTrackedFileContent (String filName) {
        String blobId = fileNameToBlobId.get(filName);
        File file = join(Blob.BLOB_FOLDER, blobId);
        return readObject(file, Blob.class).getFileContent();
    }
    public String getFileVersion (String fileName) {
        if (!fileNameToBlobId.containsKey(fileName)) {
            return null;
        }
        return this.fileNameToBlobId.get(fileName);
    }

    /** Read and deserializes a commit from the file named by its SHA-1. */
    public static Commit fromFile(File filePath, String CommitId) {
        File file = join(filePath, CommitId);
        return readObject(file, Commit.class);
    }

    /** store a commit to a file(named by its SHA-1) under the given path. */
    public void saveCommit() {
        File file = join(COMMIT_FOLDER, this.generateID());
        writeObject(file, this);
    }



}
