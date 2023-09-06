package gitlet;


import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;


/** Every object–every blob and every commit in our case
 *  has a unique integer id that serves as a reference to the object.
 *  two objects with exactly the same content will have the same id on all systems.
 *  In the case of blobs, "same content" means the same file contents.
 */

public class Blob implements Serializable {
    public static final File BLOB_FOLDER = join(Repository.OBJECTS_DIR, "blobs");
    private String filePath;

    private File blobSaveFileName;

    private byte[] fileContent;
    private String fileName;


    private String blobId;


    private TreeMap<String, String> stagingArea;
    public Blob (String fileName) {
        File file = join(Repository.CWD,fileName);
        filePath = file.getAbsolutePath();
        fileContent = readContents(file);
        blobId = sha1(fileName, fileContent);
        blobSaveFileName = join(Repository.OBJECTS_DIR, "blobs", blobId);

        writeContents(blobSaveFileName, fileContent);
    }
    public Blob (String fileName, byte[] fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }
    public String getFilePath () {
        return filePath;
    }
    public String getBlobId () {
        return sha1(serialize(this));
    }

    public byte[] getFileContent() {
        return fileContent;
    }
    public boolean exists() {
        return this.fileContent != null;
    }


    /** Read and deserializes a blob from a file named by its SHA-1. */
    public static Blob fromFile(File filePath, String blobId) {
        File file = join(filePath, blobId);
        return readObject(file, Blob.class);
    }

    /** store a blob to a file(named by its SHA-1) under the given path. */
    public void saveBlob(String filePath) {
        File file = join(filePath, this.getBlobId());
        writeObject(file, this);
    }

    /** remove a blob file from persistence. */
    public void unsaveBlob(String filePath) {
        File file = join(filePath, this.getBlobId());
        restrictedDelete(file);
    }


    /** // Read index HashMap from STAGING_DIR
    public Map<String, String> readIndex() {
        if (Repository.STAGING_DIR.exists()) {
            HashMap<String, String> index = readObject(Repository.STAGING_DIR, HashMap.class);
            return index;
        }
        else {
            return new HashMap<>();
        }

    }

    // Update index HashMap with file and blob hash
    public void updateIndex(String fileName, String blobHash) {
        Map<String, String> index = readIndex();
        index.put(fileName, blobHash);
        writeObject(Repository.STAGING_DIR, index);
    } */
    

}
