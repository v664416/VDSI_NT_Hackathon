package org.rongubombu.factory;

import org.rongubombu.dbprocessors.DBSearchProcessor;
import org.rongubombu.interfaces.SearchProcessor;

public class RBFactory {
	public static SearchProcessor getSearchProcessor(){
		return new DBSearchProcessor();
	}
}
