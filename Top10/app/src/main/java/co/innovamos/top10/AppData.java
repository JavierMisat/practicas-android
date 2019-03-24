package co.innovamos.top10;

/**
 * Created by usuario on 13/02/2017.
 */

public class AppData {
    private String titulo;
    private String artista;
    private String fechaLanzamiento;
    private String resumen;
    private String urlImagen;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }


    @Override
    public String toString() {
        return "*********************************" +
                "titulo='" + titulo + "\n" +
                " - artista='" + artista + "\n" +
                " - fechaLanzamiento='" + fechaLanzamiento + "\n" +
                " - urlImagen='" + urlImagen + "\n" +
                "*********************************";
    }
}
