package com.jd.db.transfer.core.database;

import java.sql.Connection;
import java.util.Stack;
import java.util.Vector;

import com.jd.db.transfer.core.common.JdbcUtil;
import com.jd.db.transfer.core.pojo.DatabaseBean;
import com.jd.db.transfer.core.pojo.DatabaseConfig.DatabaseResource;
  
/**
 * 功能：
 * 
 * @author miaojundong
 *
 */
public class ConnectionPool {  
	
	private static final ConnectionPool instance = new ConnectionPool();
	
	private DatabaseBean databaseFrom;
	private DatabaseBean databaseTo;
	
    private Stack<Connection> connectionsFrom = new Stack<Connection>();
    private Stack<Connection> connectionsTo = new Stack<Connection>();
    private Vector<Connection> created = new Vector<Connection>();
    
    static ConnectionPool newInstance(DatabaseBean databaseFrom, DatabaseBean databaseTo) {
    	instance.setDatabaseFrom(databaseFrom);
    	instance.setDatabaseTo(databaseTo);
		return instance;
	}
    
    synchronized void init(int initCount) {
    	if(initCount > 0) {
    		for(int i = 0; i < initCount; i++) {
    			Connection connection = doCreateConnection(DatabaseResource.FROM);
    			recycle(connection, DatabaseResource.FROM);
    			connection = doCreateConnection(DatabaseResource.TO);
    			recycle(connection, DatabaseResource.TO);
    		}
    	}
	}
	
    synchronized Connection getConnectionOfFrom() {
        synchronized (connectionsFrom) {
        	 while (connectionsFrom.size() <= 0) {
        		 try {
     				this.wait();
     			} catch (InterruptedException e) {
     				e.printStackTrace();
     			}
        	 }
        	 return ((Connection) connectionsFrom.pop());
        }
    }
    
    synchronized Connection getConnectionOfTo() {
    	synchronized (connectionsTo) {
       	 while (connectionsTo.size() <= 0) {
       		 try {
    				this.wait();
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
       	 }
       	 return ((Connection) connectionsTo.pop());
       }
    }
    
    synchronized void recycle(Connection connection, DatabaseResource databaseResource) {
    	if(databaseResource == DatabaseResource.FROM) {
    		connectionsFrom.push(connection);
    	} else if(databaseResource == DatabaseResource.TO) {
    		connectionsTo.push(connection);
    	}
    	this.notifyAll();
    }
    
    synchronized void destory() {
    	for (int i = created.size() - 1; i >= 0; i--) {
    		Connection connection = created.elementAt(i);
            try {
            	JdbcUtil.close(connection);;
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
    }
    
    private Connection doCreateConnection(DatabaseResource databaseResource) {
    	Connection connection = null;
    	if(databaseResource == DatabaseResource.FROM) {
    		connection = JdbcUtil.getDbConnection(databaseFrom);
    	} else if(databaseResource == DatabaseResource.TO) {
    		connection = JdbcUtil.getDbConnection(databaseTo);
    	}
    	created.addElement(connection);
    	
        return connection;
    }
    
	private ConnectionPool() {
	}
	
	private void setDatabaseFrom(DatabaseBean databaseFrom) {
		this.databaseFrom = databaseFrom;
	}

	private void setDatabaseTo(DatabaseBean databaseTo) {
		this.databaseTo = databaseTo;
	}
}  