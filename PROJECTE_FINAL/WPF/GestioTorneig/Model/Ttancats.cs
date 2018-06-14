using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioTorneig.Model
{
    public class Ttancats { 
 
        
        public Ttancats(int id, DateTime? dataClosed, string desc,Enums.GUANYADOR ganador,int inscritA,int inscritB)
        {
            Id = id;
            DataClosed = dataClosed;
            Desc = desc;
            Ganador= ganador;
            IncritA = inscritA;
            IncritB = inscritB;

        }

        public int Id { get; set; }

        public int IncritA{ get; set; }

        public int IncritB { get; set; }
        public Enums.GUANYADOR  Ganador{ get; set; }


        public DateTime? DataClosed { get; set; }
        
        public string Desc { get; set; }


       

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