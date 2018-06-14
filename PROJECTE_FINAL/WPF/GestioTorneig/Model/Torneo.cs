using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioTorneig.Model
{
    public class Torneo
    {
    
        public Torneo(int id, string nom, DateTime? dataInici, Modalitat modalitat, Boolean preinscripcion)
        {

            Id = id;
            Nom = nom;
            DataInici = dataInici;
            Modalitat = modalitat;
            Preinscripcion = preinscripcion;



        }

       

        public int Id { get; set; }
        public DateTime? DataCosed { get; set; }

        public DateTime? DataInici { get; set; }


        public string Nom { get; set; }

        public Modalitat Modalitat { get; set; }

        public ObservableCollection<Grup> Grups { get; set; } = new ObservableCollection<Grup>();
        public ObservableCollection<Inscrit> Inscrits { get; set; } = new ObservableCollection<Inscrit>();
        public Boolean Preinscripcion { get;  set; }
    }
}




        //public void addSinistre(Partida sinistre)
        //{
        //    if (sinistre != null && !Sinistres.Contains(sinistre))
        //    {
        //        Sinistres.Add(sinistre);
        //        if (!sinistre.Polissa.Equals(this))
        //        {
        //            sinistre.Polissa = this;
        //        }

        //    }
        //}

        //public void removeSinistre(Partida sinistre)
        //{
        //    if (sinistre != null && Sinistres.Contains(sinistre))
        //    {
        //        Sinistres.ElementAt(Sinistres.IndexOf(sinistre)).Polissa = null;
        //        Sinistres.Remove(sinistre);
        //    }
        //}


   // }
//