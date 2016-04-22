package org.pnb.java.service.thrift;

import org.apache.thrift.TException;
import org.pnb.java.utils.Utils;

public class FinshingHandler implements Fishing.Iface {

	public Result getfishes(String url, int timeout2) throws TException {
		
		Result r = new Result();
		try {
			Utils.FResponse response = Utils.getResponse(url, timeout2);
			r.setStatus(response.status);
			r.setContent(response.content);
		} catch (Exception e) {
			e.printStackTrace();
			r.setStatus(-1);
		}
		
		return r;
	}

	

}
