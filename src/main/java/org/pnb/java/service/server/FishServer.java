package org.pnb.java.service.server;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.pnb.java.service.thrift.FinshingHandler;
import org.pnb.java.service.thrift.Fishing;
public class FishServer {

	  public static FinshingHandler handler;
	  public static final int PORT = 9090;

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
//	      TServerTransport serverTransport = new TServerSocket(9090);
//	      TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
	    	
	    	TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(PORT);
			TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);
			tArgs.maxReadBufferBytes = Integer.MAX_VALUE;
			tArgs.selectorThreads(10);
			tArgs.workerThreads(10);
			tArgs.processor(processor);
			tArgs.protocolFactory(new TCompactProtocol.Factory());
			TServer server = new TThreadedSelectorServer(tArgs);

	      System.out.println("Starting the simple server...");
	      server.serve();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }	 
}