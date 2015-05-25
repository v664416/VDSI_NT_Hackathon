package org.rongubombu.elasticsearch;


import java.util.HashMap;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class ELSearchConnectionUtil {
	private static ELSearchConnectionUtil instance = new ELSearchConnectionUtil();
	private TransportClient transportClient;
	private ELSearchConnectionUtil(){
		HashMap<String, String> newmap = new HashMap<String, String>();
		//newmap.put("elasticsearch", "elasticsearch");
		Settings settings = ImmutableSettings.settingsBuilder().put(newmap).build();
		transportClient = new TransportClient(settings);
		transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
	}
	
	public static TransportClient getTransportClient(){
		
		return instance.transportClient;
	}
	public static void addDocument(){
		/* curl -XPUT "http://localhost:9200/test?t1" -d "{\"first_name\":\"raju\"}" */
		
				//Create Index and set settings and mappings
				TransportClient transportClient = getTransportClient();
				/*CreateIndexRequestBuilder createIndexRequestBuilder = transportClient.admin().indices().prepareCreate("megacorp");
				createIndexRequestBuilder.execute().actionGet();*/

				//Add documents
				IndexRequestBuilder indexRequestBuilder = transportClient.prepareIndex("megacorp" ,"employee" , "3");
				indexRequestBuilder.setSource("{\"first_name\":\"venkat\"}");
				IndexResponse response = indexRequestBuilder .execute().actionGet();
				System.out.println(response.getHeaders());
	}
	
	public static String  getDocument(){
		String str = null;
		
		

		/*curl -XGET "http://localhost:9200/test?t1" */
		//Get document
				String type = "employee";
				String id = "2";
				String index="megacorp";
				TransportClient transportClient = getTransportClient();
				GetRequestBuilder getRequestBuilder = transportClient.prepareGet(index, type, id);
				
				//getRequestBuilder.setFields(new String[]{"first_name"});
				GetResponse response1 = getRequestBuilder.execute().actionGet();
				System.out.println(response1.getSource());
				String name = response1.getFields().get("_source").getValue().toString();
				return str;
	}
	
	public static void updateDocument(){
		try{
			TransportClient transportClient = getTransportClient();
			UpdateRequest updateRequest = new UpdateRequest();
			updateRequest.index("megacorp");
			updateRequest.type("employee");
			updateRequest.id("3");
			updateRequest.doc(jsonBuilder()
			        .startObject()
			            .field("gender", "male")
			        .endObject());
			UpdateResponse resp  = transportClient.update(updateRequest).get();
		}catch(Exception err){
			err.printStackTrace();
		}
	}
	
	public static void deleteDocument(){
				TransportClient transportClient = getTransportClient();
				DeleteResponse response = transportClient.prepareDelete("megacorp", "employee", "2")
				        .execute()
				        .actionGet();
				System.out.println(response);
	}
	
	
	public static void main(String[] args) {
		//ELSearchConnectionUtil.getDocument();
		//ELSearchConnectionUtil.addDocument();
		//ELSearchConnectionUtil.deleteDocument();
		ELSearchConnectionUtil.updateDocument();
	}
}
