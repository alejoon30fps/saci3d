
import java.time.LocalDateTime;
public class Anuncio extends Evento{
    private String mensajeContenido;

    //Constructor principal

    public Anuncio(String mensajeContenido,LocalDateTime fechaInicio,int duracion,Usuario destinatario) {
        super(fechaInicio,destinatario,duracion);
        this.mensajeContenido = mensajeContenido;
    }

    // otro tipo de anuncio (constructor sobre escrito)

    public Anuncio(String mensajeContenido,LocalDateTime fechaInicio,int duracion) {
        super(fechaInicio,null,duracion);
        this.mensajeContenido = mensajeContenido;
    }

    // tostring get y set

    @Override
    public String toString() {
        return "Anuncio: " + mensajeContenido;
    }
    
    public String getMensajeContenido() {
        return mensajeContenido;
    }

    public void setMensajeContenido(String mensajeContenido) {
        this.mensajeContenido = mensajeContenido;
    }
}
