using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioTorneig.Model
{
    public class Classificacio
    {
     
       

        public Classificacio()   {
        }


        public Classificacio(Grup grup, ObservableCollection<ClassInscrit> lposicions)
        {
            lposicions = Lposicions;
            grup = Grup;


        }

       static    public Grup Grup { get; set; }

       static public ObservableCollection< ClassInscrit > Lposicions { get; set; } = new ObservableCollection<ClassInscrit>();

         
        
    }

    public class ClassInscrit
    {


        public ClassInscrit(int posicio,String nom,int partidas,int guanyades,int perdudas,Double coeficient) {
            Posicio = posicio;
            Nom = nom;
            Partidas = partidas;
            Guanyades = guanyades;
            Perdudas = perdudas;
            Coeficient = coeficient;
        }


        public int Posicio { get; set; }
        public String Nom { get; set; }
        public int Partidas{ get; set; }
        public int Guanyades { get; set; }
        public int Perdudas { get; set; }
        public Double Coeficient { get; set; }






    }
}
