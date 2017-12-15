package webservice;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.sun.jersey.multipart.FormDataParam;


@Path("/treeview")
public class treeview {
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String getOwlReasoner(@FormDataParam("owlfile") File owlfile) {
		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			OWLOntology ontology = manager.loadOntologyFromOntologyDocument(owlfile);
			Reasoner hermit = new Reasoner(ontology);
			StringWriter result_owl_reasoner = new StringWriter();
			PrintWriter pw = new PrintWriter(result_owl_reasoner);
			hermit.printHierarchies( pw, true, true, true);
			//Converte printHierarchies into JSON FORMAT for jstree
			String jstree_json = null;
	        try (Scanner scanner = new Scanner(result_owl_reasoner.toString())) {
	            String filho = null;
	            String pai = null;
	            jstree_json = "[{ \"id\" : \"Thing\", \"parent\" : \"#\", \"text\" : \"Thing\", \"icon\" : \"https://static.xx.fbcdn.net/rsrc.php/v3/yt/r/lKNUt5Yol6s.png\" },";
	            while (scanner.hasNextLine()) {
	                String line = scanner.nextLine();
	                if(line.contains("SubClassOf")){
	                    filho = line.substring(line.indexOf(':')+1,line.indexOf(' ',line.indexOf(':')));
	                    pai = line.substring(line.indexOf(':',line.indexOf(':')+1)+1,line.indexOf(' ',line.indexOf(':',line.indexOf(':')+1)));
	                    jstree_json += "{ \"id\" : \""+filho+"\", \"parent\" : \""+pai+"\", \"text\" : \""+filho+"\", \"icon\" : \"https://leaf.nutrisystem.com/wp-content/themes/leaf/assets/images/global/green-arrow.svg\" },";
	                }
	            }
	            jstree_json = jstree_json.substring(0, jstree_json.length() - 1);
	            jstree_json += "]";
	        }
	        System.out.println(jstree_json);
	        return "oi";
			//return jstree_json;
		} catch (Exception e) {
			return "ERRO: " + e.getMessage();
		}
	}
}
