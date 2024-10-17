import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gestionReservasApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestión de Reservas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Crear los componentes
        DefaultListModel<Reserva> listaReservasModel = new DefaultListModel<>();  // Lista de reservas
        DefaultListModel<Reserva> listaConfirmadasModel = new DefaultListModel<>(); // Lista de confirmadas
        DefaultListModel<Reserva> listaEliminadasModel = new DefaultListModel<>();  // Lista de eliminadas

        JList<Reserva> listaReservas = new JList<>(listaReservasModel); //Lista de reservas cuando ya sean agregar
        JList<Reserva> listaConfirmadas = new JList<>(listaConfirmadasModel); // Lista de reservas confirmadas
        JList<Reserva> listaEliminadas = new JList<>(listaEliminadasModel);   // Lista de reservas eliminadas

        //Asignaciónes de Scrolles
        JScrollPane scrollPaneReservas = new JScrollPane(listaReservas);
        JScrollPane scrollPaneConfirmadas = new JScrollPane(listaConfirmadas);
        JScrollPane scrollPaneEliminadas = new JScrollPane(listaEliminadas);

        JTextField nombreEventoField = new JTextField();
        nombreEventoField.setPreferredSize(new Dimension(200, 30));

        JButton btnAgregar = new JButton("Agregar Reserva");   // Botón de Reserva
        JButton btnEliminar = new JButton("Eliminar Reserva");   // Botón de eliminar
        JButton btnConfirmar = new JButton("Confirmar Reserva");  // Botón de confirmar




        // Agregar reserva
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEvento = nombreEventoField.getText();
                if (!nombreEvento.isEmpty()) {
                    Reserva reserva = new Reserva(nombreEvento);
                    listaReservasModel.addElement(reserva); //Ejecutar la reserva
                    nombreEventoField.setText(""); // Limpiar campo de texto
                } else {
                    JOptionPane.showMessageDialog(frame, "Ingrese el nombre del evento.");
                }
            }
        });

        // Eliminar reserva
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaReservas.getSelectedIndex();
                if (selectedIndex != -1) {
                    Reserva reserva = listaReservasModel.get(selectedIndex);
                    listaReservasModel.remove(selectedIndex);
                    listaEliminadasModel.addElement(reserva); // Añadir a la lista de eliminadas dentro del tercer panel
                } else {
                    // Al no seleccionar nada se mostrara un mensaje
                    JOptionPane.showMessageDialog(frame, "Seleccione una reserva para eliminar.");
                }
            }
        });

        // Confirmar reserva
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaReservas.getSelectedIndex();
                if (selectedIndex != -1) {
                    Reserva reserva = listaReservasModel.get(selectedIndex);
                    reserva.setConfirmada(true); // Reserva como confirmada que se mostrara en el segundo panel
                    listaReservasModel.remove(selectedIndex); // Remover de la lista de pendientes, donde se movera del panel 1 al panel 2
                    listaConfirmadasModel.addElement(reserva); // Añadir a la lista de confirmadas esto se muestra en el panel 2
                    JOptionPane.showMessageDialog(frame, "Reserva confirmada para: " + reserva.getNombreEvento());
                } else {
                    // se mostrara solo si no se selecciona
                    JOptionPane.showMessageDialog(frame, "Seleccione una reserva para confirmar.");
                }
            }
        });

        // Organizar los componentes en el panel
        JPanel panelPendientes = new JPanel(); // Panel principal
        panelPendientes.setLayout(new FlowLayout());
        panelPendientes.add(new JLabel("Reservas Pendientes"));// Ajustar el tamaño panel principal
        panelPendientes.add(scrollPaneReservas);

        JPanel panelConfirmadas = new JPanel(); // Panel 2
        panelConfirmadas.setLayout(new FlowLayout());
        panelConfirmadas.add(new JLabel("Reservas Confirmadas"));
        panelConfirmadas.add(scrollPaneConfirmadas);
        panelConfirmadas.setPreferredSize(new Dimension(250, 300)); // Ajustar el tamaño del segundo panel

        JPanel panelEliminadas = new JPanel(); // Panel 3
        panelEliminadas.setLayout(new FlowLayout());
        panelEliminadas.add(new JLabel("Reservas Eliminadas"));
        panelEliminadas.add(scrollPaneEliminadas);
        panelEliminadas.setPreferredSize(new Dimension(250, 300)); // Ajustar el tamaño del tercer panel

        JPanel panelControl = new JPanel();
        panelControl.setLayout(new FlowLayout());
        panelControl.add(new JLabel("Nombre del Evento:"));
        panelControl.add(nombreEventoField);
        panelControl.add(btnAgregar);    // Botón Agregar
        panelControl.add(btnConfirmar); // Botón Confirmar
        panelControl.add(btnEliminar);     // Botón Eliminar

        // Organizar los paneles en el frame de java(este es el mismo nombre de netbeans)
        frame.setLayout(new BorderLayout());
        frame.add(panelControl, BorderLayout.NORTH); // panel donde se muestra
        frame.add(panelPendientes, BorderLayout.WEST);  // Panel 1
        frame.add(panelConfirmadas, BorderLayout.CENTER);   // Panel 2
        frame.add(panelEliminadas, BorderLayout.EAST); // Panel 3

        // Mostrar la ventana
        frame.setVisible(true);
    }
}

// Clase Reserva con atributo de confirmación
class Reserva {
    private String nombreEvento;
    private boolean confirmada;  // Confirmar la reserva

    public Reserva(String nombreEvento) {
        this.nombreEvento = nombreEvento;
        this.confirmada = false;  // ocurre cuando no reconoce la seleccionada
    }
    // get y setters
    public String getNombreEvento() {
        return nombreEvento;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

    @Override
    public String toString() {
        return nombreEvento + (confirmada ? " (Confirmada)" : "");
    }
}