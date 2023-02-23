package test;

import stm.*;
import java.util.concurrent.Callable;

class TestAccount{
  
   public static void main(String [] args) throws Exception
   {
   
     Account ac1 = new Account ();
     Account ac2 = new Account ();
     
     Callable<Void> trans = () -> {
       ac1.setBalance(200.0);
       ac2.setBalance(100.0);
       return null;
     };
     
     Trans.atomic(()->{ac1.deposit(100.);return null;});
     Trans.atomic(trans);
   }

}
