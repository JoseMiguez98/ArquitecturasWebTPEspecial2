package test;
import java.io.IOException;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRESTInterface {

	public final String BASE_URL="http://localhost:8080/ArquiTPEspecial2/api";

	public final HttpClient client = HttpClientBuilder.create().build();


	//	public static void main(String[]args) throws ClientProtocolException, IOException {
	//		testPOSTUser();
	//	}

	@Test
	public void test_A_POSTUser() throws ClientProtocolException, IOException {
		String url = BASE_URL + "/users";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		ArrayNode knowledge = mapper.valueToTree(new String[]{"Algebra","Algorithms","Maths++","MySQL"});
		jsonObject.put("name", "Jose");
		jsonObject.put("isAuthor", true);
		jsonObject.put("isEvaluator", true);
		jsonObject.putArray("knowledge").addAll(knowledge);
		String jsonString = jsonObject.toString();

		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);
		
		Assert.assertEquals(201,response.getStatusLine().getStatusCode());
		
		knowledge = mapper.valueToTree(new String[]{"Maths++","MySQL"});
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("name", "Jose2");
		jsonObject.put("isAuthor", true);
		jsonObject.put("isEvaluator", true);
		jsonObject.putArray("knowledge").addAll(knowledge);
		jsonString = jsonObject.toString();
		
		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);
		
		Assert.assertEquals(201,response.getStatusLine().getStatusCode());
	}
	
	
	@Test
	public void test_B_POSTProject() throws ClientProtocolException, IOException {
		String url = BASE_URL + "/projects";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		ArrayNode topics = mapper.valueToTree(new String[]{"Algebra","Algorithms","Maths++","MySQL"});
		jsonObject.put("name", "ProjectAPITest");
		jsonObject.put("category", "Poster");
		jsonObject.put("idAuthor", 2);
		jsonObject.putArray("topics").addAll(topics);
		String jsonString = jsonObject.toString();

		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);
		
		Assert.assertEquals(201,response.getStatusLine().getStatusCode());
		
		topics = mapper.valueToTree(new String[]{"Algebra","Algorithms","Dijkstra","DeepLearning"});
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("name", "Project2");
		jsonObject.put("category", "Summary");
		jsonObject.put("idAuthor", 2);
		jsonObject.putArray("topics").addAll(topics);
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);
		
		Assert.assertEquals(201,response.getStatusLine().getStatusCode());
		
	}
	
	@Test
	public void test_C_PUTUser() throws ClientProtocolException, IOException {
		//Suponiendo que ya existe el user con id 1
		
		String url = BASE_URL + "/users/1";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		ArrayNode knowledge = mapper.valueToTree(new String[]{"Algebra","MySQL"});
		jsonObject.put("name", "EditedUser");
		jsonObject.put("isAuthor", true);
		jsonObject.put("isEvaluator", true);
		jsonObject.putArray("knowledge").addAll(knowledge);
		String jsonString = jsonObject.toString();

		HttpPut put = new HttpPut(url);
		put.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(put);
		
		Assert.assertEquals(200,response.getStatusLine().getStatusCode());
	}	

	@Test
	public void test_D_AddRevision() throws ClientProtocolException, IOException {
		//Suponiendo que ya existe el user con id 1
		
		String url = BASE_URL + "/users/rev";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("idUser", 1);
		//En mi caso la BBDD genera el id de los proyectos a partir de 3, en caso de no funcionar cambiar el
		//numero de id de acuerdo a como fueron generados si no retorna 404.
		jsonObject.put("idProject", 3);
		jsonObject.put("revisionDate", "03/06/2015");
		String jsonString = jsonObject.toString();

		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);
		
		Assert.assertEquals(201,response.getStatusLine().getStatusCode());
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("idUser", 1);
		jsonObject.put("idProject", 4);
		jsonObject.put("revisionDate", "03/06/2020");
		jsonString = jsonObject.toString();
		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);
		
		//No se debe poder asignar esta revisión ya que el proyecto es "Summary" y el usuario no
		//posee los conocimientos suficientes para ser evaluador del mismo
		
		Assert.assertEquals(403,response.getStatusLine().getStatusCode());
	}
	
	@Test
	public void test_E_getResearchWorks() throws ClientProtocolException, IOException {
		//Suponiendo que ya existe el user con id 1
		
		String url = BASE_URL + "/users";

		HttpGet get = new HttpGet(url+"/2/works");
		HttpResponse response = client.execute(get);
		
		//Si el content length es mayor a 2, entonces tiene trabajos si no la respuesta esta vacia
		
		Assert.assertTrue(response.getEntity().getContentLength()>2);
	
		get = new HttpGet(url+"/1/works");
		response = client.execute(get);
		
		Assert.assertFalse(response.getEntity().getContentLength()>2);
	}
	
	@Test
	public void test_F_getRevisionsBetween() throws ClientProtocolException, IOException {
		//Suponiendo que ya existe el user con id 1
		
		String url = BASE_URL + "/users/1/findRevBetween";

		HttpGet get = new HttpGet(url+"?from=03/06/2013&to=03/06/2016");
		HttpResponse response = client.execute(get);
		
		//Si es mayor a 2 entonces vino algo en la respuesta
		
		Assert.assertTrue(response.getEntity().getContentLength()>2);
	
	}
}
