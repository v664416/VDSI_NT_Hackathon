package org.rongubombu.elasticsearch;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class ELSearchConnectionUtil {
	private static ELSearchConnectionUtil instance = new ELSearchConnectionUtil();
	private static String type = "book";
	private static String index="rbweb3";
	private ELSearchConnectionUtil(){
		
	}
	
	public static TransportClient getTransportClient(){
		
		HashMap<String, String> newmap = new HashMap<String, String>();
		//newmap.put("elasticsearch", "elasticsearch");
		Settings settings = ImmutableSettings.settingsBuilder().put(newmap).build();
		TransportClient transportClient = new TransportClient(settings);
		transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		return transportClient;
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
	
	public static String  getDocument(String query){
		String str = null;
		
		

		/*curl -XGET "http://localhost:9200/test?t1" */
		//Get document
				String id = Long.toString(System.currentTimeMillis());
				TransportClient transportClient = getTransportClient();
				GetRequestBuilder getRequestBuilder = transportClient.prepareGet(index, type, id);
				GetResponse response1 = getRequestBuilder.execute().actionGet();
				str = response1.getSource().toString();
				return str;
	}
	
	
	public static List<Map<String,Object>> getResult(String query){
		 int scrollSize = 1000;
	        List<Map<String,Object>> esData = new ArrayList<Map<String,Object>>();
	        SearchResponse response = null;
	        TransportClient client = getTransportClient();
	        int i = 0;
	        while( response == null || response.getHits().hits().length != 0){
	        	if(query != null && query.length() > 0){
	        		response = client.prepareSearch(index)
		                    .setTypes(type)
		                       .setQuery(QueryBuilders.matchAllQuery())
		                       //.setQuery(QueryBuilders.matchQuery("book_name", query))
		                       .setQuery(QueryBuilders.multiMatchQuery(query, "book_name", "auth_name", "category", "publisher"))
		                       .setSize(scrollSize)
		                       .setFrom(i * scrollSize)
		                    .execute()
		                    .actionGet();
	        	}else{
	        		response = client.prepareSearch(index)
		                    .setTypes(type)
		                       .setQuery(QueryBuilders.matchAllQuery())
		                       .setSize(scrollSize)
		                       .setFrom(i * scrollSize)
		                    .execute()
		                    .actionGet();
	        	}
	            
	            for(SearchHit hit : response.getHits()){
	                esData.add(hit.getSource());
	            }
	            i++;
	        }
	        client.close();
	        System.out.println("Processed Query result" + esData);
	        return esData;
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
		//ELSearchConnectionUtil.updateDocument();
		//ELSearchConnectionUtil.getResult();
	}
}