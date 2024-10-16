package Interfaces;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.sql.rowset.JoinRowSet;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Editor_texto_basico {
	
//ELEMENTOS QUE NECESITAMOS EN EL METODO
    private JTextPane area;            // El área de texto
    private JCheckBox negrita, cursiva; // Casillas de verificación para negrita y cursiva
    private JSpinner spinner;          // Spinner para seleccionar el tamaño de la fuente
    private JComboBox <String> fontComboBox; // ComboBox para seleccionar el tipo de fuente
    private  JLabel info;
    
    //estos lo ponemos aqui para que los eventos puedan utilizar estos componentes
    private   JCheckBoxMenuItem boxnegrita;
    private   JCheckBoxMenuItem boxcursiva;
    
    public Editor_texto_basico() {
        JFrame frame = new JFrame("Editor de Texto Básico");
        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);

//PANEL PRINCIPAL 
        // Panel principal con área de texto y la info
        JPanel panelprincipal = new JPanel(new BorderLayout());
        area = new JTextPane();
        JScrollPane jScrollPane = new JScrollPane(area);
        
        info= new JLabel();
        updateInfo(); //le ponemos la info que tenga el area
        
        panelprincipal.add(jScrollPane, BorderLayout.CENTER);
        panelprincipal.add(info,BorderLayout.SOUTH);
        //evento del textpane
        
        //por logica la verdad tenia que existir este evento
        area.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				updateFont();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				updateFont();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				updateFont();
				
			}
		});
        
//PANEL DE HERRAMIENTAS        
        // Panel de herramientas con JCheckBoxes, JSpinner y JComboBox
        JPanel toolpanel = new JPanel(new GridLayout(1, 4));//lo he puesto como un grid y le digo ue lo ponga 1 fila y 4 columnas

        // Crear los componentes de estilo y tamaño
        negrita = new JCheckBox("Negrita");
        cursiva = new JCheckBox("Cursiva");
        
        //evento negrita
        negrita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cada vez que se marque o desmarque, actualizamos la fuente y actualizamos el menu de arribad
            	if(negrita.isSelected())
            		boxnegrita.setSelected(true);
            	else {
            		boxnegrita.setSelected(false);
				}
                updateFont();
            }
        });
        
        //evento cusiva
        cursiva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(cursiva.isSelected())
            		boxcursiva.setSelected(true);
            	else {
            		boxcursiva.setSelected(false);
				}
                updateFont();
            }
        });

        // Spinner para cambiar el tamaño de la fuente
        spinner = new JSpinner(new SpinnerNumberModel(area.getFont().getSize(), 10, 36, 2));
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Cuando cambie el valor del spinner, se actualiza la fuente
                updateFont();
            }
        });

        // ComboBox para elegir el tipo de fuente
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontComboBox = new JComboBox<>(fonts);
        fontComboBox.setSelectedItem(area.getFont().getFamily());
        
        //que cuando se seleccione el comobox pues se llama al metodo que te pone todo el font al area
        fontComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFont();
            }
        });

        // Añadir los componentes al panel de herramientas
        toolpanel.add(negrita);
        toolpanel.add(cursiva);
        toolpanel.add(spinner);
        toolpanel.add(fontComboBox);
//MENU
        // Menubar y menus y submenus
        JMenuBar bar = new JMenuBar();
        JMenu archivo = new JMenu("Archivo");
        JMenuItem nuevo = new JMenuItem("Nuevo");
        JMenuItem salir = new JMenuItem("Salir");
        JMenu estilo= new JMenu("Estilo");
        boxnegrita= new JCheckBoxMenuItem("Negrita");
        boxcursiva= new JCheckBoxMenuItem("Cursiva");
        
        frame.setJMenuBar(bar); //añadimos el menu bar al principal
        
     //EVENTOS DEL MENU ARCHIVO  y submenus
        // Acción para el menú "Nuevo" que limpia el área de texto
        nuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                area.setText(""); // Limpiar el área de texto
            }
        });

        // Acción para el menú "Salir" que cierra la aplicación
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // asi es como se cierra la app por "obligacion"
            }
        });

        archivo.add(nuevo);
        archivo.add(salir);
        bar.add(archivo);
        
      //EVENTOS DEL MENU ESTILO Y submenus(checkboxmenu)
        boxnegrita.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				if(boxnegrita.isSelected())
				negrita.setSelected(true);
				else {
				negrita.setSelected(false);
				}
				
				updateFont();
			}
		});
        boxcursiva.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(boxcursiva.isSelected())
					cursiva.setSelected(true);
					else {
					cursiva.setSelected(false);
					}
				updateFont();
			}
		}); 
		
        bar.add(estilo);
        estilo.add(boxnegrita);
        estilo.add(boxcursiva);
  
//SELECIONAR PARTE DEL TEXTO Y QUE SE PONGA ESA PARTE EN NEGRITA O EN CURSIVA 
        
        
        
// Añadir los paneles al frame
        panel.add(panelprincipal, BorderLayout.CENTER); //uno en el centtro y el otro arriba
        panel.add(toolpanel, BorderLayout.NORTH);

// Configuración de la ventana
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

// Método que actualiza el estilo de fuente del JTextArea 
    private void updateFont() {
        int fontSize = (int) spinner.getValue(); // Obtener tamaño del spinner
        String fontName = (String) fontComboBox.getSelectedItem(); // Obtener fuente
        int fontStyle = Font.PLAIN; // Empezar con el estilo normal

        // Si hay texto seleccionado
        if (area.getSelectedText() != null) {
            // Determinar el estilo de la fuente
            if (negrita.isSelected()) {
                fontStyle += Font.BOLD;
            }

            if (cursiva.isSelected()) {
                fontStyle += Font.ITALIC;
            }

            // Crear una nueva fuente con los valores seleccionados
            Font newFont = new Font(fontName, fontStyle, fontSize);
            
            // Obtener el documento del JTextPane
            StyledDocument doc = area.getStyledDocument();
            
            // Establecer un estilo
            Style style = area.addStyle("FontStyle", null);
            StyleConstants.setFontFamily(style, fontName);
            StyleConstants.setFontSize(style, fontSize);
            StyleConstants.setBold(style, negrita.isSelected());
            StyleConstants.setItalic(style, cursiva.isSelected());
            
            // Aplicar el estilo a la selección
            int start = area.getSelectionStart();
            int end = area.getSelectionEnd();
            doc.setCharacterAttributes(start, end - start, style, false); // Aplica el estilo sin reemplazar el texto
            //es muy raro esto pero lo entendi

        } else {
            // Si no hay texto seleccionado, aplicamos el estilo a todo el texto
            if (negrita.isSelected()) {
                fontStyle += Font.BOLD;
            }

            if (cursiva.isSelected()) {
                fontStyle += Font.ITALIC;
            }

            // Crear una nueva fuente con los valores seleccionados
            Font newFont = new Font(fontName, fontStyle, fontSize);
            area.setFont(newFont); // Aplicar la nueva fuente al área de texto
        }

        updateInfo(); // Actualizar la información en el JLabel
    }


//evento para actualizar el label
    private void updateInfo() {
    	int cantidadpalabras=area.getText().length();
    	int cantidadlineas=getLineCount();
    	Font fuentearea= area.getFont();
    	String estilo="";
    	switch (fuentearea.getStyle()) {
    	case 0:
    		estilo="normal";
    		break;
		case 1:
			estilo="negrita";
			break;

		case 2:
			estilo="cursiva";
			break;
		case 3:
			estilo="negrita y cursiva ";	
			break;
		}
    	
    	
    	info.setText("Palabras:	"+cantidadpalabras+" | Lineas: "+cantidadlineas+" | Tipo de letra: "+fuentearea.getFamily()+" | Estilo: " + estilo + " | Tamaño: "+fuentearea.getSize());
    	
    }
    
    //la cantidad de lineas para el textpane (maldito metodo)
    private int getLineCount() {
        // Obtener el documento asociado al JTextPane
        Document doc = area.getDocument();

        // Contar el número de saltos de línea visuales (líneas lógicas)
        return doc.getDefaultRootElement().getElementCount();
    }


    
//MAIN
    public static void main(String[] args) {
        new Editor_texto_basico(); // Crear el editor
    }
}
