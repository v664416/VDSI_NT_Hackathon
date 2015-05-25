package org.rongubombu.interfaces;

import java.util.List;
import java.util.Map;

public interface SearchProcessor {
	List<Map<String,Object>> getQueryResult(String query) throws Exception;
}
