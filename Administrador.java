import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Administrador extends Usuario  {

    //constructor 
    // explicacion spity

    public Administrador(String id, String nombre, String usuario, String contraseña) {
        super(id, nombre, usuario,contraseña);
    }

    //funcionalidades propias de la clase administrador
    
    public void cancelarCitas(ArrayList<Cita> citas,String idCita) {
        int indice = -1;
        for (int i = 0; i < citas.size(); i++) {
            if(citas.get(i).getId().equals(idCita)){
                indice = i;
                citas.get(i).getNumImpresora().setDisponible();
            }
        }
        if (indice >= 0){
            citas.remove(indice);
            System.out.println("La cita ha sido cancelada exitosamente.");
        }else{
            System.out.println("Esta cita no puede ser cancelada.");
        }
        
    }
    
    public void cambiarDispo(ArrayList<Impresora> listaImpresoras,String idImpresora){
        boolean hecho= false;
        for(int i = 0; i < listaImpresoras.size(); i++) {
            if(listaImpresoras.get(i).getId().equals(idImpresora)){
                System.out.println("cambio hecho!");
                listaImpresoras.get(i).setDisponible();
                hecho=true;
            }
        }
        if(hecho==false){
            System.out.println("impresora no encontrada");
        }else{
            System.out.println("Se ha cambiado la disponibilidad de "+ idImpresora + ", mensaje enviado a usuarios" );
            
            new Anuncio("Se ha cambiado la disponibilidad de "+ idImpresora , LocalDateTime.now(), 30);
        }
            
    }

    public void cambiarTopeSemanal(int actualizarTope){
        Usuario.tope=actualizarTope; 
        System.out.println("Se ha cambiado el tope semanal a "+ actualizarTope);
    }

    public void cambiarTopeDiario(int actualizarTope){
        Usuario.tope=actualizarTope;
        System.out.println("Se ha cambiado el tope diario a "+ actualizarTope);
    }

    public void agregarImpresora(Scanner sc,  List<Impresora> impresoras){
        System.out.println(impresoras);
        
        System.out.println("Ingrese el Id de la nueva impresora:");
        String id = sc.nextLine();
                
        System.out.println("Ingrese su capacidad de citas:");
        int cantidad = sc.nextInt();
        sc.nextLine(); // limpiar salto

        Impresora nuevaImpresora = new Impresora(id, cantidad);

        impresoras.add(nuevaImpresora);


    }

    public void anunciar(String mensaje, ArrayList<Anuncio> listaAnuncios,LocalDateTime fechaInicio, int duracion,Usuario destinatario) {
        listaAnuncios.add(new Anuncio(mensaje,fechaInicio,duracion,destinatario));
    }
    
    
}
