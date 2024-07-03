package calculadora.br.com.cod3r.calc.visao;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import calculadora.br.com.cod3r.calc.modelo.Memoria;
import calculadora.br.com.cod3r.calc.modelo.MemoriaObservador;

@SuppressWarnings("serial")
public class Display extends JPanel implements MemoriaObservador {

	private final JLabel label;

	public Display() {

		Memoria.getInstancia().addObservador(this);
		setBackground(Color.darkGray);
		label = new JLabel(Memoria.getInstancia().getTextoAtual());
		label.setForeground(Color.WHITE);
		label.setFont(new Font("courier", Font.PLAIN, 30));
	
		setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 25));

		add(label);
	}

	@Override
	public void valorAlterado(String novoValor) {
		label.setText(novoValor);

	}
}
