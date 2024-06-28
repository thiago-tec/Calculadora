package calculadora.br.com.cod3r.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {

	private enum TipoComando{
		ZERAR,NUMERO,DIV,MULT,SUB,SOMA,IGUAL,VIRGULA;
	};
	
	private static Memoria instancia = new Memoria();

	private TipoComando ultimaOperacao = null;
	private boolean substituir = false;
	private String textoAtual = "";
	private String textoBuffer = "";

	private final List<MemoriaObservador> observadores = new ArrayList<>();

	private Memoria() {

	}

	public static Memoria getInstancia() {
		return instancia;
	}

	public void addObservador(MemoriaObservador observador) {
		observadores.add(observador);
	}

	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}

	public void processarComando(String texto) {
		
		TipoComando tipoComando = detectarTipocomando(texto);
		
//		System.out.println(tipoComando);
		
		if(tipoComando == null) {
			return;
		}else if(tipoComando == TipoComando.ZERAR) {
			textoAtual = "";
			textoBuffer = "";
			substituir = false;
			ultimaOperacao = null;
		}else if(tipoComando == TipoComando.NUMERO 
				||tipoComando == TipoComando.VIRGULA) {
			textoAtual = substituir ? texto : textoAtual + texto;
			substituir = false;
		}else {
			textoAtual = obterResultadoOperacao();
			substituir = true;
			textoBuffer = textoAtual;
			ultimaOperacao = tipoComando;
		}
			
		
		observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
	}

	private String obterResultadoOperacao() {
		if(ultimaOperacao == null) {
			return textoAtual;
		}
		double numeroBuffer = Double.parseDouble(textoBuffer.replace(",", "."));
		double numeroAtual = Double.parseDouble(textoAtual.replace(",", "."));
		
		double resultado = 0;
		
		if(ultimaOperacao == TipoComando.SOMA) {
			numeroBuffer = numeroBuffer + numeroAtual;
		}else if(ultimaOperacao == TipoComando.SUB) {
			numeroBuffer = numeroBuffer - numeroAtual;
		}else if(ultimaOperacao == TipoComando.MULT) {
			numeroBuffer = numeroBuffer * numeroAtual;
		}else if(ultimaOperacao == TipoComando.DIV) {
			numeroBuffer = numeroBuffer / numeroAtual;
		}
		
		String resultadoString = Double.toString(resultado).replace(".", ",");
		
		return resultadoString;
	}

	private TipoComando detectarTipocomando(String texto) {
		
		if(textoAtual.isEmpty() && texto == "0") {
			return null;
		}
		
		try {
			Integer.parseInt(texto);
			return TipoComando.NUMERO;
		} catch (NumberFormatException e) {
			//quando não for number
			
			if("AC".equals(texto)) {
				return TipoComando.ZERAR;
			}else if ("/".equals(texto)) {
				return TipoComando.DIV;
			}else if ("*".equals(texto)) {
				return TipoComando.MULT;
			}else if ("+".equals(texto)) {
				return TipoComando.SOMA;
			}else if ("-".equals(texto)) {
				return TipoComando.SUB;
			}else if ("=".equals(texto)) {
				return TipoComando.IGUAL;
			}else if (",".equals(texto) 
					&& !textoAtual.contains(",")) {
				return TipoComando.VIRGULA;
			}
			
		}
		
		return null;
	}

}
