package ventana;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

import controlador.Controlador;
import modelo.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuPrincipal extends JFrame {
	

	private static final long serialVersionUID = 1L;
	
	JPanel panel;
	JScrollPane dirwinLacteo;
	JScrollPane dirwinPrima;
	JScrollPane dirwin;
	JLabel dirText;
	
	JTextPane primaPane;
	JLabel primaText;
	JScrollPane primaScroll;
	
	/*JTextPane lacteoPane;
	JLabel lacteoText;
	JScrollPane lacteoScroll;*/
	
	
	
	MenuPrincipal menuprincipal;
	
	DefaultCaret caret;
	
	public static String TAG = "GUI";
	
	Controlador controlador = new Controlador();
	
	
	// Para ense�ar los productos en las tablas
	
	public void productos() {
		
		JLabel textLabel1 = new JLabel();
		JLabel textLabel2 = new JLabel();
		
		textLabel1.setBounds(100,130,150,20);
		textLabel1.setText("Productos lácteos");
		textLabel2.setBounds(600,130,100,20);
		textLabel2.setText("Materias Primas");
		
		panel.add(textLabel1);
		panel.add(textLabel2);
		
		// Se pone tabla de materias primas
		
		Object[] columnNames = {"ID", "Nombre", "Caducidad", "Entrada", "Refrigeración", "Cantidad/Capacidad"};
		Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        final JTable table = new JTable(model) {

        private static final long serialVersionUID = 1L;

        public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    case 4:
                    	return String.class;
                    default:
                        return String.class;
                }
            }
        public boolean isCellEditable(int row, int col) {
            return false;
        }
        };
        
        table.addMouseListener( new MouseAdapter()
        {
        	public void mousePressed( MouseEvent e )
        	{
        		// Cuando pulsamos el boton derecho
        		if ( SwingUtilities.isRightMouseButton( e ) )
        		{
        			Point p = e.getPoint();
        			int rowNumber = table.rowAtPoint( p );
        			ListSelectionModel model = table.getSelectionModel();
        			model.setSelectionInterval( rowNumber, rowNumber );
        		}
        	}
        });
        
	    for (int i=0;i<controlador.ListMP.size();i++) {
	    	MateriaPrima m = controlador.ListMP.get(i);
	    	String refrigerado ="no";
			 if(m.isRefrigerar()){
				 refrigerado = "si";
			 }
	    	model.addRow(new Object[]{m.getId(), m.getNombre(), m.getEntrada(), m.getCaducidad(),refrigerado,controlador.getnumMatPrimas() + " / " +
	    	controlador.getCapacidadMatPrimas()});
        
	    }
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(600, 150, 400, 300);
		panel.add(scrollPane);
		
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem entregar = new JMenuItem("Entregar a fabricación");
        // Usuario ha pulsado bot�n de "Entregar a fabricaci�n"
        entregar.addActionListener(new ActionListener() {
			int id;
			String nametext;
			String entradatext;
			String caducidadtext;
			String cancap;
			String cantidad = "";
			
			public void actionPerformed(ActionEvent e) {
				int selectedRowIndex = table.getSelectedRow();

				for (int i=0;i<6;i++) {
					Object o = table.getModel().getValueAt(selectedRowIndex, i);
					if (i==0) {
						id = Integer.parseInt(o.toString());
					}
					if (i==1) {
						nametext = o.toString();
					}
					if (i==2) {
						entradatext = o.toString();
					}
					if (i==3) {	
						caducidadtext = o.toString();
					}
					if (i==4) {
						
					}
					if (i==5) {
						cancap = o.toString();
						int j = 0;
						while (cancap.charAt(j) != '/') {
							cantidad += cancap.charAt(j);
							j++;
						}
					}
				}
				// NECESITAMOS M�S DE LOS DATOS PARA SOLICITUD??! QU� INCLUYE DESCRIPCI�N??
				controlador.entregarFabricacionDeLista(id, cantidad);
				// TENEMOS QUE "UPDATE" LAS TABLAS DE PRODUCTOS EN INTERFAZ. DEBER�A QUE FUNCIONAR CON ESTA LINEA S�LO?
				productos();
			}
        	
        });
        
        popupMenu.add(entregar);
        table.setComponentPopupMenu(popupMenu);
		
		
		// Se pone lista de productos lacteos
		Object[] columnNames2 = {"ID", "Nombre", "Fecha de caducidad", "Tipo", "Formato", "Cantidad/Capacidad"};
		Object[][] data2 = {};
        DefaultTableModel model2 = new DefaultTableModel(data2, columnNames2);
        final JTable table2 = new JTable(model2) {

        private static final long serialVersionUID = 1L;

        public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    case 4:
                    	return String.class;
                    default:
                        return String.class;
                }
            }
        public boolean isCellEditable(int row, int col) {
            return false;
        }
        };
        
        table2.addMouseListener( new MouseAdapter()
        {
        	public void mousePressed( MouseEvent e )
        	{
        		if ( SwingUtilities.isRightMouseButton( e ) )
        		{
        			Point p = e.getPoint();
        			int rowNumber = table2.rowAtPoint( p );
        			ListSelectionModel model = table2.getSelectionModel();
        			model.setSelectionInterval( rowNumber, rowNumber );
        		}
        	}
        });
        JPopupMenu popupMenu2 = new JPopupMenu();
        JMenuItem exportar = new JMenuItem("Exportar a ventas");
        popupMenu2.add(exportar);
        exportar.addActionListener(new ActionListener() {
			int id;
			String nametext;
			String caducidadtext;
			String tipotext;
			String formatotext;
			String cancap;
			String cantidad = "";
			
			public void actionPerformed(ActionEvent e) {
				int selectedRowIndex = table2.getSelectedRow();

				for (int i=0;i<6;i++) {
					Object o = table2.getModel().getValueAt(selectedRowIndex, i);
					if (i==0) {
						id = Integer.parseInt(o.toString());
					}
					if (i==1) {
						nametext = o.toString();
					}
					if (i==2) {
						caducidadtext = o.toString();
					}
					if (i==3) {
						tipotext = o.toString();
					}
					if (i==4) {
						formatotext = o.toString();
					}
					if (i==5) {
						cancap = o.toString();
						int j = 0;
						while (cancap.charAt(j)!='/'){
							cantidad += cancap.charAt(j);
							j++;
						}
						
					}
				}
				//CU�LES DATOS NECESITAMOS A SOLICITUD??! QU� INCLUYE DESCRIPCI�N?
				controlador.exportarVentasDeLista(id, cantidad);
				// TENEMOS QUE "UPDATE" LAS TABLAS DE PRODUCTOS EN INTERFAZ. DEBER�A QUE FUNCIONAR CON ESTA LINEA S�LO?
				productos();
			}
        	
        });
        table2.setComponentPopupMenu(popupMenu2);
        
	    for (int i=0;i<controlador.ListPL.size();i++) {
	    	ProductoLacteo p = controlador.ListPL.get(i);
	    	model2.addRow(new Object[]{p.getId(), p.getNombre(), 
	    			p.getCaducidad(),p.getTipo(), p.getFormato(), controlador.getnumLacteos() + "/" + 
	    	controlador.getCapacidadLacteos()});
	    }
        
        table2.setPreferredScrollableViewportSize(table2.getPreferredSize());
        JScrollPane scrollPane2 = new JScrollPane(table2);
		scrollPane2.setBounds(100, 150, 400, 300);
		panel.add(scrollPane2);
		
	}
	
	// Usuario ha pulsado bot�n de "Entregar a fabricaci�n"
	
	class EntregarFabricacion implements ActionListener {
		JTextField id = new JTextField(1);
		JTextField nombre = new JTextField(1);
		JTextField cantidad = new JTextField(1);
		JTextField fecha = new JTextField(1);
		JTextField descripcion = new JTextField(1);
		
		public void actionPerformed(ActionEvent e) {

			JLabel textLabel1 = new JLabel();
			JLabel textLabel2 = new JLabel();
			JLabel textLabel3 = new JLabel();
			JLabel textLabel4 = new JLabel();
			JLabel textLabel5 = new JLabel();

			textLabel1.setText("ID");
			textLabel2.setText("Nombre");
			textLabel3.setText("Cantidad");
			textLabel4.setText("Fecha");
			textLabel5.setText("Descripci�n");
			JPanel connectPanel = new JPanel();

			connectPanel.setLayout(new GridLayout(5,2));
			connectPanel.add(textLabel1);
			connectPanel.add(id);
			connectPanel.add(textLabel2);
			connectPanel.add(nombre);
			connectPanel.add(textLabel3);
			connectPanel.add(cantidad);
			connectPanel.add(textLabel4);
			connectPanel.add(fecha);
			connectPanel.add(textLabel5);
			connectPanel.add(descripcion);
			
			String idtext = id.getText();
			String nombretext = nombre.getText();
			String cantidadtext = cantidad.getText();
			String fechatext = fecha.getText();
			String descripciontext = descripcion.getText();
			
			int result = JOptionPane.showConfirmDialog(null, connectPanel,"Entregar a fabricaci�n", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {

			try {
				// Los cambios en Controlador!!
				controlador.entregarFabricacion(idtext, nombretext, cantidadtext, fechatext, descripciontext);
				// TENEMOS QUE "UPDATE" LAS TABLAS DE PRODUCTOS EN INTERFAZ. DEBER�A QUE FUNCIONAR CON ESTA LINEA S�LO?
				productos();
				JOptionPane.showMessageDialog(null, "Entregar completa.");
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Encontraron un falso en los datos.");
			}
			}
			

			};
		
	}
	
	// Usuario ha pulsado bot�n de "Exportar a ventas"
	
	class ExportarVentas implements ActionListener {
		JTextField id = new JTextField(1);
		JTextField nombre = new JTextField(1);
		JTextField cantidad = new JTextField(1);
		JTextField fecha = new JTextField(1);
		JTextField descripcion = new JTextField(1);
		
		public void actionPerformed(ActionEvent e) {

			JLabel textLabel1 = new JLabel();
			JLabel textLabel2 = new JLabel();
			JLabel textLabel3 = new JLabel();
			JLabel textLabel4 = new JLabel();
			JLabel textLabel5 = new JLabel();
			
			textLabel1.setText("ID");
			textLabel2.setText("Nombre");
			textLabel3.setText("Cantidad");
			textLabel4.setText("Fecha");
			textLabel5.setText("Descripci�n");
			JPanel connectPanel = new JPanel();

			connectPanel.setLayout(new GridLayout(5,2));
			connectPanel.add(textLabel1);
			connectPanel.add(id);
			connectPanel.add(textLabel2);
			connectPanel.add(nombre);
			connectPanel.add(textLabel3);
			connectPanel.add(cantidad);
			connectPanel.add(textLabel4);
			connectPanel.add(fecha);
			connectPanel.add(textLabel5);
			connectPanel.add(descripcion);
			
			String idtext = id.getText();
			String nombretext = nombre.getText();
			String cantidadtext = cantidad.getText();
			String fechatext = fecha.getText();
			String descripciontext = descripcion.getText();
			
			int result = JOptionPane.showConfirmDialog(null, connectPanel,"Exportar a ventas", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {

			try {
				// LOS CAMBIOS EN CONTROLADOR!!!
				controlador.exportarVentas(idtext, nombretext, cantidadtext, fechatext, descripciontext);
				// TENEMOS QUE "UPDATE" LAS TABLAS DE PRODUCTOS EN INTERFAZ. DEBER�A QUE FUNCIONAR CON ESTA LINEA S�LO?
				productos();
				JOptionPane.showMessageDialog(null, "Exportar completa.");
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Encontraron un falso en los datos.");
			}
			}
			

			};
		
	}
	
	// Usuario ha pulsado bot�n de "Agregar materia prima"
	
	class AgregarMateriaPrima implements ActionListener {
		JTextField nombre = new JTextField(1);
		JTextField caducidad = new JTextField(1);
		JTextField entrada = new JTextField(1);
		JTextField refrigeracion = new JTextField(1);

		public void actionPerformed(ActionEvent e) {

			JLabel textLabel1 = new JLabel();
			JLabel textLabel2 = new JLabel();
			JLabel textLabel3 = new JLabel();
			JLabel textLabel4 = new JLabel();
			JCheckBox siButton = new JCheckBox("S�");
			siButton.setMnemonic(KeyEvent.VK_C); 
		    siButton.setSelected(true);
			textLabel1.setText("Nombre");
			textLabel2.setText("Fecha de caducidad");
			textLabel3.setText("Fecha entrada");
			textLabel4.setText("Refrigeraci�n");
			JPanel connectPanel = new JPanel();

			
			connectPanel.setLayout(new GridLayout(4,2));
			connectPanel.add(textLabel1);
			connectPanel.add(nombre);
			connectPanel.add(textLabel2);
			connectPanel.add(caducidad);
			connectPanel.add(textLabel3);
			connectPanel.add(entrada);
			connectPanel.add(textLabel4);
			connectPanel.add(siButton);
			
			String nombretext = nombre.getText();
			String caducidadtext = caducidad.getText();
			String entradatext = entrada.getText();
			
			int result = JOptionPane.showConfirmDialog(null, connectPanel,"Agregar materia prima", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {

			try {
				// LOS CAMBIOS EN CONTROLADOR??!
				controlador.agregarMateriaPrima(nombretext, caducidadtext, entradatext, siButton.isSelected());
				// TENEMOS QUE "UPDATE" LAS TABLAS DE PRODUCTOS EN INTERFAZ. DEBER�A QUE FUNCIONAR CON ESTA LINEA S�LO?
				productos();
				JOptionPane.showMessageDialog(null, "Agregar completa.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Encontraron un falso en los datos.");
			}
			}
			

			};
		}
	
	// Usuario ha pulsado bot�n de "Agregar producto l�cteo"
	
	class AgregarProductoLacteo implements ActionListener {
		JTextField nombre = new JTextField(1);
		JTextField tipo = new JTextField(1);
		JTextField formato = new JTextField(1);
		JTextField caducidad = new JTextField(1);

		public void actionPerformed(ActionEvent e) {

			JLabel textLabel1 = new JLabel();
			JLabel textLabel2 = new JLabel();
			JLabel textLabel3 = new JLabel();
			JLabel textLabel4 = new JLabel();

			textLabel1.setText("Nombre");
			textLabel2.setText("Tipo");
			textLabel3.setText("Formato");
			textLabel4.setText("Fecha caducidad");
			JPanel connectPanel = new JPanel();

			
			connectPanel.setLayout(new GridLayout(4,2));
			connectPanel.add(textLabel1);
			connectPanel.add(nombre);
			connectPanel.add(textLabel2);
			connectPanel.add(tipo);
			connectPanel.add(textLabel3);
			connectPanel.add(formato);
			connectPanel.add(textLabel4);
			connectPanel.add(caducidad);
			
			String nombretext = nombre.getText();
			String tipotext = tipo.getText();
			String formatotext = formato.getText();
			String caducidadtext = caducidad.getText();
			
			int result = JOptionPane.showConfirmDialog(null, connectPanel,"Agregar producto lácteo", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				try {
					// LOS CAMBIOS EN CONTROLADOR?!
					controlador.agregarProductoLacteo(nombretext, tipotext, formatotext, caducidadtext);
					// TENEMOS QUE "UPDATE" LAS TABLAS DE PRODUCTOS EN INTERFAZ. DEBER�A QUE FUNCIONAR CON ESTA LINEA S�LO?
					productos();
					JOptionPane.showMessageDialog(null, "Producto agregado.");
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios.");
				}
			}
			};
		
	}
	
	
	// Usuario ha pulsado el bot�n de "Ver Peticiones"
	
	class VerPeticiones implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object[] columnNames = {"ID", "Departamento", "Fecha", "Descripci�n"};
			Object[][] data = {};
	        DefaultTableModel model = new DefaultTableModel(data, columnNames);
	        JTable table = new JTable(model) {

	        private static final long serialVersionUID = 1L;

	        public Class getColumnClass(int column) {
	                switch (column) {
	                    case 0:
	                        return Integer.class;
	                    case 1:
	                        return String.class;
	                    case 2:
	                        return Date.class;
	                    default:
	                        return String.class;
	                }
	            }
	        public boolean isCellEditable(int row, int col) {
	            return false;
	        }
	        };
	        
	    
		    for (int i=0;i<controlador.ListSolicitud.size();i++) {
		    	Solicitud s = controlador.ListSolicitud.get(i);
		    	model.addRow(new Object[]{s.getId(), s.getDepartamento(), s.getFecha(), s.getDescripcion()});
		    }
	        
	        table.setBounds(25, 50, 950, 600);
	        JScrollPane scrollPane = new JScrollPane(table);
			JOptionPane.showMessageDialog(null, (new JScrollPane(scrollPane)));
		}
		}

	
	
	// Usuario ha pulsado bot�n de logout
	
	class Logout implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			menuprincipal.setVisible(false);
			try {
				controlador.logout();
				new MenuLogin();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	// Usuario ha pulsado bot�n de "Ayuda"
	
	class Ayuda implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Desktop.isDesktopSupported()) {
	            try {
	            	// Aqu� tenemos que abrir la documentaci�n de ayuda de Teresa!! 
	            	// y los paths no funcionan como as� en jar, no s� porque.. 
	            	// si no funciona, poned los documentos, imagenes y todos los ficheros que tenemos que abrir
	            	// en fichero de src, y deber�an que funcionar!
	                File myFile = new File("documentacion/documentacion.pdf");
	                Desktop.getDesktop().open(myFile);
	            } catch (IOException ex) {
	                
	            }
	        }
		}
	}
	
	// Usuario ha pulsado bot�n de "Apagar"
	
	class Apagar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	public MenuPrincipal() throws IOException {
		// Se pone frame y panel de menu principal
		menuprincipal = this;
		setTitle("Almacén de Deleite GOURMET v1.0");
		setSize(1150,600);
		setLocationRelativeTo(null);

		panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		Color myColor = Color.decode("#70DB70");
		panel.setBackground(myColor);
		
		// Se pone exit listener
		addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
		System.exit(0);
		}
		});
		
		// Se pone bot�n de "Entregar a fabricaci�n"
		JButton fabricacion = new JButton("Entregar a fabricaci�n");
		fabricacion.setBounds(150, 75, 150, 50);
		fabricacion.addActionListener(new EntregarFabricacion());
		panel.add(fabricacion);
		fabricacion.setToolTipText("");

		
		// Se pone bot�n de "Exportar a ventas"
		JButton exportar = new JButton("Exportar a ventas");
		exportar.setBounds(320, 75, 150, 50);
		exportar.addActionListener(new ExportarVentas());
		panel.add(exportar);
		exportar.setToolTipText("");
		
		
		// Se pone bot�n de "Agregar materia prima"
		JButton prima = new JButton("Agregar materia prima");
		prima.setBounds(490, 75, 150, 50);
		prima.addActionListener(new AgregarMateriaPrima());
		panel.add(prima);
		prima.setToolTipText("");
		
		// Se pone bot�n de "Agregar producto l�cteo"
		JButton lacteo = new JButton("Agregar producto lácteo");
		lacteo.setBounds(660, 75, 150, 50);
		lacteo.addActionListener(new AgregarProductoLacteo());
		panel.add(lacteo);
		lacteo.setToolTipText("");
		
		// Se pone bot�n de "Ver peticiones"
		JButton vpeticiones = new JButton("Ver peticiones");
		vpeticiones.setBounds(830, 75, 150, 50);
		vpeticiones.addActionListener(new VerPeticiones());
		panel.add(vpeticiones);
		vpeticiones.setToolTipText("");
	
		// Se pone bot�n de menu de "USUARIO"
		JMenuBar menubar = new JMenuBar();
		JMenu usuariomenu = new JMenu("USUARIO");
		
		// Se pone imagen de menu de "USUARIO"
		// POR ALGUNA RAZ�N NO FUNCIONA PATH imagenes/Usuario.jpg en .jar!!! ENTONCES SI LOCALIZAN LOS FICHEROS EN SRC SE PUEDE ACCEDER A LOS FICHEROS CON /Usuario.jpg
		//BufferedImage usuarioPicture = ImageIO.read(getClass().getResource("/Usuario.jpg"));
		BufferedImage usuarioPicture = ImageIO.read(new File("imagenes/Usuario.jpg"));
		Image usuarioIcon = usuarioPicture.getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		usuariomenu.setIcon(new ImageIcon(usuarioIcon));
		
		usuariomenu.setMnemonic(KeyEvent.VK_A);
		JMenuItem logout = new JMenuItem("Logout");
		JMenuItem ayuda = new JMenuItem("Ayuda");
		JMenuItem apagar = new JMenuItem("Apagar");
		usuariomenu.add(logout);
		usuariomenu.add(ayuda);
		usuariomenu.add(apagar);
		//logout.addActionListener(new Logout());
		//ayuda.addActionListener(new Ayuda());
		//apagar.addActionListener(new Apagar());
		menubar.add(Box.createHorizontalGlue());
		menubar.add(usuariomenu);
		usuariomenu.setBounds(900, 25, 100, 30);
		this.setJMenuBar(menubar);
		
		productos();
		
		
		// Se pone logo
		//BufferedImage myPicture = ImageIO.read(getClass().getResource("/LogoLacteos.jpg"));
		BufferedImage myPicture = ImageIO.read(new File("imagenes/LogoLacteos.jpg"));
		Image myPicture1 = myPicture.getScaledInstance(150, 75, Image.SCALE_DEFAULT);
		JLabel picLabel = new JLabel(new ImageIcon(myPicture1));
		panel.add(picLabel);
		picLabel.setBounds(0, 0, 150, 75);
		
		this.add(panel);
		this.setVisible(true);
		getContentPane().setBackground(Color.green);
		
		
	}
	
	public static void main(String[] args) throws IOException {
		new MenuPrincipal();
	}
	
}