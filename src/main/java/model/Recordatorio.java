package model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.Instant;

@Entity
@DynamicInsert // Permite que se inserte un registro sin tener que especificar todos los campos, para que se genere un valor por defecto
@Table(name = "Recordatorio", schema = "railway")
public class Recordatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Column(name = "fechaCreacion", nullable = false)
    private Instant fechaCreacion;

    @ColumnDefault("'Pendiente'")
    @Lob
    @Column(name = "estado", nullable = true) // Tiene que ser nullable para que MySQL pueda asignar un valor por defecto
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_fk", nullable = false)
    private Usuario usuarioFk;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getUsuarioFk() {
        return usuarioFk;
    }

    public void setUsuarioFk(Usuario usuarioFk) {
        this.usuarioFk = usuarioFk;
    }

}