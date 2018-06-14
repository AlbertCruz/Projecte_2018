package info.infomila.appbolos.models;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by alber
 */

public class EstadisticaModalitatPK implements Serializable {

    private static final long serialVersionUID = 8L;

    private Soci soci;
    private Modalitat modalitat;

    protected EstadisticaModalitatPK() { }

    public EstadisticaModalitatPK(Soci soci, Modalitat modalitat) {
        setSoci(soci);
        setModalitat(modalitat);
    }

    public Soci getSoci()
    {
        return soci;
    }

    protected final void setSoci(Soci soci)
    {
        if (soci != null)
        {
            this.soci = soci;
        }
    }

    public Modalitat getModalitat()
    {
        return modalitat;
    }

    protected final void setModalitat(Modalitat modalitat)
    {
        if (modalitat != null)
        {
            this.modalitat = modalitat;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.soci);
        hash = 61 * hash + Objects.hashCode(this.modalitat);
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
        final EstadisticaModalitatPK other = (EstadisticaModalitatPK) obj;
        if (!Objects.equals(this.soci, other.soci)) {
            return false;
        }
        return Objects.equals(this.modalitat, other.modalitat);
    }

}
