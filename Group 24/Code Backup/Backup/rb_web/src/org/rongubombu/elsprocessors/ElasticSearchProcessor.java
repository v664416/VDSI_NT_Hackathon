package org.rongubombu.elsprocessors;

import java.util.List;
import java.util.Map;

import org.rongubombu.elasticsearch.ELSearchConnectionUtil;
import org.rongubombu.interfaces.SearchProcessor;

public class ElasticSearchProcessor implements SearchProcessor{

	/*public String getQueryResult(String query) throws Exception{
		return ELSearchConnectionUtil.getDocument(query);
	}*/
	
	
	public List<Map<String,Object>> getQueryResult(String query) throws Exception {
		return ELSearchConnectionUtil.getResult(query);
	}
}
