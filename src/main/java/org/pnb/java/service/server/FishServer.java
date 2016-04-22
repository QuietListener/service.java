package org.pnb.java.service.server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.pnb.java.service.thrift.FinshingHandler;
import org.pnb.java.service.thrift.Fishing;
public class FishServer {

	  public static FinshingHandler handler;

	  public static Fishing.Processor<FinshingHandler> processor;

	  @SuppressWarnings("unchecked")
	  public static void main(String [] args) {
	    try {
	      handler = new FinshingHandler();
	      processor = new  Fishing.Processor<FinshingHandler>(handler);

	      Runnable simple = new Runnable() {
	        public void run() {
	          simple(processor);
	        }
	      };      
	     
	      new Thread(simple).start();
	    } catch (Exception x) {
	      x.printStackTrace();
	    }
	  }

	  public static void simple(Fishing.Processor<FinshingHandler> processor) {
	    try {
	      TServerTransport serverTransport = new TServerSocket(9090);
	      TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

	      System.out.println("Starting the simple server...");
	      server.serve();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }	 
}