using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Text;
using System.Threading.Tasks;



namespace GestioTorneig.Model
{
    public class Grup : INotifyPropertyChanged
    {
       

        public Grup(int id, Torneo torneig, string descripcio,int caramVictoria ,int limitEntrades, int mismogrupo )
      
        {
            Id = id;
            Torneig = torneig;           
            Descripcio = descripcio;
            CaramVictoria = caramVictoria;
            LimitEntrades = limitEntrades;
            MismoGrupo = mismogrupo;

        }

      

        public int Id { get; set; }

        public Torneo Torneig { get; set; }

        public string Descripcio { get; set; }

        public int CaramVictoria { get; set; }
        public int LimitEntrades { get; set; }
        public int MismoGrupo { get; set; }
        







       public ObservableCollection<Inscrit> Inscrits { get; set; } = new ObservableCollection<Inscrit>();




        public void addInscrit(Inscrit  inscrit)
        {
            if (inscrit != null && !Inscrits.Contains(inscrit))
            {
                Inscrits.Add(inscrit);
                if (!inscrit.Grup.Equals(this))
                {
                    inscrit.Grup = this;
                }

            }
        }

        public void removeInscrit(Inscrit inscrit)
        {
            if (inscrit != null && Inscrits.Contains(inscrit))
            {
                Inscrits.Remove(inscrit);
                Inscrits.ElementAt(Inscrits.IndexOf(inscrit)).Grup = null;
            }
        }


        public ObservableCollection<Partida> Partides { get; set; } = new ObservableCollection<Partida>();

        public event PropertyChangedEventHandler PropertyChanged;


        //public void addPartida(Partida partida)
        //{
        //    if (partida != null && !Partides.Contains(partida))
        //    {
        //        Partides.Add(partida);
        //        if (!partida.Grup.Equals(this))
        //        {
        //            inscrit.Grup = this;
        //        }

        //    }
        //}

        //public void removeInscrit(Inscrit inscrit)
        //{
        //    if (inscrit != null && Inscrits.Contains(inscrit))
        //    {
        //        Inscrits.Remove(inscrit);
        //        Inscrits.ElementAt(Inscrits.IndexOf(inscrit)).Grup = null;
        //    }
    }




















}

