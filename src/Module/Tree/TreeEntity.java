package Module.Tree;

import java.io.Serializable;
import java.util.Objects;

public class TreeEntity implements Serializable {
    public String url;
    public String data;
    public String processedFile;
    public boolean isProcessing;
    public String status;

    public TreeEntity() {
        super();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeEntity treeEntity = (TreeEntity) o;
        return Objects.equals(url, treeEntity.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

}
