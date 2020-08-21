package Module;

import java.util.Map;

public class IqtreePathEntity {
    public static Map<String, String> map = System.getenv();
    public static String IQTREE_HOME = IqtreePathEntity.map.get("IQTREE_HOME") != null ? map.get("IQTREE_HOME") : "E:\\EncryptFile-master\\iqtree-1.6.12-Windows\\bin";
    public static String IQTREE_DATAFOLDER = IqtreePathEntity.map.get("IQTREE_DATAFOLDER") != null ? map.get("IQTREE_DATAFOLDER") : "E:\\EncryptFile-master\\iqtree-1.6.12-Windows\\data";
    public static String IQTREE_RESULTFOLDER = IqtreePathEntity.map.get("IQTREE_RESULTFOLDER") != null ? map.get("IQTREE_RESULTFOLDER") : "E:\\EncryptFile-master\\iqtree-1.6.12-Windows\\result";
    public static String IQTREE_EXAMPLEALIGNMENT = IqtreePathEntity.map.get("IQTREE_EXAMPLEALIGNMENT") != null ? map.get("IQTREE_EXAMPLEALIGNMENT") : "E:\\EncryptFile-master\\iqtree-1.6.12-Windows\\example.phy";

}
