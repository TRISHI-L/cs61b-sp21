package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;

public class Index implements Serializable {
    public static final File INDEX_FOLDER = join(Repository.GITLET_DIR,"stagingArea");
    private HashMap<String, String> add = new HashMap<>();
    private HashSet<String> remove = new HashSet<>();

    public String getBlobId(String fileName) {
        if (!add.containsKey(fileName)) {
            return "";
        }
        return add.get(fileName);
    }
    public HashMap<String, String> getAdd() {
        return add;
    }
    public HashSet<String> getRemove() {
        return remove;
    }
    public boolean isEmpty() {
        return add.isEmpty() && remove.isEmpty();
    }
    public void removeFile(String fileName) {
        add.remove(fileName);
        remove.add(fileName);
    }
    public void addFile(String fileName, String blobId) {
        remove.remove(fileName);
        add.put(fileName,blobId);
    }

}

