package stm;

public class Block{

	/**
	(13:14:22) dubie: .block() e unblock()
	(13:14:41) dubie: se eu chamar block, fico bloqueado ateh alguem chamar unblock
	(13:15:44) dubie: se eu chamar unblock e niguem chamaou block ainda, entao eu fico  bloqueado ateh alguem chamar o block
	(13:15:50) dubie: agora vem o mais dificil:
	(13:16:37) dubie: se o unblock ja foi chamado e a thread que tinha chamado o block ja foi liberada, quando alguem chamar o unblock de novo nao fica bloqueado
	*/

	private boolean hasUnblock, hasBlock;

    Block(){
		hasUnblock = false;
		hasBlock = false; 
    }

    /** @mem blocks till somebody calls unblock(), if unblock() hasn't been called yet */
	synchronized void block(){

		hasBlock=true;
		if (!hasUnblock){
			try{
				wait();
			}catch(Exception e) {System.out.println(e);}
		}
		try{
			notifyAll();
		}catch(Exception e) {System.out.println(e);}

	}

    synchronized void unblock(){

		hasUnblock = true;
		if (!hasBlock){
			try{
					wait();
				}catch(Exception e) {System.out.println(e);}
			} else {
			try{
				notifyAll();
			}catch(Exception e) {System.out.println(e);}
		}
    }

}
