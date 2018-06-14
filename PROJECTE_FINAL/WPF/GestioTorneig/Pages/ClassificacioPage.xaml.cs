using GestioTorneig.BD;
using GestioTorneig.Controls;
using GestioTorneig.Model;
using GestioTorneig.Pages.Model;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
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


using Windows.UI.Popups;



// La plantilla de elemento Página en blanco está documentada en https://go.microsoft.com/fwlink/?LinkId=234238

namespace GestioTorneig.Pages
{
    /// <summary>
    /// Una página vacía que se puede usar de forma independiente o a la que se puede navegar dentro de un objeto Frame.
    /// </summary>
    public sealed partial class ClassificacioPage : Page
    {
        private Model.Classificacio r;
        private Torneo Torneig;
        private BDConnector con;
        public ClassificacioPage()
        {
            this.InitializeComponent();
        }

        private void ClassificacioPage_Loaded(object sender, RoutedEventArgs e)
        {
            con = new BDConnector("server=92.222.27.83;uid=m2-acruz;pwd=47111916G;database=m2_acruz;SslMode=None;");
            Loaded += ClassificacioPage_Loaded;
          
        }




        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);
            if (e != null)
            {
                r = (Model.Classificacio)e.Parameter;
                if (r != null && r.IdTorneig >= 0)
                {
                    Torneig = r.ResTornejos.ElementAt(r.IdTorneig);
                    //Torneig.Grups = con.GetGrupsTorneig(Torneig.Id, false, true);
                    ObservableCollection<object> GrupsObject = new ObservableCollection<object>(Torneig.Grups);
                    LvGrup.ItemsSource = GrupsObject;                   
                    LvGrup.ObjecteSeleccionat= (Torneig.Grups.Count > 0) ? 0 : -1;

                }
            }
        }

        private void LvGrup_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            Grup g = (Grup)LvGrup.ObjecteSeleccionat;
            if (g != null)
            {
                ObservableCollection<object> ClsassObject = new ObservableCollection<object>(g.Partides);
                lvClassificacions.ItemsSource = ClsassObject;
            }
        }
    }
    }

