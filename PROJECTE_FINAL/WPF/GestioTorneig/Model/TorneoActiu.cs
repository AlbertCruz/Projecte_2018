using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioTorneig.Model
{
    public class TorneoActiu
    {
        
        public TorneoActiu(int id, DateTime dataInici,int qtgrup,int qtpartida)
        {
            Id = id;
            Qtgrup = qtgrup;
            Qtpartida = qtpartida;
            DataInici = dataInici;


        }

        public int Id { get; set; }
        public int Qtgrup { get; set; }
        public int Qtpartida { get; set; }

        public DateTime DataInici { get; set; }


       

        public ObservableCollection<Grup> Grups { get; set; } = new ObservableCollection<Grup>();
        public ObservableCollection<Inscrit> Inscrits { get; set; } = new ObservableCollection<Inscrit>();
       
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