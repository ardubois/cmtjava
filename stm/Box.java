package stm;

import java.util.*;

public class Box <A> {
	private Conteiner<A> box;
    private FieldInfo<A> boxFieldInfo; 
	


	public Box(A conteudo){
		this.box = new Conteiner (conteudo);
        this.boxFieldInfo = new FieldInfo<A> ("box", (A a) -> {box.setConteudo(a); return null;});
	}
	
	public void set (A n) throws Exception {
		
	
            Trans t = Trans.local.get();
			
			boolean mark[] = {false};
			Long version = boxFieldInfo.wlock.get(mark);
		    if(mark[0] && t.transId.equals(version)){
		    	assert(boxFieldInfo.wlock.isMarked() && boxFieldInfo.wlock.getReference().equals(t.transId));
				t.writeSet.put(boxFieldInfo, n);
				//r = new TResult(new stm.Void(), t, Trans.Status.ACTIVE);
			}else{
				if(boxFieldInfo.wlock.compareAndSet(null, t.transId, false, true)){
					t.writeSet.put(boxFieldInfo, n);
					if (boxFieldInfo.rlock.getReference() > t.validationStamp && !t.extend()){
                        t.status = Trans.Status.ABORTED;
						throw new Exception();// throw exception r = new TResult(null, t, Trans.Status.ABORTED);
					}//else
						//r = new TResult(new stm.Void(), t, Trans.Status.ACTIVE);
					}
				else{
                    t.status = Trans.Status.ABORTED;
					throw new Exception();//r = new TResult(null, t, Trans.Status.ABORTED);
				}
			}		
			//return r;
		
	}

   public A get() throws Exception {
		
		    Trans t = Trans.local.get();
			
			A result;
			boolean mark[] = {false};
			Long version = boxFieldInfo.wlock.get(mark);
			
	    	if(mark[0] && t.transId.equals(version)){
	    		assert(boxFieldInfo.wlock.isMarked() && boxFieldInfo.wlock.getReference().equals(t.transId));
				result = (A)t.writeSet.get(boxFieldInfo);
				//r = new TResult(result, t, Trans.Status.ACTIVE);
			}else{
		   		version = boxFieldInfo.rlock.get(mark);
				while(true){
					if(mark[0]==true){
						version = boxFieldInfo.rlock.get(mark);
						continue;
					}

					result = box.getConteudo();
					long version2 = boxFieldInfo.rlock.get(mark);
					if(version == version2 && mark[0]==false) break;
					version=version2;
				}

				boolean added = t.readSet.put(boxFieldInfo, version);
				if (!added || (version > t.validationStamp && !t.extend()) ){
					t.status = Trans.Status.ABORTED;
					throw new Exception();
				}
			}	

			return result;
		
	} 


	//public A getConteudo(){return this.conteudo;}
	//public void setConteudo(A conteudo){this.conteudo = conteudo;}
}
