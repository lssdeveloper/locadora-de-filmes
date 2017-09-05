package br.rj.lssdeveloper.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import br.rj.lssdeveloper.entidades.Usuario;

public class AssertTest {

	@Test
	public void test() {

		/*
		 * Verificando expressões Boolean
		 */
		assertTrue(true);
		assertFalse(false);
		
		/*
		 * Fazendo Comparações 
		 */
		
		assertEquals(1, 1);
		/*
		 * Mensagem nas assertivas em caso de erro
		 * 			//assertEquals("Erro de Comparação", 1, 2);
		 * Não é obrigatório a mensagem, sendo o contexto de uso livre.
		 */
		assertNotEquals(1, 2);
		
		/*
		 * Comparando Double (uso de delta)
		 */
		
		//com delta de comparação quando for double
		//delta = 0.001 representa uma margem de erro = 0.51234  )
		assertEquals(0.51, 0.5, 0.1);
		//ou 
		assertEquals(0.512, 0.51, 0.01);
		//ou 
		assertEquals(0.5123, 0.512, 0.001);
		//se fizer assim o teste quebra
			//assertEquals(0.5123, 0.51, 0.001); 
		//veja o valor de PI
		assertEquals(Math.PI, 3.14, 0.1);
		assertEquals(Math.PI, 3.14, 0.01);	
		
		/*
		 * Comparando Inteiros
		 */
		
		//wrapper
		int i =  5;
		Integer i2 = 5;
		//converter para um Tipo Integer
		assertEquals(Integer.valueOf(i) , i2);
		//converter para um Tipo primitivo
		assertEquals(i , i2.intValue());
		
		/*
		 * Comparando String
		 */
		assertNotEquals("bola", "Bola");
		assertEquals("bola", "bola");
		assertTrue("bola".equalsIgnoreCase("Bola"));
		//assim quebra
			//assertEquals("Erro de Comparação", "bola", "Bola");
		//comparando com o radical
		assertTrue("bola".startsWith("bo"));
		
		/*
		 * Comparando Objetos e Instância
		 */
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		
		//quebra pois expected:Usuario@443b7951 but was:Usuario@14514713
		//isto aconteceu porque a classe Usuario não implementou o Hashcode e o Equals herdando da superclasse Object  
			//assertEquals("Erro de comparação", u1, u2);  //mas como comparar neste caso?
		//implementado o método equals na classe Usuario 
		assertEquals(u1, u2);
		
	
		//Como garantir que um objeto é da mesma instância?
			//assertSame(u1, u2);  //quebra
		assertNotSame(u1, u2);//sim são diferentes as instâncias
		
		//passa pq é a mesma instância
		assertEquals(u1, u1);
		//ou
		assertSame(u1, u1);
		
		/*
		 * Comparando instâncias
		 */

		Usuario u3 = u2;
		//então agora sim!
		assertSame(u3, u2);
		
		/*
		 * Use assertSame para comparar instâncias 
		 * e   assertEquals para conteúdo de objetos com (Hashcode e Equals)
		 */
		//E se um objeto estiver null?
		Usuario u4 = null;		
		assertNull(u4);
		
		assertNotNull(u2);
		
		/*
		 * AssertThat (Algo como: verifique que)
		 * É um método mais genérico!
		 * Motivo:
		 * 1-Posso fazer a verificação que eu quiser, sendo mais abragente.
		 * 2-Leitura do método mais fluido (Fluent Interface - Martin Fowler)
		 * 3-Apresenta um código mais organizado
		 */
		//usando o assertThat
		//Veja como fica mais fluido o código dando um sentido a frase
			//  verifique que valor da locação é 15.0
			//assertThat(locacao.getValor(), is(15.0));
	}

}
