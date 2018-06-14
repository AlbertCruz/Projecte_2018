using GestioTorneig.Model;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// The User Control item template is documented at http://go.microsoft.com/fwlink/?LinkId=234236

namespace GestioTorneig.Controls
{
    public sealed partial class ControlClassificacio : UserControl
    {
        public event EventHandler onClickEntradesInforme;
        public event EventHandler onClickTornar;
        public event EventHandler onClickUpdatePerit;
        public  ObservableCollection<object> entradesObject { get; set; }
       

        public ControlClassificacio()
        {
            this.InitializeComponent();
            Loaded += ControlClassificacio_Loaded;

            lvSInscrits.onSelected_changed += lvSInscrits_onSelected_changed;
        }

        private void lvSInscrits_onSelected_changed(object sender, EventArgs e)
        {
            // imgEntradaInforme.Source = ((Grup)lvEntradesInforme.ObjecteSeleccionat).BitmapFoto;
        }

        private void ControlClassificacio_Loaded(object sender, RoutedEventArgs e)
        {
            preparaLlistaClassificacio();
           

           // ObservableCollection<object> entradesObject;          
            if (Classificacio.Grup!= null)
            {
              

                entradesObject = new ObservableCollection<object>(Classificacio.Lposicions);
                lvSInscrits.ItemsSource = entradesObject;



            }




            //else
            ////{
            //    entradesObject = new ObservableCollection<object>();
            //    trucadesObject = new ObservableCollection<object>(); 
            //}
            //if (Sinistre.Trucades != null)
            //{
            //    trucadesObject = new ObservableCollection<object>(Sinistre.Trucades);
            //}
            //else
            //{
            //    trucadesObject = new ObservableCollection<object>();
            //}






                //cboxPerits.SelectedIndex = getIndexPeritSinistre();

                //checkTancat.IsEnabled = Sinistre.EstatSininistre != Enums.MOTIU_VICTORIA.TANCAT;
                //cboxPerits.IsEnabled = Sinistre.EstatSininistre != Enums.MOTIU_VICTORIA.TANCAT;
                //btnCanviarPerit.IsEnabled = Sinistre.EstatSininistre != Enums.MOTIU_VICTORIA.TANCAT;

        }

        //private int getIndexPeritSinistre()
        //{
        //    foreach (Inscrit p in Perits)
        //    {
        //        if (Sinistre.Perit != null && p.Numero == Sinistre.Perit.Numero)
        //        {
        //            return Perits.IndexOf(p);
        //        }
        //    }
        //    return -1;
        //}

        //public Partida Sinistre
        //{
        //    get { return (Partida)GetValue(SinistreProperty); }
        //    set
        //    {
        //        SetValue(SinistreProperty, value);

        //        DataContext = value;
        //    }


        //}

        //private ObservableCollection<Inscrit> mPerits;

        //public ObservableCollection<Inscrit> Perits
        //{
        //    get { return mPerits; }
        //    set
        //    {
        //        mPerits = value;
        //        cboxPerits.ItemsSource = value;
        //        cboxPerits.DisplayMemberPath = "FullName";

        //    }
        //}



      
        // Using a DependencyProperty as the backing store for MyProperty.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty MyPropertyProperty =
            DependencyProperty.Register("Classificacio", typeof(Classificacio), typeof(ControlClassificacio), new PropertyMetadata(0));



       

        //private void btnInformes_Click(object sender, RoutedEventArgs e)
        //{
        //    onClickEntradesInforme(this, null);
        //}

        //private void btnEnrere_Click(object sender, RoutedEventArgs e)
        //{
        //    onClickTornar(this, null);
        //}

        private void preparaLlistaClassificacio()
        {
            Dictionary<string, string> headerCamps = new Dictionary<string, string>();
            Dictionary<string, int> ordreCamps = new Dictionary<string, int>();
            Dictionary<string, bool> campsVisibles = new Dictionary<string, bool>();          
            double[] midesColumnes = { 1, 1,1,1,1,1 };

            campsVisibles["Posicio"] = true;
            campsVisibles["Nom"] = true;
            campsVisibles["Partides"] = true;
            campsVisibles["Jugades"] = true;
            campsVisibles["Guanyades"] = true;
            campsVisibles["Perdudes"] = true;
            campsVisibles["Coeficient"] = true;

            headerCamps["Posicio"] = "Posicio";
            headerCamps["Nom"] = "Nom";
            headerCamps["Partides"] = "Partides";
            headerCamps["Jugades"] = "Jugades";
            headerCamps["Guanyades"] = "Guanyades";
            headerCamps["Perdudes"] = "Perdudes";
            headerCamps["Coeficient"] = "Coeficient";


            ordreCamps["Posicio"] = 0;
            ordreCamps["Nom"] = 1;
            ordreCamps["Partides"] = 2;
            ordreCamps["Jugades"] = 3;
            ordreCamps["Guanyades"] = 4;
            ordreCamps["Perdudes"] = 5;
            ordreCamps["Coeficient"] = 6;


            lvSInscrits.CampsVisibles = campsVisibles;
            lvSInscrits.Capsaleres = headerCamps;
            lvSInscrits.OrdreCamps = ordreCamps;
            lvSInscrits.MidaColumnes = midesColumnes;
        }


      



        //private void btnCanviarPerit_Click(object sender, RoutedEventArgs e)
        //{
        //    setPeritSinistre();
        //    onClickUpdatePerit(this, null);
        //}

        //private void setPeritSinistre()
        //{

        //    if (cboxPerits.SelectedIndex != -1 && getIndexPeritSinistre() != Perits.IndexOf((Inscrit)cboxPerits.SelectedItem))
        //    {
        //        Sinistre.Perit = (Inscrit)cboxPerits.SelectedItem;
        //        if (checkTancat.IsChecked == true && checkTancat.IsEnabled)
        //        {
        //            Sinistre.EstatSininistre = Enums.MOTIU_VICTORIA.TANCAT;
        //        }
        //        else
        //        {
        //            Sinistre.EstatSininistre = Enums.MOTIU_VICTORIA.ASSIGNAT;
        //        }
        //    }
        //    else
        //    {
        //        if (checkTancat.IsChecked == true && checkTancat.IsEnabled)
        //        {
        //            Sinistre.EstatSininistre = Enums.MOTIU_VICTORIA.TANCAT;
        //        }
        //    }
        //}


    }
}
