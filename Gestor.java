import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Gestor {
    private Usuario[] listaUsuarios;
    private ArrayList<Impresora> listaImpresoras;
    private ArrayList<Cita> listaCitas;
    private ArrayList<Anuncio> listaAnuncios;
    
    // Constructor
    public Gestor() {
        this.listaUsuarios= getUsuarios();
        this.listaImpresoras = getImpresora();
        this.listaCitas = getCitas();
        this.listaAnuncios=getAnuncios();
    }

    // listas de manejo para pruebas
    // 2025-06-19 12:00
    // 2025-06-30 12:00

    private ArrayList<Anuncio> getAnuncios(){
        ArrayList<Anuncio> Anuncios = new ArrayList<>();
        Anuncios.add(new Anuncio("Mantenimiento el lunes", LocalDateTime.now(), 60, listaUsuarios[0]));
        Anuncios.add(new Anuncio("Cambio de horario", LocalDateTime.now(), 30, listaUsuarios[1]));
        return Anuncios;
    }

    private Usuario[] getUsuarios() {
        Usuario[] usuarios = new Usuario[] {
        new Profesor("1001", "Sergio Valencia", "sevalenciaa", "rockyou"),
        new Administrador("1002", "Juliana Lopez", "jlopezm", "password123"),
        new Estudiante("1003", "Carlos Martinez", "cmartinez", "1234abcd"),
        new Estudiante("1004", "Ana Vasquez", "anavasq", "ana2024"),
        new Profesor("1005", "Luis Rodriguez", "lrodriguez", "qwerty"),
        new Administrador("1006", "Alejandro Muñoz", "a", "a")
        };
        return usuarios;
    }

    private ArrayList<Impresora> getImpresora(){
        ArrayList<Impresora> impresoras = new ArrayList<>();
        
            impresoras.add(new Impresora("A", 5));
            impresoras.add(new Impresora("B", 3));
            impresoras.add(new Impresora("C", 4));
        
        return impresoras;
        
    }
    
    private ArrayList<Cita> getCitas() {  ArrayList<Cita> citas = new ArrayList<>();

        // Agregar objetos Cita
        citas.add(new Cita(listaImpresoras.get(0), 23.5, LocalDateTime.of(2025, 6, 28, 13, 0), 60, listaUsuarios[0]));
        citas.add(new Cita(listaImpresoras.get(1), 45.0, LocalDateTime.of(2025, 6, 28, 12, 0), 90, listaUsuarios[1]));
        citas.add(new Cita(listaImpresoras.get(2), 30.0, LocalDateTime.of(2025, 6, 29, 9, 30), 45, listaUsuarios[2]));
        
        return citas;
    }

    // Métodos para agregar, buscar, validar, etc...

    // Método para iniciar sesión (versión básica con solo nombre de usuario)
    public Usuario iniciarSesion(String inicioUsuario, String contraseña) {

        for (Usuario usuario : listaUsuarios) {
            if (usuario != null && usuario.getUsuario().equals(inicioUsuario)) {
                if(usuario.getContraseña().equals(contraseña)){
                    return usuario;
                }
            }
        }
        return null; // no encontrado
    }

    // Calcula el total de filamento usado en todas las citas
    public double calculoFilamento() {
        double total = 0;
        for (Impresora c : listaImpresoras) {
            if (c != null) {
                total += c.getFilamento();
            }
        }
        return total;
    }

    // Verifica si una nueva cita se superpone con alguna existente
    public boolean seSuperponeCon(Cita nueva) {
        LocalDateTime ini2 = nueva.getFechaInicio();
        LocalDateTime fin2 = nueva.getFechaFinal();

        for (Cita existente : listaCitas) {
            if (existente != null) {
                LocalDateTime ini1 = existente.getFechaInicio();
                LocalDateTime fin1 = ini1.plusMinutes(existente.getDuracion());

                // Verifica superposición
                if (ini1.isBefore(fin2) && ini2.isBefore(fin1)) {
                    return true;
                }
            }
        }
        return false;
    }

    //Inicios de sesion y funcionalidades segun el rol
    
    public void inicio(){

        System.out.println("Bienvenido al SACI3D piloto");

        Scanner sc = new Scanner(System.in);

        System.out.print("Usuario: ");
        String username = sc.nextLine();

        System.out.print("Contraseña: ");
        String password = sc.nextLine();

        boolean rep= true;
        Usuario ingreUsuario;

        while(rep){
            ingreUsuario=iniciarSesion(username, password);
            if(ingreUsuario!=null){
                rep=false;
                if (ingreUsuario instanceof Profesor) {
                    Profesor profesor = (Profesor) ingreUsuario;
                    inicioProfesor(profesor);
                } else if (ingreUsuario instanceof Estudiante) {
                    Estudiante estudiante = (Estudiante) ingreUsuario;
                    inicioEstudiante(estudiante);
                    // Aquí puedes acceder a métodos o atributos específicos de Estudiante
                } else if (ingreUsuario instanceof Administrador) {
                    Administrador admin = (Administrador) ingreUsuario;
                    inicioAdministrador(admin);
                    // Aquí puedes acceder a métodos o atributos específicos de Administrador
                } else {
                    System.out.println("Tipo de usuario desconocido");
                }

            }else{
                System.out.println("Usuario o contraseña incorrectos");
                inicio();
            }
            
        }


    }

    public void inicioEstudiante(Estudiante estudiante){
        Scanner sc= new Scanner(System.in);
        boolean menu= true;
        while(menu){
            System.out.println("Hola "+estudiante.getNombre()+" ¿que deseas hacer el dia de hoy?");
            System.out.println("1) consultar\n2) agendar\n3) cancelar\n4)ver mis citas\n5)salir");
            String opt=sc.nextLine();
        
            boolean rep=true;
            while(rep){

                switch (opt) {

                    case "1":
                        estudiante.consultar(listaCitas);
                        rep=false;
                        break;

                    case "2":
                        estudiante.agendar(sc,listaCitas, listaImpresoras);
                        rep=false;
                        break;

                    case "3":
                        System.out.println("ingresa el id de tu cita a cancelar");
                        String idCitaPropia=sc.nextLine();
                        estudiante.cancelar(listaCitas,idCitaPropia);
                        break;
                    case "4":
                        estudiante.getMisCitas(listaCitas);
                        rep=false;
                        break;
                    case "5":
                        
                        return;

                    default:
                        System.out.println("Opción no válida.");
                }
            }


        }

    }  

    public void inicioProfesor(Profesor profesor){
        Scanner sc= new Scanner(System.in);
        boolean menu= true;
        while(menu){
            System.out.println("Hola profesor "+profesor.getNombre()+" ¿que deseas hacer el dia de hoy?");
            System.out.println("1) consultar\n2) agendar\n3) cancelar\n4) anunciar\n5)ver mis citas\n6)salir");
            String opt=sc.nextLine();
            boolean rep=true;
            while(rep){

                switch (opt) {

                    case "1":
                        profesor.consultar(listaCitas);
                        rep=false;
                        break;

                    case "2":
                        profesor.agendar(sc,listaCitas, listaImpresoras);
                        rep=false;
                        break;


                    case "3":
                        System.out.print("Ingresa el id de la cita a cancelar: ");
                        String idCitaPropia = sc.nextLine();
                        profesor.cancelar(listaCitas, idCitaPropia);
                        rep = false;
                        break;
                    case "4":

                        System.out.print("Escribe el mensaje del anuncio: ");
                        String mensaje = sc.nextLine();

                        System.out.print("Escribe la fecha de inicio (formato yyyy-MM-dd HH:mm): ");

                        LocalDateTime fechaInicio = null;
                        while (true) {
                            try {
                                System.out.println("Ingrese la fecha y hora de inicio (formato: yyyy-MM-dd HH:mm):");
                                String fechaStr = sc.nextLine();
                                fechaInicio = LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));


                                if (!Evento.validarFecha(fechaInicio)) {
                                    System.out.println("La fecha ingresada ya pasó. Intente con una fecha futura.");
                                } else {
                                    break; // fecha válida
                                }
                            } catch (Exception e) {
                                System.out.println("Formato de fecha inválido. Intente nuevamente.");
                            }
                        }


                        System.out.print("Escribe la duración (en minutos): ");
                        int duracion = Integer.parseInt(sc.nextLine());

                        //implementar logica para dar el mensaje a varias personas
                        System.out.print("indica el lugar del usuario ");
                        int index = Integer.parseInt(sc.nextLine());

                        profesor.anunciar(mensaje, listaAnuncios, fechaInicio, duracion, listaUsuarios[index]);

                        System.out.println("Anuncio realizado con exito!");

                        rep=false;
                        break;
                    case "5":
                        profesor.getMisCitas(listaCitas);
                        rep=false;
                        break;
                    case "6":
                    
                        return;

                    default:
                        System.out.println("Opción no válida.");
                }
            }


        }

    }
    
    public void inicioAdministrador(Administrador administrador){
        Scanner sc= new Scanner(System.in);
        boolean menu= true;
        while(menu){
            System.out.println("Hola profesor "+administrador.getNombre()+" ¿que deseas hacer el dia de hoy?");
            System.out.println("1) consultar\n2) agendar\n3) cancelar mi cita\n4) anunciar\n5)cancelar citas\n6)cambiar disponibilidad impresora\n7)cambiar tope de impresion \n8)agregar impresora\n9)ver mis citas\n10)salir");
            String opt=sc.nextLine();
            boolean rep=true;
            while(rep){

                switch (opt) {

                    case "1":
                        administrador.consultar(listaCitas);
                        rep=false;
                        break;

                    case "2":
                        administrador.agendar(sc,listaCitas, listaImpresoras);
                        rep=false;
                        break;

                    case "3":
                        System.out.println("ingresa el id de tu cita a cancelar");
                        String idCitaPropia=sc.nextLine();
                        administrador.cancelar(listaCitas,idCitaPropia);
                        rep=false;
                        break;
                    case "4":

                        System.out.print("Escribe el mensaje del anuncio: ");
                        String mensaje = sc.nextLine();

                        LocalDateTime fechaInicio = null;
                        while (true) {
                            try {
                                System.out.println("Ingrese la fecha y hora de inicio (formato: yyyy-MM-dd HH:mm):");
                                String fechaStr = sc.nextLine();
                                fechaInicio = LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));


                                if (!Evento.validarFecha(fechaInicio)) {
                                    System.out.println("La fecha ingresada ya pasó. Intente con una fecha futura.");
                                } else {
                                    break; // fecha válida
                                }
                            } catch (Exception e) {
                                System.out.println("Formato de fecha inválido. Intente nuevamente.");
                            }
                        }

                        System.out.print("Escribe la duración (en minutos): ");
                        int duracion = Integer.parseInt(sc.nextLine());

                        //implementar logica para dar el mensaje a varias personas
                        System.out.print("indica el lugar del usuario ");
                        int index = Integer.parseInt(sc.nextLine());

                        administrador.anunciar(mensaje, listaAnuncios, fechaInicio, duracion, listaUsuarios[index]);

                        System.out.println("Anuncio realizado con exito!");
                        rep=false;
                        break;

                    case "5":

                        System.out.print("Ingresa el id de la cita a cancelar: ");
                        String idCita = sc.nextLine();
                        administrador.cancelarCitas(listaCitas, idCita);
                        rep = false;
                    
                        break;
                    case "6":
                        System.out.println("Ingresa el id de la impresora");
                        String idImpresora=sc.nextLine();
                        administrador.cambiarDispo(listaImpresoras,idImpresora);
                        rep=false;
                        break;
                    case "7":
                        System.out.println("Indica el tope");
                        int top = Integer.parseInt(sc.nextLine());
                        administrador.cambiarTopeSemanal(top);
                        rep = false;
                        break;

                    case "8":
                        administrador.agregarImpresora(sc, listaImpresoras);
                        rep=false;
                        break;
                    case "9":
                        administrador.getMisCitas(listaCitas);
                        rep=false;
                        break;
                    case "10":
                        return;
                    default:
                        System.out.println("Opción no válida.");
                        rep=false;
                        break;
                }
            }


        }

    }
}
