public class Impresora implements interfazGetId{
    final private String idImpresora;
    private boolean disponible;
    private Cita[] proximasCitas;
    private int capacidadMax;
    private double filamento;
    private String estado;

    public static double filamentoTotal=0;
    public static double consumo=0;


    public Impresora(String idImpresora, int capacidadMaxCitas, double filamento) {
        this.idImpresora = idImpresora;
        this.disponible = true;
        this.proximasCitas = new Cita[capacidadMaxCitas]; 
        capacidadMax = capacidadMaxCitas;
        this.filamento=filamento;
        filamentoTotal+=filamento;
        this.estado= "Estado: Disponible";
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
        filamentoTotal-=valor;
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

    public void cambiarEstado(String opcion){
        switch (opcion) {

            case "agendado":
                this.estado="Estado: No disponible\n Motivo: Ocupado en una cita ";
                break;
            case "descompuesto":
                this.estado="Estado: No disponible\n Motivo: Descompuesto o en mantenimiento ";
            case "WOTmaterial":
                this.estado="Estado: No disponible\n Motivo: No hay suficiente Material";
                
        }
    }
    // to string 
    
    public String toString() {
        return idImpresora + " Disponible: "+ disponible + " Filamento: "+ filamento;
    }
}
