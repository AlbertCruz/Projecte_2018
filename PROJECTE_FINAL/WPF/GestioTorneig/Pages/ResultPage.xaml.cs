using GestioTorneig.BD;
using GestioTorneig.Controls;
using GestioTorneig.Model;
using GestioTorneig.Pages.Model;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Popups;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// The Blank Page item template is documented at http://go.microsoft.com/fwlink/?LinkId=234238

namespace GestioTorneig.Pages
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class ResultPage : Page
    {
        public ResultPage()
        {
            this.InitializeComponent();
        }
        
        public static Model.Classificacio r;
        private Torneo Torneig;
        private Partida Partida;
        private Grup Grup;
        private ObservableCollection<Grup> lGrups = new ObservableCollection<Grup>();
        private ObservableCollection<Object> lobject = new ObservableCollection<Object>();
        private ObservableCollection<Partida> lPartides = new ObservableCollection<Partida>();
      
        private bool bCanvis = false;
        private bool bCanviantSeleccio = false;
        private BDConnector con;
        private String sms = "";

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);
            if (e != null)
            {
                prepararLlistaGrups(lvGrups);
              
                con = new BDConnector("server=92.222.27.83;uid=m2-acruz;pwd=47111916G;database=m2_acruz;SslMode=None;");
                r = (Model.Classificacio)e.Parameter;
                if (r!= null && r.IdTorneig >= 0)
                {
                    Torneig = r.ResTornejos.ElementAt(r.IdTorneig);
                    lGrups =con.getgruposinfiltro(Torneig);
                    Torneig.Grups = lGrups;
                   
        
                   lvGrups.ItemsSource = new ObservableCollection<object>(lGrups);
                    lvGrups.onSelected_changed += lvGrups_onSelected_changed;

                    lvGrups.ObjecteSeleccionat = (lGrups.Count > 0) ? 0 : -1;
                    //kitar
                    Torneig.Preinscripcion = false;

                    if (Torneig.Preinscripcion|| !(Torneig.Grups.Count()>0))
                    {
                        SetFormEnabled(false);
                    }
                }
                else
                {
                    SetFormEnabled(false);
                }
            }
            if (r!= null) r.Canvis = false;
        }

        private void SetFormEnabled(bool bEnabled)
        {
            tbCarambolesA.IsEnabled = tbCarambolesB.IsEnabled = tbEntradesA.IsEnabled = tbEntradesB.IsEnabled = tbEntradesTotals.IsEnabled = cbEstatPartida.IsEnabled = cbModeVictoria.IsEnabled = cbGuanyador.IsEnabled = btSavePartida.IsEnabled = bEnabled;
        }

        private void lvGrups_onSelected_changed(object sender, EventArgs e) {

            //if (!bCanviantSeleccio) CanviarGrup();
            CanviarGrup();
        }

        public void prepararLlistaGrups(Control_ListViewDBCreator lvGrups)
        {


            Dictionary<string, string> headerCamps = new Dictionary<string, string>();
            Dictionary<string, int> ordreCamps = new Dictionary<string, int>();
            Dictionary<string, bool> campsVisibles = new Dictionary<string, bool>();
            double[] midesColumnes = { 20, 20};


            campsVisibles["Descripcio"] = true;
            campsVisibles["MismoGrupo"] = true;        


            headerCamps["Descripcio"] = "Descriccio";
            headerCamps["MismoGrupo"] = "IdGrup";
          

            ordreCamps["Descripcio"] = 0;
            ordreCamps["MismoGrupo"] = 1;
          

            lvGrups.CampsVisibles = campsVisibles;
            lvGrups.Capsaleres = headerCamps;
            lvGrups.OrdreCamps = ordreCamps;
        

        }

        private async void CanviarGrup()
        {
            bool bOk = true;

            if (bCanvis)
            {
                sms="Hi han modificacions pendents";
                var ek = new MessageDialog(sms);               
                await ek.ShowAsync();
                bOk = false;
            }

            if (bOk)
               
                {
                bCanvis = false;
                var idx =lvGrups.ObjecteSeleccionat;
                if (!(idx ==null))
                {
                   // Grup = lGrups.ElementAt();
                    Grup g = (Grup)lvGrups.ObjecteSeleccionat;
                    lPartides = g.Partides;                 
                    ObservableCollection<object> objetctPart = new ObservableCollection<object>(lPartides);    
                    lvPartides.ItemsSource = objetctPart;
                    lvPartides.ObjecteSeleccionat = (lPartides.Count > 0) ? 0 : -1;
                }
                else
                {
                    lvPartides.ItemsSource = null;
                    lvPartides.ObjecteSeleccionat= -1;
                }
                bCanvis = false;
            }
            else
            {
                bCanviantSeleccio = true;
                lvGrups.ObjecteSeleccionat = lGrups.IndexOf(Grup);
                bCanviantSeleccio = false;
            }
        }

        private void lvPartides_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (!bCanviantSeleccio) CanviarPartida();
        }

        private async void CanviarPartida()
        {
            bool bOk = true;


            if (bCanvis)
            {
                bOk = await MessageBox.ShowConfirm("Hi han modificacions pendents.", "Hi han modificacions pendents de grabar, segur que vols seguir amb la selecció sense guardar?", false);
            }
           
            

            if (bOk)
            {
                object idx = lvPartides.ObjecteSeleccionat;
                Partida p = (Partida)idx;

                Partida = (!(idx==null)) ? lPartides.ElementAt(lvPartides.IndexItemSeleccionat) : null;
                establirDades();
            }
            else
            {
                bCanviantSeleccio = true;
                lvPartides.ObjecteSeleccionat = lPartides.IndexOf(Partida);
                bCanviantSeleccio = false;
            }
        }

        private void establirDades()
        {
            bool bEnableForm = false;

            if (Torneig != null && Grup != null && Partida != null)
            {
                cbGuanyador.Items.Clear();
                cbGuanyador.Items.Add(Partida.InscritA);
                cbGuanyador.Items.Add(Partida.InscritB);

                tbCarambolesA.Text = Partida.CarambolesA + "";
                tbCarambolesB.Text = Partida.CarambolesB + "";
                tbEntradesA.Text = Partida.NumEntrades + "";
                tbEntradesB.Text = Partida.NumEntrades + "";
                tbEntradesTotals.Text = Partida.NumEntrades + "";
                cbEstatPartida.SelectedIndex = (int)Partida.EstatPartida;
                cbModeVictoria.SelectedIndex = (int)Partida.MotiuVictoria;
                cbGuanyador.SelectedIndex = (int)Partida.ganador;

                bEnableForm = (!Torneig.Preinscripcion && !(Torneig.Grups.Count>0) && Partida.EstatPartida == Enums.ESTAT_PARTIDA.PENDENT);
            }
            else
            {
                cbGuanyador.Items.Clear();
                tbCarambolesA.Text = "";
                tbCarambolesB.Text = "";
                tbEntradesA.Text = "";
                tbEntradesB.Text = "";
                tbEntradesTotals.Text = "";
                cbEstatPartida.SelectedIndex = -1;
                cbModeVictoria.SelectedIndex = -1;
                cbGuanyador.SelectedIndex = -1;
            }

            SetFormEnabled(bEnableForm);
            bCanvis = false;
        }

        private void ComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            bCanvis = true;
        }

        private void TextBox_KeyDown(object sender, KeyRoutedEventArgs e)
        {
            if (!e.Key.ToString().Contains("Number"))
            {
                e.Handled = true;
            }
            else
            {
                try
                {
                    Int32.Parse(((TextBox)sender)?.Text + e.Key.ToString().Replace("Number", ""));
                }
                catch (OverflowException) { e.Handled = true; }
            }
            if (!e.Handled) bCanvis = true;
        }

        private void btSavePartida_Click(object sender, RoutedEventArgs e)
        {
            GuardarCanvis();
        }

        private async void GuardarCanvis()
        {
            bool bOk = true;

            if (bCanvis)
            {
                bOk = await MessageBox.ShowConfirm("Guardar canvis.", "Estas segur de que vols guardar els canvis realitzats a la partida?", false);
            }

            if (bOk)
            {
                            }

            if (bOk)
            {
                int carambolesA = Int32.Parse((tbCarambolesA.Text.Length > 0) ? tbCarambolesA.Text : "0");
                int carambolesB = Int32.Parse((tbCarambolesB.Text.Length > 0) ? tbCarambolesB.Text : "0");
                int entradesA = Int32.Parse((tbEntradesA.Text.Length > 0) ? tbEntradesA.Text : "0");
                int entradesB = Int32.Parse((tbEntradesB.Text.Length > 0) ? tbEntradesB.Text : "0");
                int numeroEntrades = Int32.Parse((tbEntradesTotals.Text.Length > 0) ? tbEntradesTotals.Text : "0");

                if ((carambolesA + carambolesB) > numeroEntrades)
                {
                    MessageBox.Show("Dades de partida no vàlides.", "La suma de caramboles dels dos socis es major al número d'entrades totals!");
                }
                else if ((entradesA + entradesB) != numeroEntrades)
                {
                    MessageBox.Show("Dades de partida no vàlides.", "La suma d'entrades dels dos socis no es el valor d'entrades totals!");
                }
                else if (carambolesA > entradesA)
                {
                    MessageBox.Show("Dades de partida no vàlides.", "Les caramboles del soci A són majors a les entrades que ha realitzat!");
                }
                else if (carambolesB > entradesB)
                {
                    MessageBox.Show("Dades de partida no vàlides.", "Les caramboles del soci B són majors a les entrades que ha realitzat!");
                }

             
                //else if (carambolesA + carambolesB > Grup.CaramVictoria)
                //{
                //    MessageBox.Show("Dades de partida no vàlides.", "La suma de caramboles dels socis supera el máxim de caramboles de victoria establert al grup!");
                //}
                //else if ((entradesA > Grup.LimitEntrades) || (entradesB > Grup.LimitEntrades))
                //{
                //    MessageBox.Show("Dades de partida no vàlides.", "La suma d'entrades dels socis supera el límit d'entrades establert al grup");
                //}
                else
                {
                    Enums.ESTAT_PARTIDA estatPartida = (cbEstatPartida.SelectedIndex >= 0) ? (Enums.ESTAT_PARTIDA)cbEstatPartida.SelectedIndex : Enums.ESTAT_PARTIDA.PENDENT;
                    Enums.MOTIU_VICTORIA modeVictoria = (cbModeVictoria.SelectedIndex >= 0) ? (Enums.MOTIU_VICTORIA)cbModeVictoria.SelectedIndex : Enums.MOTIU_VICTORIA.PER_CARAMBOLES;
                    Enums.GUANYADOR guanyador = (cbGuanyador.SelectedIndex >= 0) ? (Enums.GUANYADOR)cbGuanyador.SelectedIndex : Enums.GUANYADOR.A;

                    Partida p = new Partida(Partida.Id, carambolesA, carambolesB,null, Partida.InscritA, Partida.InscritB, numeroEntrades, estatPartida);
                    p.ganador = guanyador;

            if (con.ActualizaPartida(p,r.idGrup)==true)
                    {
                        Partida.CarambolesA = carambolesA;
                        Partida.CarambolesB = carambolesB;                       
                        Partida.NumEntrades = numeroEntrades;
                        Partida.EstatPartida = estatPartida;
                        Partida.MotiuVictoria = modeVictoria;
                        Partida.ganador = guanyador;
                        establirDades();


                        MessageBox.Show("Partida modificada.", "La partida seleccionada s'ha modificat correctament!");
                    }
                    else
                    {
                        MessageBox.Show("Error al modificar partida.", "Hi ha hagut un error al modificar la partida seleccionada!");
                    }
                }

            }
        }

    
}
}
