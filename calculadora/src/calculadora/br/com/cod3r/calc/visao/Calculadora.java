package calculadora.br.com.cod3r.calc.visao;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Calculadora extends JFrame {

	public Calculadora() {

		organizarLayout();

//		setUndecorated(true); some com a barra para você criar a sua 

		setSize(232, 322);
		setVisible(true); // abre a janela
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE); // sai da aplicação/processo finalizado
	}

	private void organizarLayout() {
		setLayout(new BorderLayout());

		Display display = new Display();
		display.setPreferredSize(new Dimension(233, 60));
		add(display, BorderLayout.NORTH);

		Teclado teclado = new Teclado();
		add(teclado, BorderLayout.CENTER);

	}

	public static void main(String[] args) {
		new Calculadora();
	}

}
