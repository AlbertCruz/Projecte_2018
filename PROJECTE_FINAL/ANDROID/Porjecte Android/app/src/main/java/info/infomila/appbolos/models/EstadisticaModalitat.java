package info.infomila.appbolos.models;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import info.infomila.appbolos.models.exceptions.EstadisticaModalitatException;

public class EstadisticaModalitat implements Serializable {

    private static final long serialVersionUID = 3L;

    private EstadisticaModalitatPK emPK;
    private double coeficientBase;
    private int totalCarambolasTemporadaActual;
    private int totalEntradesTemporadaActual;

    protected EstadisticaModalitat() {}

    public EstadisticaModalitat(Soci soci, Modalitat modalitat, double coeficientBase, int carambolesTemporadaActual, int entradesTemporadaActual)
    {
        emPK = new EstadisticaModalitatPK(soci, modalitat);
        setCoeficientBase(coeficientBase);
        setCarambolesTemporadaActual(carambolesTemporadaActual);
        setEntradesTemporadaActual(entradesTemporadaActual);
    }

    public Soci getSoci()
    {
        return emPK.getSoci();
    }

    protected final void setSoci(Soci soci)
    {
        emPK.setSoci(soci);
    }

    public Modalitat getModalitat()
    {
        return emPK.getModalitat();
    }

    protected final void setModalitat(Modalitat modalitat)
    {
        emPK.setModalitat(modalitat);
    }

    public double getCoeficientBase()
    {
        return coeficientBase;
    }

    public final void setCoeficientBase(double coeficientBase)
    {
        if (coeficientBase>=0 && coeficientBase<=1) {
            throw new EstadisticaModalitatException("El coeficient de la estadística ha de ser un valor entre 0 i 1");
        }
        this.coeficientBase = coeficientBase;
    }

    public int getCarambolesTemporadaActual()
    {
        return totalCarambolasTemporadaActual;
    }

    public final void setCarambolesTemporadaActual(int carambolesTemporadaActual)
    {
        if (carambolesTemporadaActual<0) {
            throw new EstadisticaModalitatException("El total de caramboles ha de ser un número estrictament positiu");
        }
        this.totalCarambolasTemporadaActual = carambolesTemporadaActual;
    }

    public int getEntradesTemporadaActual()
    {
        return totalEntradesTemporadaActual;
    }

    public final void setEntradesTemporadaActual(int entradesTemporadaActual)
    {
        if (entradesTemporadaActual<0) {
            throw new EstadisticaModalitatException("El total d'entrades ha de ser un número estrictament positiu");
        }
        this.totalEntradesTemporadaActual = entradesTemporadaActual;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.emPK);
        return hash;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EstadisticaModalitat other = (EstadisticaModalitat) obj;
        return Objects.equals(this.emPK, other.emPK);
    }

    @Override
    public String toString() {
        return emPK.getModalitat().getDescripcio();
    }
}
