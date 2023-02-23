package stm;

import java.util.*;

public class Conteiner <A> {
	
	volatile A conteudo;

	public Conteiner(A conteudo){
		this.conteudo = conteudo;
	}
	
	public Conteiner(){
		this.conteudo = null;
	}

	public A getConteudo(){return this.conteudo;}
	public void setConteudo(A conteudo){this.conteudo = conteudo;}
}
