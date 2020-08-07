package Module.File;

//import javax.persistence.Entity;
//import javax.persistence.Table;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//import javax.ws.rs.QueryParam;
//import java.sql.Timestamp;
//
//public class SearchFileModel {
//    @QueryParam("id")  public Integer id;
//    @QueryParam("length") public Integer length;
//    @QueryParam("name")public String name;
//    @QueryParam("employeeId")public Integer employeeId;
//    @QueryParam("createTime") public Timestamp createTime;
//    @QueryParam("extension")public String extension;
//    public CriteriaQuery<FileModel> applyTo(CriteriaBuilder builder, CriteriaQuery<FileModel> criteria, Root<FileModel> root) {
//        if (id != null) {
//            criteria.where(builder.equal(root.get(FileModel_.id), id));
//        }
//        if (length != null) {
//            criteria.where(builder.equal(root.get(FileModel_.length), length));
//        }
//        if (name != null && !name.isEmpty()) {
//            criteria.where(builder.like(builder.lower(root.get(FileModel_.name)), "%" + name.toLowerCase() + "%"));
//        }
//        if (employeeId != null) {
//            criteria.where(builder.equal(root.get(FileModel_.employeeId), employeeId));
//        }
//        if (createTime != null) {
//            criteria.where(builder.equal(root.get(FileModel_.createTime), createTime));
//        }
//        if (extension != null && !extension.isEmpty()) {
//            criteria.where(builder.like(builder.lower(root.get(FileModel_.extension)), "%" + name.toLowerCase() + "%"));
//        }
//        return criteria;
//    }
//}
