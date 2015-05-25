package org.rongubombu.elasticsearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.QueryParam;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
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
import org.elasticsearch.index.query.QueryBuilders;
import org.rongubombu.util.LogUtil;

public class ELSearchConnectionUtil {
	private static ELSearchConnectionUtil instance = new ELSearchConnectionUtil();
	private static String type = "book";
	private static String index = "rbweb_g24_3";
	private TransportClient transportClient;
	
	private ELSearchConnectionUtil() {
		HashMap<String, String> newmap = new HashMap<String, String>();
			newmap.put("elasticsearch", "elasticsearch");
		//newmap.put("client.transport.ping_timeout", "30");
		Settings settings = ImmutableSettings.settingsBuilder().put(newmap)
				.build();
		transportClient = new TransportClient(settings);
		transportClient = transportClient
				.addTransportAddress(new InetSocketTransportAddress(
						"localhost", 9300));
	}

	public static TransportClient getTransportClient() {
		return instance.transportClient;
	}

	public static void addDocument() {
		/*
		 * curl -XPUT "http://localhost:9200/test?t1" -d
		 * "{\"first_name\":\"raju\"}"
		 */

		// Create Index and set settings and mappings
		TransportClient transportClient = getTransportClient();
		/*
		 * CreateIndexRequestBuilder createIndexRequestBuilder =
		 * transportClient.admin().indices().prepareCreate("megacorp");
		 * createIndexRequestBuilder.execute().actionGet();
		 */

		// Add documents
		IndexRequestBuilder indexRequestBuilder = transportClient.prepareIndex(
				index, type, System.currentTimeMillis()+"");
		String str = "{\"first_name\":\"venkat\"}";
		indexRequestBuilder.setSource(str);
		IndexResponse response = indexRequestBuilder.execute().actionGet();
		System.out.println("Row inserted."+response.getHeaders().stream());
	}
	
	
	public static boolean addDocument(String authoname, String category, String bookName, String publisher) {
		TransportClient transportClient = getTransportClient();
		IndexRequestBuilder indexRequestBuilder = transportClient.prepareIndex(
				index, type, System.currentTimeMillis()+"");
		String str = "{\"book_name\":\""+bookName+"\", \"auth_name\":\""+authoname+
				"\", \"category\":\""+category+"\", \"publisher\":\""+publisher+"\"}";
		indexRequestBuilder.setSource(str);
		IndexResponse response = indexRequestBuilder.execute().actionGet();
		return response.isCreated();
	}
	
	public static void addDocument(String book,String bookId) throws Exception{
		try{
			
				TransportClient transportClient = getTransportClient();
				IndexRequestBuilder indexRequestBuilder = transportClient
						.prepareIndex(index, type, bookId);
				indexRequestBuilder.setSource(book);
				IndexResponse response = indexRequestBuilder.execute()
						.actionGet();
				System.out.println("document inserted: "
						+ response.getHeaders());
			
		}catch(Exception error){
			LogUtil.logTrace(error);
			throw error;
		}
	}

	/*public static void addDocument(List<String> books,List<String> bookId) throws Exception{
		try{
			for (int i = 0; i < books.size(); i++) {
				TransportClient transportClient = getTransportClient();
				IndexRequestBuilder indexRequestBuilder = transportClient
						.prepareIndex(index, type, bookId.get(i));
				String val = books.get(i);
				indexRequestBuilder.setSource(val);
				IndexResponse response = indexRequestBuilder.execute()
						.actionGet();
				System.out.println("document inserted: "
						+ response.getHeaders().stream().toString());
			}
		}catch(Exception error){
			LogUtil.logTrace(error);
			throw error;
		}
	}*/
	
	public static void addDocument(List<String> books,List<String> bookId) throws Exception{
		try{
			TransportClient transportClient = getTransportClient();
			BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
			BulkResponse bulkResponse = null;
			int recordCount = 0;
			int size = books.size();
			long  start = System.currentTimeMillis();
			String typetemp = type+System.currentTimeMillis();
			for (int i = 0; i < size; i++) {
				recordCount++;
				IndexRequestBuilder indexRequestBuilder = transportClient
						.prepareIndex(index, typetemp, bookId.get(i));
				indexRequestBuilder.setSource(books.get(i));
				bulkRequest.add(indexRequestBuilder);
				if(recordCount == 100000 || (i == (size-1))){
					bulkResponse = bulkRequest.execute().actionGet();
					if(bulkResponse.hasFailures()){
						System.out.println("Failured Occured"+bulkResponse.toString());
					}
					recordCount = 0;
					bulkResponse = null;
					bulkRequest = transportClient.prepareBulk();
					typetemp = type+System.currentTimeMillis();
					System.out.println("100000 Records Inserted in :"+((System.currentTimeMillis()-start)/1000)+" seconds");
				}
			}
		}catch(Exception error){
			LogUtil.logTrace(error);
			throw error;
		}
	}


	public static String getDocument(String query) {
		String str = null;

		/* curl -XGET "http://localhost:9200/test?t1" */
		// Get document
		String id = Long.toString(System.currentTimeMillis());
		TransportClient transportClient = getTransportClient();
		GetRequestBuilder getRequestBuilder = transportClient.prepareGet(index,
				type, id);
		GetResponse response1 = getRequestBuilder.execute().actionGet();
		str = response1.getSource().toString();
		return str;
	}

	public static SearchResponse getResult(String query, int page, int scrollSize) {
		SearchResponse response = null;
		TransportClient client = getTransportClient();
		int i = 0;
		if(page > 1){
			i = ((page-1)*(scrollSize))-1;
		}
		if (query != null && query.trim().length() > 0) {
			response = client
					.prepareSearch(index)
					//.setTypes(type)
					.setQuery(QueryBuilders.matchAllQuery())
					// .setQuery(QueryBuilders.matchQuery("book_name",
					// query))
					.setQuery(
							QueryBuilders.multiMatchQuery(query,
									"book_name", "auth_name", "category",
									"publisher"))
					.setSize(scrollSize)
					.setFrom(i).execute().actionGet();
		} else {
			response = client.prepareSearch(index)
					//.setTypes(type)
					.setQuery(QueryBuilders.matchAllQuery())
					.setSize(scrollSize)
					.setFrom(i).execute()
					.actionGet();
		}

		
		//System.out.println("Processed Query result" + esData);
		return response;
	}

	public static void updateDocument() {
		try {
			TransportClient transportClient = getTransportClient();
			UpdateRequest updateRequest = new UpdateRequest();
			updateRequest.index("megacorp");
			updateRequest.type("employee");
			updateRequest.id("3");
			updateRequest.doc(jsonBuilder().startObject()
					.field("gender", "male").endObject());
			UpdateResponse resp = transportClient.update(updateRequest).get();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	public static void deleteDocument() {
		TransportClient transportClient = getTransportClient();
		DeleteResponse response = transportClient
				.prepareDelete("megacorp", "employee", "2").execute()
				.actionGet();
		System.out.println(response);
	}
	
	public static boolean deleteDocument(String type, String docId) {
		TransportClient transportClient = getTransportClient();
		DeleteResponse response = transportClient
				.prepareDelete(index, type, docId).execute()
				.actionGet();
		return response.isFound();
	}
	
	public static boolean updateDocument(String type, String docId, 
			String authoname, String category, String bookName, String publisher) {
		try {
			TransportClient transportClient = getTransportClient();
			UpdateRequest updateRequest = new UpdateRequest();
			updateRequest.index(index);
			updateRequest.type(type);
			updateRequest.id(docId);
			updateRequest.doc(jsonBuilder().startObject()
					.field("book_name", bookName)
					.field("auth_name", authoname)
					.field("category", category)
					.field("publisher", publisher)
					.endObject());
			UpdateResponse resp = transportClient.update(updateRequest).get();
			return resp.getVersion() > 0;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) throws Exception{
		// ELSearchConnectionUtil.getDocument();
		// ELSearchConnectionUtil.addDocument();
		// ELSearchConnectionUtil.deleteDocument();
		// ELSearchConnectionUtil.updateDocument();
		// ELSearchConnectionUtil.getResult();
		/*Book book = new Book();
		book.setBookId(Long.toString(System.currentTimeMillis()));
		book.setBookName("book"+book.getBookId());
		book.setAuthorName("author"+book.getBookId());
		book.setCategory("category"+book.getBookId());
		book.setContent("content"+book.getBookId());
		book.setDescription("desc"+book.getBookId());
		book.setPublisherName("publisher"+book.getBookId());
		ELSearchConnectionUtil.addDocument();*/
		
		SearchResponse response = ELSearchConnectionUtil.getResult("java",1, 10);
		String str = response.toString();
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		transportClient.close();
		//TODO: CLOSE ALL threads of client.
	}
}