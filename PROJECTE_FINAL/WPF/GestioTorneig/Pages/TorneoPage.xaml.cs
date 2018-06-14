using GestioTorneig.BD;
using GestioTorneig.Controls;
using GestioTorneig.Model;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.Linq;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using System.IO;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;



using Windows.UI.Popups;
using GestioTorneig.Pages.Model;
// La plantilla de elemento Página en blanco está documentada en http://go.microsoft.com/fwlink/?LinkId=234238

namespace GestioTorneig.Pages
{
    /// <summary>
    /// Una página vacía que se puede usar de forma independiente o a la que se puede navegar dentro de un objeto Frame.
    /// </summary>
    public sealed partial class TorneoPage : Page

    {
        static Torneo torneoActual;
        static Grup GActual = null;
        static int grupActual = 0;
        static int mismogrupo = 0;
        static bool primero = true;
        static bool tancat = false;
        static bool actiu = false;
        static bool check1 = false;
        static int ID = -1;
        private Model.Classificacio r;


        static public ObservableCollection<Soci> socis { get; set; } = new ObservableCollection<Soci>();
        static public ObservableCollection<Socilist> socislist { get; set; } = new ObservableCollection<Socilist>();
        public ObservableCollection<Partida> partidas { get; set; } = new ObservableCollection<Partida>();
        public ObservableCollection<TorneoActiu> torneosActius { get; set; } = new ObservableCollection<TorneoActiu>();
        public ObservableCollection<Ttancats> torneosTancats { get; set; } = new ObservableCollection<Ttancats>();
        public ObservableCollection<Torneo> torneos { get; set; } = new ObservableCollection<Torneo>();
        public ObservableCollection<Grup> grups = new ObservableCollection<Grup>();
        public ObservableCollection<ActiuGrup> gruposActius { get; set; } = new ObservableCollection<ActiuGrup>();
        public List<String> Modalitats { get; set; } = new List<String>();
        public List<String> activo { get; set; } = new List<String>();       
        private BDConnector con;
       
        public TorneoPage()
        {
            this.InitializeComponent();
            Loaded += TorneoPage_Loaded;

        }

        private void TorneoPage_Loaded(object sender, RoutedEventArgs e)
        {
            ObservableCollection<Grup> Grunpinit = new ObservableCollection<Grup>();
            prepararLlistaInscrit();
            prepararLlistaTorneos(lvSTorneos);
            //con = new BDConnector("server=127.0.0.1;uid=root;pwd=root;database=projecte;SslMode=None;");
            con = new BDConnector("server=92.222.27.83;uid=m2-acruz;pwd=47111916G;database=m2_acruz;SslMode=None;");
            getModalitat();          
            getActiu();
            cboxModalitat.ItemsSource = Modalitats;
            cboxActivo.ItemsSource = activo;
            socislist = con.getLlistaSocis();
           
            torneos = con.getLlistaTorneos();
            foreach (Torneo t in torneos)
            {
                t.Grups = con.getgruposinfiltro(t);

            }
            ObservableCollection<object> socisObject = new ObservableCollection<object>(socislist);
            ObservableCollection<object> TorneosObject = new ObservableCollection<object>(torneos);
           
            lvSInscrits.ItemsSource = socisObject;
            lvSTorneos.ItemsSource = TorneosObject;
            lvSTorneos.onSelected_changed += lvSTorneos_onSelected_changed;
            dpDataFrom.IsEnabled = dpDataTo.IsEnabled = true;
        }

        private void preperarListaTorneosActius(Control_ListViewDBCreator lvTorneos)
        {

            Dictionary<string, string> headerCamps = new Dictionary<string, string>();
            Dictionary<string, int> ordreCamps = new Dictionary<string, int>();
            Dictionary<string, bool> campsVisibles = new Dictionary<string, bool>();
            double[] midesColumnes = { 20, 20, 20 };

            campsVisibles["DataInici"] = true;
            campsVisibles["Qtgrup"] = true;
            campsVisibles["Qtpartida"] = true;        


            headerCamps["DataInici"] = "Data Inici";
            headerCamps["Qtgrup"] = "qtat grup";
            headerCamps["Qtpartida"] = "qtat partida";           

            ordreCamps["DataInici"] = 0;
            ordreCamps["Qtgrup"] = 1;
            ordreCamps["Qtpartida"] = 2;          

            lvTorneos.CampsVisibles = campsVisibles;
            lvTorneos.Capsaleres = headerCamps;
            lvTorneos.OrdreCamps = ordreCamps;
            lvTorneos.MidaColumnes = midesColumnes;
        }

        private void lvSTorneos_onSelected_changed(object sender, EventArgs e)
        {

            Control_ListViewDBCreator lvSTorneos = (Control_ListViewDBCreator)sender;
            if (lvSTorneos.IndexItemSeleccionat != -1)
            {
                ID = lvSTorneos.IndexItemSeleccionat;

                if (actiu == true)
                {
                    TorneoActiu a = (TorneoActiu)lvSTorneos.ObjecteSeleccionat;
                    tbErrorTorneo.Text = "Torneos Activos";
                }
                else
                {
                    Torneo p = (Torneo)lvSTorneos.ObjecteSeleccionat;
                    if (p != null)
                    {
                        torneoActual = p;
                        if (p.Preinscripcion == false)//-->0-->activos
                        {
                            preperarListaTorneosActius(lvSTorneos);
                            torneosActius = con.getTorneosActiu();
                            ObservableCollection<object> torneosObject = new ObservableCollection<object>(torneosActius);
                            lvSTorneos.ItemsSource = torneosObject;
                            gruposActius = con.getgrupo(p.Id);
                            ObservableCollection<object> GrupObject = new ObservableCollection<object>(gruposActius);
                            lvSgrup.ItemsSource = GrupObject;
                            actiu = true;
                            tbErrorTorneo.Text = "Torneos Activos";

                        }
                        else
                        {
                            prepararLlistaTorneosTancat(lvSTorneos);
                            torneosTancats = con.getTorneosTanctats();
                            ObservableCollection<object> tancatsObject = new ObservableCollection<object>(torneosTancats);
                            lvSTorneos.ItemsSource = tancatsObject;
                            tancat = true;
                            tbErrorTorneo.Text = "Torneos Cerrados";
                        }
                    }
                }
            }
        }
        
        private void getModalitat()
        {
            Modalitats.Add("Lliure");
            Modalitats.Add("a 1 Banda");
            Modalitats.Add("a 3 Bandes");
        }
        private void getActiu()
        {
            activo.Add("Cerradas");
            activo.Add("Abiertas");
        }


        private void btnAddTorneo_Click(object sender, RoutedEventArgs e)
        {

            btnAddTorneo.IsEnabled = false;
            var date = dpDataini.Date;
            DateTime dataIni = DateTime.Today.Date;
            if (date != null)
            {
                dataIni = date.Value.DateTime;
            }
            btnCloseTorneo.IsEnabled = false;
            int idTorneoActual = con.getInsertIdTorneo();
            Modalitat m = new Modalitat(cboxModalitat.SelectedIndex, cboxModalitat.SelectedItem.ToString());
            torneoActual = new Torneo(idTorneoActual, tbTitol.Text, dataIni, m, true);
            Debug.WriteLine(dataIni.Date);
            con.insertTorneo(idTorneoActual, torneoActual.Nom, dataIni.Date, torneoActual.Modalitat, torneoActual.Preinscripcion);
            torneos.Add(torneoActual);
            ObservableCollection<object> TorneosObject = new ObservableCollection<object>(torneos);
            lvSTorneos.ItemsSource = TorneosObject;
        }
               
        

        private void btnEsborrar_Click(object sender, RoutedEventArgs e)
        {
            string missatgeError = "";          
            if (torneoActual.Grups.Count == 0)
            {
                con.deleteTorneo(torneoActual.Id, out missatgeError);
                lvSTorneos.ItemsSource = new ObservableCollection<object>(con.getLlistaTorneos());

            }
            else
            {        
                missatgeError = "El torneig te dades asignades ,el vol borrar permanentment";               
                espera(missatgeError, sender, e);
            }
            
        }
              
        

        private async void espera(string sms,object sender, RoutedEventArgs e)
        {
            //  await System.Threading.Tasks.Task.Delay(2000);
       
            var dialog = new Windows.UI.Popups.MessageDialog(sms);
            dialog.Commands.Add(new Windows.UI.Popups.UICommand("Yes") { Id = 0 });
            dialog.Commands.Add(new Windows.UI.Popups.UICommand("No") { Id = 1 });

            if (Windows.System.Profile.AnalyticsInfo.VersionInfo.DeviceFamily != "Windows.Mobile")
            {
                // Adding a 3rd command will crash the app when running on Mobile !!!
                dialog.Commands.Add(new Windows.UI.Popups.UICommand("Mas Tarde") { Id = 2 });
            }

            dialog.DefaultCommandIndex = 0;
            dialog.CancelCommandIndex = 1;
            var result = await dialog.ShowAsync();
            var btn = sender as Button;
            btn.Content = $"Result: {result.Label} ({result.Id})";                
            cascade();

        }

        private void cascade()
        {
            actiu=false;
            string missatgeError = "";
            con.deleteTorneo(ID, out missatgeError);               
            lvSTorneos.ItemsSource = new ObservableCollection<object>(con.getLlistaTorneos());

        }

        private void desactivarFiltre()
        {
            dpDataini.Date = null;
            dpDataini.Date = null;
            cboxActivo.SelectedIndex = -1;
            lvSTorneos.ItemsSource = new ObservableCollection<object>(con.getLlistaTorneos());
            lvSTorneos.IndexItemSeleccionat = -1;
        }

       

        private void AddGroup_Click(object sender, RoutedEventArgs e)
        {
            int caramboles = 0;
            int entrades = 0;
            string nom = "";
            // CloseGroup.IsEnabled = false;     

            try
            {
                if (!(String.IsNullOrEmpty(txtnom.Text.ToString())))
                {
                    nom = txtnom.Text.ToString();
                }
                if (!(String.IsNullOrEmpty(txtcCaram.ToString())))
                {
                    caramboles = Int32.Parse(txtcCaram.Text.ToString());
                }
                if (!(String.IsNullOrEmpty(txtEntrades.Text.ToString())))
                {
                    entrades = Int32.Parse(txtEntrades.Text.ToString());
                }
                if (lvSInscrits.IndexItemSeleccionat != -1)

                {
                    Socilist i = (Socilist)lvSInscrits.ObjecteSeleccionat;
                    grupActual = con.getIdGrup();
                    if (primero == true)
                    {
                        
                            mismogrupo = grupActual;
                            primero = false;
                            int idPartida = 0;
                            Grup gp = new Grup(grupActual, torneoActual, nom, caramboles, entrades, mismogrupo);
                            torneoActual.Grups.Add(gp);
                            con.AddGroup(gp.Id, gp.Torneig.Id, gp.Descripcio, gp.CaramVictoria, gp.LimitEntrades, mismogrupo, idPartida);
                            GActual = gp;
                           // lvSInscrits.ItemsSource.Remove(i);
                            primero = false;                       
                    }
                    lvSInscrits.ItemsSource.Remove(i);
                    DateTime d = DateTime.Today.Date;
                    Inscrit ins = new Inscrit(i.Id, GActual, torneoActual, d);
                    con.AddInscrit(ins.Numero, mismogrupo, ins.Torneo.Id, ins.DataInscrp);
                   //  gp.Inscrits.Add(ins);                
                    GActual.addInscrit(ins);
                    if (GActual.Inscrits.Count() > 1)
                    {
                        btnCloseTorneo.IsEnabled = true;
                    }
                }
            }
            catch
            {
                //mensaje eerrooo

            }
        }

        public void CloseGroup_Click(object sender, RoutedEventArgs e)
        {
            primero = false;
            // con.getIdGrup();
            grupActual = con.getIdGrup();
            txtnom.Text = "";
            txtcCaram.Text = "";
            txtEntrades.Text = "";
            controlBotones();
        }



        private void btnCloseTorneo_Click(object sender, RoutedEventArgs e)
        {
            int i = con.tancarPartides(torneoActual.Id);
            if (i<=0)
            {
                btnCloseTorneo.IsEnabled = true;
                con.tancarTorneig(torneoActual);
            }
            controlBotones();
        }

       

        private void controlBotones()
        {
            foreach (Inscrit index in GActual.Inscrits)
            {
                for (int n = 0; n < GActual.Inscrits.Count - 1; n++)
                {
                    if (index.Equals(GActual.Inscrits[n]))
                    {
                        n++;
                    }
                    int id_partida = con.getIdPartida();
                    Partida p = new Partida(id_partida, 0, 0, null, index, GActual.Inscrits[n], 0, Enums.ESTAT_PARTIDA.PENDENT);
                    partidas.Add(p);
                    con.addPartida(p,torneoActual.Id);
                    con.updateGrup(p.Id, GActual.Id);
                }
            }

        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);

            if (e.Parameter is string)
            {

            }
            else
            {
                con = new BDConnector("server=92.222.27.83;uid=m2-mhinojosa;pwd=46776946Y;database=m2_mhinojosa;SslMode=None;");    
                int i = ID;
           
            }
        }

        private void prepararLlistaInscrit()
        {
            Dictionary<string, string> headerCamps = new Dictionary<string, string>();
            Dictionary<string, int> ordreCamps = new Dictionary<string, int>();
            Dictionary<string, bool> campsVisibles = new Dictionary<string, bool>();
            double[] midesColumnes = { 30, 30, 30, 30, 30 };

            campsVisibles["Id"] = true;
            campsVisibles["Nom"] = true;
            campsVisibles["Cognom1"] = true;
            campsVisibles["Cognom2"] = true;
            campsVisibles["Nif"] = true;

            headerCamps["Id"] = "Id";
            headerCamps["Nom"] = "Nom";
            headerCamps["Cognom1"] = "Cognom1";
            headerCamps["Cognom2"] = "Cognom2";
            headerCamps["Nif"] = "NIF";

            ordreCamps["Id"] = 0;
            ordreCamps["Nom"] = 1;
            ordreCamps["Cognom1"] = 2;
            ordreCamps["Cognom2"] = 3;
            ordreCamps["Nif"] = 4;


            lvSInscrits.CampsVisibles = campsVisibles;
            lvSInscrits.Capsaleres = headerCamps;
            lvSInscrits.OrdreCamps = ordreCamps;
            lvSInscrits.MidaColumnes = midesColumnes;

           // lvSTorneos.onSelected_changed += lvSTorneos_onSelected_changed;

        }

        public void prepararLlistaTorneos(Control_ListViewDBCreator lvTorneos)
        {


            Dictionary<string, string> headerCamps = new Dictionary<string, string>();
            Dictionary<string, int> ordreCamps = new Dictionary<string, int>();
            Dictionary<string, bool> campsVisibles = new Dictionary<string, bool>();
            double[] midesColumnes = { 20, 20, 20 };

            campsVisibles["Nom"] = true;
            campsVisibles["DataInici"] = true;
            campsVisibles["Preinscripcion "] = true;


            headerCamps["Nom"] = "Nom";
            headerCamps["DataInici"] = "DataInici";
            headerCamps["Preinscripcion"] = "Preinscripcion ";


            ordreCamps["Nom"] = 0;
            ordreCamps["DataInici"] = 1;
            ordreCamps["Preinscripcion"] = 2;

            lvTorneos.CampsVisibles = campsVisibles;
            lvTorneos.Capsaleres = headerCamps;
            lvTorneos.OrdreCamps = ordreCamps;
            lvTorneos.MidaColumnes = midesColumnes;
            //lvSTorneos.onSelected_changed += lvSTorneos_onSelected_changed;

        }

        public void prepararLlistaTorneosTancat(Control_ListViewDBCreator lvTorneos)
        {


            Dictionary<string, string> headerCamps = new Dictionary<string, string>();
            Dictionary<string, int> ordreCamps = new Dictionary<string, int>();
            Dictionary<string, bool> campsVisibles = new Dictionary<string, bool>();
            double[] midesColumnes = { 20, 20, 20, 20,20 };

            campsVisibles["DataClosed"] = true;
            campsVisibles["Desc"] = true;
            campsVisibles["Ganador"] = true;
            campsVisibles["inscritA"] = true;
            campsVisibles["inscritB"] = true;

           

            headerCamps["DataClosed"] = "Data  closed";
            headerCamps["Desc"] = "Descripcion";
            headerCamps["Ganador"] = "qtat partida";
            headerCamps["inscritA"] = "inscritA";
            headerCamps["inscritB"] = "inscritB";

            ordreCamps["DataClosed"] = 0;
            ordreCamps["Desc"] = 1;
            ordreCamps["Ganador"] = 2;
            ordreCamps["inscritA"] = 3;
            ordreCamps["inscritB"] = 4;


            lvTorneos.CampsVisibles = campsVisibles;
            lvTorneos.Capsaleres = headerCamps;
            lvTorneos.OrdreCamps = ordreCamps;
            lvTorneos.MidaColumnes = midesColumnes;
           // lvSTorneos.onSelected_changed += lvSTorneos_onSelected_changed;

        }


        private void prepararGrupo()
        {
            Dictionary<string, string> headerCamps = new Dictionary<string, string>();
            Dictionary<string, int> ordreCamps = new Dictionary<string, int>();
            Dictionary<string, bool> campsVisibles = new Dictionary<string, bool>();
            double[] midesColumnes = { 30, 30 };
            campsVisibles["Nom"] = true;
            campsVisibles["Total"] = true;

            headerCamps["Nom"] = "Nom";
            headerCamps["Total"] = "Total";

            ordreCamps["Nom"] = 0;
            ordreCamps["Total"] = 1;

            lvSInscrits.CampsVisibles = campsVisibles;
            lvSInscrits.Capsaleres = headerCamps;
            lvSInscrits.OrdreCamps = ordreCamps;
            lvSInscrits.MidaColumnes = midesColumnes;
            // lvSInscrits.onSelected_changed += lvSInscrits_onSelected_changed;

        }

       

        private void btnAtras_Click(object sender, RoutedEventArgs e)
        {
            prepararLlistaTorneos(lvSTorneos);
            torneos = con.getLlistaTorneos();
            ObservableCollection<object> TorneosObject = new ObservableCollection<object>(torneos);
            lvSTorneos.ItemsSource = TorneosObject;
            actiu = false;
            tancat = false;
            tbErrorTorneo.Text = "";
        }

        private void prepararaListaTorneo()
        {
            torneos = con.getLlistaTorneos();
            ObservableCollection<object> TorneosObject = new ObservableCollection<object>(torneos);
            lvSTorneos.ItemsSource = TorneosObject;
        }

        private void btnFiltreOn_Click(object sender, RoutedEventArgs e)
        {
            String dataFrom = " ";
            String dataTo = " ";
            dpDataFrom.IsEnabled = dpDataTo.IsEnabled = true;
            int act = cboxModalitat.SelectedIndex;
            if (check1 == true)
            {
                if (dpDataFrom.Date.Value != null && dpDataFrom.Date.Value != null)
                {
                    dataFrom = dpDataFrom.Date.Value.DateTime.ToString("yyyy-MM-dd");
                    dataTo = dpDataFrom.Date.Value.DateTime.ToString("yyyy-MM-dd");
                }
            }
            torneos = con.getTorneosFiltrados(dataFrom, dataTo, act);
            ObservableCollection<object> TorneosObject = new ObservableCollection<object>(torneos);
            lvSTorneos.ItemsSource = TorneosObject;
            lvSTorneos.onSelected_changed += lvSTorneos_onSelected_changed;
        }



        private async void dpDataTo_DateChanged(object sender, DatePickerValueChangedEventArgs e)
        {

            if (dpDataTo.Date.Value.DateTime < dpDataFrom.Date.Value.DateTime)
            {
                string sms = "DATE FROM SUPERIOR A DATE TO ";
                var ek = new MessageDialog(sms);
                await ek.ShowAsync();
            }
            check1 = true;
        }

        private void btnFiltreOff_Click(object sender, RoutedEventArgs e)
        {
            desactivarFiltre();
        }

      

        private void btnClass_editar_Click(object sender, RoutedEventArgs e)
        {
            Debug.WriteLine("VALOR de ID:  " + ID);           
           // Torneo escojido = torneos[ID];
            Debug.WriteLine("VALOR torrneo Actial:  " + torneoActual.Nom);
            GestioTorneig.Model.Classificacio enviado = new GestioTorneig.Model.Classificacio(torneoActual.Grups[0], con.inserClassInscrit(torneoActual.Grups[0].Id));
            Debug.WriteLine("VALOR DE Enviado:  " + enviado.ToString());            
            this.Frame.Navigate(typeof(ClassificacioPage), enviado);

        }

       

        private void button_Click(object sender, RoutedEventArgs e)
        {
            r = new Model.Classificacio(null, -1, false);
            Debug.WriteLine("VALOR de ID:  " + ID);
            Torneo escojido = torneos[ID];
            Debug.WriteLine("VALOR DE ESCOJIdO:  " + escojido.Nom);
            r.IdTorneig = ID;
            r.ResTornejos = con.getLlistaTorneos();        
             this.Frame.Navigate(typeof(ResultPage), r);
        }

        }
    }



        








    






