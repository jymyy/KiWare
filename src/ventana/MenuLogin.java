package ventana;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import controlador.Controlador;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MenuLogin extends JFrame {
	
	private static final long serialVersionUID = 1L;
	/**
	* Se pone variables basicos utilizados en clase de MenuLogin
	*/
	
	JTextField usuario = new JTextField(1);
	JPasswordField contrasena = new JPasswordField(1);
	
	JPanel panel;
	MenuLogin menulogin;
	DefaultCaret caret;
	Controlador controlador = new Controlador();

	
	public static String TAG = "GUI";
	
	
	
	// Usuario ha pulsado bot�n entrar
	
	class Entrar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String usuariotext = usuario.getText();
			char[] contrasenatext = contrasena.getPassword();
			char[] empty = {};
			
			if (usuariotext.equals("") || Arrays.equals(contrasenatext, empty)) {
				JOptionPane.showMessageDialog(null, "Debes introducir tu usuario y contraseña para poder acceder.");
			}
			else {
				if (controlador.login(usuariotext, contrasenatext)) {
				// depende de return, si no encontran combinaci�n de usuario y contrase�a no acceden a 
				// menu principal
				try {
					new MenuPrincipal();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				menulogin.setVisible(false);
				}
				else {
					JOptionPane.showMessageDialog(null, "El usuario o la contraseña no son correctos.");
				}
		}
		}
	}

	public MenuLogin() throws IOException {
		// Se pone frame y panel principal
		menulogin = this;

		setTitle("Almacén de Deleite GOURMET v1.0");
		setSize(1150,600);
		setLocationRelativeTo(null);
		panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		// Se pone exit listener
		addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
		System.exit(0);
		}
		});
		
		// Se pone labels de usuario y contrase�a
		JLabel textLabel1 = new JLabel();
		JLabel textLabel2 = new JLabel();
		
		textLabel1.setBounds(475,380,100,20);
		textLabel1.setText("Usuario");
		textLabel2.setBounds(475,410,100,20);
		textLabel2.setText("Contraseña");
		
		usuario.setBounds(575,380,100,20);
		contrasena.setBounds(575,410,100,20);

		panel.add(textLabel1);
		panel.add(usuario);
		panel.add(textLabel2);
		panel.add(contrasena);
		
		// Se pone entrar bot�n
		JButton entrar = new JButton("Entrar");
		entrar.setBounds(525, 450, 100, 30);
		entrar.addActionListener(new Entrar());
		panel.add(entrar);
		entrar.setToolTipText("Entrar aplicación con usuario");
		Color myColor = Color.decode("#70DB70");
		panel.setBackground(myColor);

		// Se pone imagen
		//BufferedImage myPicture = ImageIO.read(getClass().getResource("Z:/Desktop/Heur/Interfaz/imagenes/LogoLacteos.jpg"));
		BufferedImage myPicture = ImageIO.read(new File("imagenes/LogoLacteos.jpg"));
		Image myPicture1 = myPicture.getScaledInstance(400, 200, Image.SCALE_DEFAULT);
		JLabel picLabel = new JLabel(new ImageIcon(myPicture1));
		panel.add(picLabel);
		picLabel.setBounds(400, 100, 400, 200);
		
		this.add(panel);
		this.setVisible(true);

		

	}
	
	public static void main(String[] args) throws IOException {
		MenuLogin menu = new MenuLogin();
	}
	
}