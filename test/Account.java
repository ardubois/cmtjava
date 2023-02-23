package test;

import stm.*;

class Account extends TAccount{

	public void deposit (Double n) throws Exception {
		
		
			Double balance = this.getBalance();
			this.setBalance(balance + n);
			Integer ops = this.getOps();
			this.setOps(ops+1);
		
	}

	public void withdraw (Double n) throws Exception{
		
		

			Integer ops = this.getOps();
			this.setOps(ops+1);
			Double balance = this.getBalance();

			if(balance>=n)
				this.setBalance(balance - n);
			else
				Trans.retry();
		
	}

	public void transfer(Account dest, Double n) throws Exception{
	
		
			this.withdraw(n);
			dest.deposit(n);
	
	}
}
