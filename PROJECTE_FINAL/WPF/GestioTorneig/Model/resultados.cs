using GestioTorneig.Model;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioTorneig.Pages.Model
{
    public class Classificacio
    {
        public Classificacio(ObservableCollection<Torneo> resTornejos, int idTorneig, bool pCanvis)
        {
            ResTornejos = resTornejos;
            IdTorneig= idTorneig;
            Canvis = pCanvis;
        }

        public int idGrup { get; set; }
        private bool mCanvis;

        public bool Canvis
        {
            get { return mCanvis; }
            set { mCanvis = value; }
        }       
        public ObservableCollection<Torneo> ResTornejos { get; set; }
        public int IdTorneig { get; set; }
      


    }
}
