package test;

import java.util.concurrent.Callable;
import java.lang.*;
import stm.*;

class Test {

	public static void main (String args[]) throws Exception{

		/**
			A producer/consumer example where the producer deposits a value in the account
			and the consumer withdraws the same value, calling retry when the account balance 
			is not enough  
			args[0] > the number of producers/consumers
			args[1] > the number of operations that each producer/consumers performs
		*/
		final int NUMBER_OF_THREADS = Integer.parseInt(args[0]) * 2;
		final int NUMBER_OF_OPERATIONS = Integer.parseInt(args[1]);
		final Double INITIAL_VALUE = 5000.0;


		Account account = new Account();
		Trans.atomic((Callable<java.lang.Void>) () -> { account.deposit(INITIAL_VALUE); return null;});

		Operation[] ops = new Operation[NUMBER_OF_THREADS];
		for(int i=0;i<NUMBER_OF_THREADS;i++){
			int opCode = (i%2==0) ? Operation.OP_CODE_DEPOSIT : Operation.OP_CODE_WITHDRAW;
			ops[i] = new Operation(opCode, account, NUMBER_OF_OPERATIONS);
		}
		long tempoInicial = System.currentTimeMillis();
		for(int i=0;i<NUMBER_OF_THREADS;i++){
			ops[i].start();
		}

		for(int i=0;i<NUMBER_OF_THREADS;i++){
			ops[i].join();
		}	

		long tempoFinal = System.currentTimeMillis();  
		System.out.println( tempoFinal - tempoInicial );	
		System.out.println( Trans.atomic((Callable<Integer>) () -> {return account.getOps();}));

		assert(Trans.atomic((Callable<Integer>) () -> {return account.getOps();}) == (NUMBER_OF_OPERATIONS*NUMBER_OF_THREADS)+1);
		assert(Trans.atomic((Callable<Double>) () -> {return account.getBalance();}).equals(INITIAL_VALUE));
	}
}

class Operation extends Thread {

	public static final int OP_CODE_DEPOSIT = 1;
	public static final int OP_CODE_WITHDRAW = 2;

	private int code;
	private Account account;
	private int ops;

	Operation(int code, Account acc, int ops) {
		this.code = code;
		this.account = acc;
		this.ops = ops;
	}

	public void run() {
		switch(code){
			case OP_CODE_WITHDRAW: 
				for(int i=0;i<ops;i++){
					Trans.atomic((Callable<Void>) () -> {account.withdraw(1.); return null; });
				}
				break;
			case OP_CODE_DEPOSIT: 
				for(int i=0;i<ops;i++){
					Trans.atomic((Callable<Void>) () -> {account.deposit(1.);return null;});
				}
				break;
			default: break;
		}
	}
}
