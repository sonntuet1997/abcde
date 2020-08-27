package Module;

import java.io.File;
import java.util.Map;

public class IqtreePathEntity {
    public static Map<String, String> map = System.getenv();
    public static String current = new File("").getAbsolutePath();
    public static String IQTREE_HOME = IqtreePathEntity.map.get("IQTREE_HOME") != null ? map.get("IQTREE_HOME") : "E:\\EncryptFile-master\\iqtree-1.6.12-Windows";
    public static String IQTREE_BIN = IqtreePathEntity.map.get("IQTREE_BIN") != null ? map.get("IQTREE_BIN") : IQTREE_HOME+"/bin";
    public static String IQTREE_DATAFOLDER = IqtreePathEntity.map.get("IQTREE_DATAFOLDER") != null ? map.get("IQTREE_DATAFOLDER") : IQTREE_HOME+"/data";
    public static String IQTREE_RESULTFOLDER = IqtreePathEntity.map.get("IQTREE_RESULTFOLDER") != null ? map.get("IQTREE_RESULTFOLDER") : IQTREE_HOME+"/result";
    public static String IQTREE_EXAMPLEALIGNMENT = IqtreePathEntity.map.get("IQTREE_EXAMPLEALIGNMENT") != null ? map.get("IQTREE_EXAMPLEALIGNMENT") : IQTREE_HOME+"/example.phy";

}
