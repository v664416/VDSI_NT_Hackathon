package el_search;

import java.util.HashMap;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

public class TestDemo {
	
	public static void main(String[] args) throws Exception {
		//Create Client
		HashMap<String, String> newmap = new HashMap<String, String>();
		newmap.put("elasticsearch", "elasticsearch");
		Settings settings = ImmutableSettings.settingsBuilder().put(newmap).build();
		TransportClient transportClient = new TransportClient(settings);
		transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		//return (Client) transportClient;

		//Create Index and set settings and mappings
		/*String indexName = "test";
		CreateIndexRequestBuilder createIndexRequestBuilder = transportClient.admin().indices().prepareCreate(indexName);
		createIndexRequestBuilder.execute().actionGet();*/

		//Add documents
		/*String documentType = "";
		String documentId = "";
		IndexRequestBuilder indexRequestBuilder = transportClient.prepareIndex(indexName,documentType , documentId);
		//build json object
		XContentBuilder contentBuilder = indexRequestBuilder.
		contentBuilder.field("name", "jai");
		//contentBuilder.stopObject();
		indexRequestBuilder.setSource(contentBuilder);
		IndexResponse response = indexRequestBuilder .execute().actionGet();*/

		//Get document
		String type = "";
		String id = "";
		GetRequestBuilder getRequestBuilder = transportClient.prepareGet("", type, id);
		getRequestBuilder.setFields(new String[]{"name"});
		GetResponse response1 = getRequestBuilder.execute().actionGet();
		//String name = response1.field("name").getValue().toString();
		String name = response1.getFields().get("name").getValue().toString();

		
		
		/*
		 * 
		 * //Create Client
		HashMap<String, String> newmap = new HashMap<String, String>();
		newmap.put("cluster.name", "localtestsearch");
		Settings settings = ImmutableSettings.settingsBuilder().put(newmap).build();
		TransportClient transportClient = new TransportClient(settings);
		transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		//return (Client) transportClient;

		//Create Index and set settings and mappings
		String indexName = "test";
		CreateIndexRequestBuilder createIndexRequestBuilder = transportClient.admin().indices().prepareCreate(indexName);
		createIndexRequestBuilder.execute().actionGet();

		//Add documents
		String documentType = "";
		String documentId = "";
		IndexRequestBuilder indexRequestBuilder = transportClient.prepareIndex(indexName,documentType , documentId);
		//build json object
		XContentBuilder contentBuilder = indexRequestBuilder.startObject().prettyPrint();
		contentBuilder.field("name", "jai");
		contentBuilder.stopObject();
		indexRequestBuilder.setSource(contentBuilder);
		IndexResponse response = indexRequestBuilder .execute().actionGet();

		//Get document
		String type = "";
		String id = "";
		GetRequestBuilder getRequestBuilder = transportClient.prepareGet(indexName, type, id);
		getRequestBuilder.setFields(new String[]{"name"});
		GetResponse response1 = getRequestBuilder.execute().actionGet();
		String name = response1.field("name").getValue().toString();

		 * 
		 */
	}
	
	
	
}
