using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioTorneig.Model
{
    public class Inscrit
    {
     

        public Inscrit(int numero, Grup grup,Torneo torneo, DateTime dataInscrp)
        {
            Numero = numero;
            Grup= grup;
            Torneo= torneo;
            DataInscrp = dataInscrp;
           
        }



        public Inscrit()   {  }
              

        public int Numero { get; set; }

        public Grup Grup { get; set; }

        public Torneo Torneo{ get; set; }      

      

        public DateTime DataInscrp { get; set; }

        public ObservableCollection<Grup> Cites { get; set; } = new ObservableCollection<Grup>();

        public ObservableCollection<Partida> Sinistres { get; set; } = new ObservableCollection<Partida>();

        //public void addSinistre(Partida sinistre)
        //{
        //    if (sinistre != null && !Sinistres.Contains(sinistre))
        //    {
        //        Sinistres.Add(sinistre);
        //        if (!sinistre.Perit.Equals(this))
        //        {
        //            sinistre.Perit = this;
        //        }

        //    }
        //}

        //public void removeSinistre(Partida sinistre)
        //{
        //    if (sinistre != null && Sinistres.Contains(sinistre))
        //    {
        //        Sinistres.Remove(sinistre);
        //        Sinistres.ElementAt(Sinistres.IndexOf(sinistre)).Perit = null;
        //    }
        //}
        
    }
}
