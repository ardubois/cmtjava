package test;

import stm.*;

class TAccount{

	private Box<Double> balance;
	private Box<Integer> ops; 
	
	TAccount (Double b)
	{
	   balance =  new Box<Double>(b);
	   ops =  new Box<Integer>(0);
	}
	
	TAccount ()
	{
	   balance =  new Box<Double>(0.);
	   ops =  new Box<Integer>(0);
	}


	public void setBalance (Double n) throws Exception{
	
	    balance.set(n); 
        }
        
        public Double getBalance () throws Exception{
	
	    return balance.get();
        }
        public void setOps (Integer n) throws Exception{
	
	    ops.set(n); 
        }
        
        public Integer getOps () throws Exception{
	
	    return ops.get();
        }
}


