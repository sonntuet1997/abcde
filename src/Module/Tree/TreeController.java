package Module.Tree;

import Module.GenerateTree.GenerateTreeEntity;
import Module.GenerateTree.GenerateTreeService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/tree")
public class TreeController {
    @Inject
    private TreeService treeService;

    public TreeController() {
    }

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public GenerateTreeEntity e() {
        GenerateTreeEntity generateTreeEntity = new GenerateTreeEntity();
        generateTreeEntity.geneticCode = "sadasd";
        return generateTreeEntity;
    }



    @GET
    @Path("{url}")
    @Produces(MediaType.APPLICATION_JSON)
    public TreeEntity getTree(@PathParam("url") String url) {
        return treeService.getTree(url);
    }

}