
package com.vplan.hackathon;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.netty.handler.codec.http.multipart.HttpPostMultipartRequestDecoder;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

class ElasticSearchTest{
	
	public static void main(String[] x){

		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "elasticsearch").build(); 
		
		/*TransportClient transportClient = new TransportClient(settings); 
		transportClient = transportClient.addTransportAddress
				(new InetSocketTransportAddress("localhost", 9300)); 
		//return (Client) transportClient; */
		
		
		//ElasticsearchClient

		Node node = NodeBuilder.nodeBuilder().clusterName("elasticsearch").node();
		Client client = node.client();
		client.prepareIndex("kodcucom", "article", "2").setSource(putJsonDocument("ElasticSearch: Java API",
		"ElasticSearch provides the Java API, all operations can be executed asynchronously using a client object.",
		new Date(),
		new String[]{"elasticsearch"},
		"Hüseyin Akdoğan")).execute().actionGet();
		//node.close();
		
		
		HttpPost 


		GetResponse getResponse = client.prepareGet("kodcucom", "article", "2").execute().actionGet();
		Map<String, Object> source = getResponse.getSource();
		System.out.println("------------------------------");
		System.out.println("Index: " + getResponse.getIndex());
		System.out.println("Type: " + getResponse.getType());
		System.out.println("Id: " + getResponse.getId());
		System.out.println("Version: " + getResponse.getVersion());
		System.out.println(source);
		System.out.println("------------------------------");

	}
	


	public static Map<String, Object> putJsonDocument
	(String title, String content, Date postDate, String[] tags, String author){
	
	
		Map<String, Object> jsonDocument = new HashMap<String, Object>();
	
		jsonDocument.put("title", title);
		jsonDocument.put("conten", content);
		jsonDocument.put("postDate", postDate);
	
		jsonDocument.put("tags", tags);
		jsonDocument.put("author", author);
	
		return jsonDocument;
	
	}	
	
}