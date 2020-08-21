package Module.Tree;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static Module.IqtreePathEntity.IQTREE_RESULTFOLDER;

public class TreeService {

    public TreeService() {

    }

    public TreeEntity getTree(String url) {
        TreeEntity treeEntity = new TreeEntity();
        treeEntity.url = url + ".treefile";
        try {
            treeEntity.data = FileUtils.readFileToString(new File(IQTREE_RESULTFOLDER, treeEntity.url), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return treeEntity;
    }
}
