package org.rongubombu.elsprocessors;

import org.rongubombu.elasticsearch.ELSearchConnectionUtil;
import org.rongubombu.interfaces.SearchProcessor;

public class ElasticSearchProcessor implements SearchProcessor{

	public String getQueryResult(String query) throws Exception{
		return ELSearchConnectionUtil.getDocument();
	}
}
