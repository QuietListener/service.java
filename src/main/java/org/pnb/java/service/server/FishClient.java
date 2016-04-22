package org.pnb.java.service.server;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.pnb.java.service.thrift.Fishing;
import org.pnb.java.service.thrift.Result;


public class FishClient {
	  public static void main(String [] args) {	   
	    try {
	      TTransport transport = new TSocket("127.0.0.1", 9090);
	      transport = new TFramedTransport(transport);
	      transport.open();
			
	      TProtocol protocol = new TCompactProtocol(transport);
	      Fishing.Client client = new Fishing.Client(protocol);

	      perform(client);

	      transport.close();
	    } catch (Exception x) {
	      x.printStackTrace();
	    } 
	  }

	  private static void perform(Fishing.Client client) throws TException
	  {
	   
	    Result r = client.getfishes("http://www.baidu.com", 10);
	    System.out.println(r);
	  }
	}