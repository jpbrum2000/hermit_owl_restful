package webservice;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@Path("/owlreasoner")
public class owl_reasoner {
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String getOwlReasoner(@QueryParam("owlfile") String owlfile) {
		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			InputStream stream;
			try {
				stream = new ByteArrayInputStream(owlfile.getBytes(StandardCharsets.UTF_8.name()));
			} catch (UnsupportedEncodingException e) {
				throw new Exception("Erro de codificação UTF-8 no arquivo");
			}
			OWLOntology ontology = manager.loadOntologyFromOntologyDocument(stream);
			Reasoner hermit = new Reasoner(ontology);
			String result_owl_reasoner = null;
			

			return result_owl_reasoner;
		} catch (Exception e) {
			return "ERRO: " + e.getMessage();
		}
	}
}
