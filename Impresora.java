public class Impresora implements interfazGetId{
    final private String idImpresora ;
    private boolean disponible;
    private Cita[] proximasCitas;
    private int capacidadMax;
    public static double filamento = 1000;


    public Impresora(String idImpresora, int capacidadMaxCitas) {
        this.idImpresora = idImpresora;
        this.disponible = true;
        this.proximasCitas = new Cita[capacidadMaxCitas]; 
        capacidadMax = capacidadMaxCitas;
        
    }
    
    // getters

    public double getFilamento(){
        return filamento;
    }
    
    public boolean enUso() {
        for (Cita cita : proximasCitas) {
            if (cita != null) return true;
        }
        return false;
    }

    public String getId() {
        return idImpresora;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Cita[] getProximasCitas() {
        return proximasCitas;
    }
    
    // set y calculos utiles
    
    public static void minusFila(double valor){
        filamento-=valor;
    }

    public void setDisponible() {
    this.disponible = !disponible;
    }

    public boolean agendarCita(Cita nuevaCita) {
        for (int i = 0; i < proximasCitas.length; i++) {
            if (proximasCitas[i] == null) {
                proximasCitas[i] = nuevaCita;
                return true;
            }
        }
        return false; 
    }

    // to string 
    
    public String toString() {
        return idImpresora + "Disponible: "+ disponible + "Filamento: "+ filamento;
    }
}
