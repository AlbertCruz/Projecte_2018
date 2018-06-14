using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioTorneig.Model
{
    public  class Partida : INotifyPropertyChanged
    {

       


        public int Id { get; set; }

        public int CarambolesB { get; set; }

        public int CarambolesA { get; set; }


        public DateTime? DataPartida { get; set; }


        public int NumEntrades { get; set; }



        public event PropertyChangedEventHandler PropertyChanged; 

        public Inscrit InscritA { get; set; }

        public Inscrit InscritB { get; set; }

        public Enums.MOTIU_VICTORIA MotiuVictoria { get; set; }

        public Enums.GUANYADOR ganador { get; set; }



        public Enums.ESTAT_PARTIDA EstatPartida { get; set; }




        public Partida(int id, int carambolesA, int carambolesB, DateTime? dataPartida, Inscrit inscritA, Inscrit inscritB,
             int numEntrades, Enums.ESTAT_PARTIDA estatPartida)
        {

            Id = id;
            CarambolesA = carambolesA;
            CarambolesB = carambolesB;
            DataPartida = dataPartida;
            InscritA = inscritA;
            InscritB = inscritB;
            NumEntrades = numEntrades;
            EstatPartida = estatPartida;

        }

        public Partida(int id, int carambolesA, int carambolesB, DateTime? dataPartida, Inscrit inscritA, Inscrit inscritB, int numEntrades, Enums.ESTAT_PARTIDA estatPartida, Enums.GUANYADOR g)
        {        }


    }
}


