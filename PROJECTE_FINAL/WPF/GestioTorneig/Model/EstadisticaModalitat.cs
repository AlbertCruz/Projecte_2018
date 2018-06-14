using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioTorneig.Model
{
    public class EstadisticaModalitat
    {
        public int CoeficientBase { get; set; }

        public int TotalCarambolasTemporadaActual { get; set; }

        public int TotalEntradesTemporadaActual { get; set; }

        public ObservableCollection<Modalitat> Modalitats { get; set; } = new ObservableCollection<Modalitat>();

        public ObservableCollection<Soci> Socis { get; set; } = new ObservableCollection<Soci>();



        //public EstadisticaModalitat() { }

        public EstadisticaModalitat(int coeficientBase,int  totalCarambolasTemporadaActual, int totalEntradesTemporadaActual)

        {
            CoeficientBase = coeficientBase;
            TotalCarambolasTemporadaActual = totalCarambolasTemporadaActual;
            TotalEntradesTemporadaActual = totalEntradesTemporadaActual;

        }



    }
}
