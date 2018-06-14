using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Text;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Markup;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// The User Control item template is documented at http://go.microsoft.com/fwlink/?LinkId=234236

namespace GestioTorneig.Controls
{
    public sealed partial class Control_ListViewDBCreator : UserControl
    {
        private string[] validValues = { "Header", "Item" };
        public event EventHandler onSelected_changed;
        public event EventHandler onDouble_tap;
        public Control_ListViewDBCreator()
        {
            this.InitializeComponent();
        }
        //visibilitat, ordre, contingut "Header"

        private ObservableCollection<Object> mItemsSource;

        public ObservableCollection<Object> ItemsSource
        {
            get { return mItemsSource; }
            set
            {
                mItemsSource = value;

                if (CampsVisibles != null && OrdreCamps != null && Capsaleres != null && MidaColumnes != null)
                {

                    generarListView(value);
                }
                else
                {
                    runDefaults(value);
                    generarListView(value);
                }

                if (((ObservableCollection<Object>)value).Count == 0) BuidaLlista();
                else MostraLlista();
            }
        }


        private void runDefaults(ObservableCollection<Object> objs)
        {
            if (objs.Count > 0)
            {
                Dictionary<string, string> headerCamps = new Dictionary<string, string>();
                Dictionary<string, int> ordreCamps = new Dictionary<string, int>();
                Dictionary<string, bool> campsVisibles = new Dictionary<string, bool>();

                Type t = objs.ElementAt(0).GetType();
                PropertyInfo[] properties = t.GetProperties();
                int x = properties.Length;
                double[] midaCols = new double[x];
                int pos = 0;
                for (int i = 0; i < properties.Length; i++)
                {


                    ordreCamps[properties[i].Name] = pos;
                    campsVisibles[properties[i].Name] = true;
                    headerCamps[properties[i].Name] = properties[i].Name;
                    midaCols[i] = 1;
                    pos++;

                }
                if (CampsVisibles == null)
                {//Mostra tots els camps
                    CampsVisibles = campsVisibles;
                }
                if (OrdreCamps == null)
                {//Mostra els camps per ordre de les propietats
                    OrdreCamps = ordreCamps;
                }
                if (Capsaleres == null)
                {//Mostra com a header el nom de la propietat
                    Capsaleres = headerCamps;
                }
                if (MidaColumnes == null)
                { // Totes les columnes amb el mateix ample
                    MidaColumnes = midaCols;
                }
            }
        }



        public void BuidaLlista()
        {
            lvGeneric.Visibility = Visibility.Collapsed;
            borderEmpty.Visibility = Visibility.Visible;
        }
        public void MostraLlista()
        {
            borderEmpty.Visibility = Visibility.Collapsed;
            lvGeneric.Visibility = Visibility.Visible;
        }

    


  

        public Object ObjecteSeleccionat
        {
            get { return lvGeneric.SelectedItem; }
            set { lvGeneric.SelectedItem = value; }
        }

      
        public Object UltimIbjListView
        {
            get { return ((ObservableCollection<Object>)lvGeneric.ItemsSource).Last<Object>(); }
        }


        public ListView ListViewObjects
        {
            get { return lvGeneric; }
        }

   
        public Object PrimerObjListView
        {
            get { return ((ObservableCollection<Object>)lvGeneric.ItemsSource).First<Object>(); ; }

        }


        public int IndexItemSeleccionat
        {
            get { return lvGeneric.SelectedIndex; }
            set { lvGeneric.SelectedIndex = value; }
        }


        private Dictionary<string, bool> mCampsVisibles;

        public Dictionary<string, bool> CampsVisibles
        {
            get { return mCampsVisibles; }
            set { mCampsVisibles = value; }
        }

        private Dictionary<string, int> mOrdreCamps;

        public Dictionary<string, int> OrdreCamps
        {
            get { return mOrdreCamps; }
            set { mOrdreCamps = value; }

        }

        private Dictionary<string, string> mCapsaleres;

        public Dictionary<string, string> Capsaleres
        {
            get { return mCapsaleres; }
            set { mCapsaleres = value; }
        }


        private double[] mMidaColumnes;

        public double[] MidaColumnes
        {
            get { return mMidaColumnes; }
            set { mMidaColumnes = value; }
        }




        private void generarListView(ObservableCollection<object> llistaObjects)
        {
            if (llistaObjects.Count != 0)
            {
                Type t = llistaObjects.ElementAt(0).GetType();
                PropertyInfo[] properties = t.GetProperties();

                lvGeneric.HeaderTemplate = createDataTemplate(properties, "Header");
                lvGeneric.ItemTemplate = createDataTemplate(properties, "Item");

                lvGeneric.ItemsSource = llistaObjects;
            }

        }



        private DataTemplate createDataTemplate(PropertyInfo[] properties, String type)
        {
            if (validValues.Contains(type))
            {

                StringBuilder sb = new StringBuilder();


                sb.Append("<DataTemplate xmlns=\"http://schemas.microsoft.com/winfx/2006/xaml/presentation\">");
                sb.Append("<Grid HorizontalAlignment=\"Stretch\">");
                sb.Append("<Grid.ColumnDefinitions>");
                for (int i = 0; i < CampsVisibles.Count; i++)
                {
                    if (MidaColumnes.Length == CampsVisibles.Count) sb.Append("<ColumnDefinition Width=\"" + MidaColumnes[i] + "*" + "\"/>");
                    else sb.Append("<ColumnDefinition Width=\"*\"/>");
                }
                sb.Append("</Grid.ColumnDefinitions>");
                int column = 0;
                int j = 0;
                string[] colors = { "Azure", "Aquamarine" };
                foreach (PropertyInfo p in properties)
                {
                    if (CampsVisibles.ContainsKey(p.Name))
                    {
                        if (type.Equals("Header"))
                        {
                            sb.Append("<Border BorderThickness=\"1\" BorderBrush=\"Black\" Background=\"Gray\" Grid.Column=\"" + OrdreCamps[p.Name] + "\">");
                            sb.Append("<TextBlock Text=\"" + Capsaleres[p.Name] + "\" VerticalAlignment=\"Center\" HorizontalAlignment=\"Center\"/>");
                        }
                        else if (type.Equals("Item"))
                        {
                            sb.Append("<Border BorderThickness=\"1\" BorderBrush=\"Black\" Grid.Column=\"" + OrdreCamps[p.Name] + "\">");
                            sb.Append("<TextBlock Text=\"{Binding " + p.Name + "}\" TextWrapping=\"Wrap\" VerticalAlignment=\"Center\" HorizontalAlignment=\"Center\"/>");
                        }
                        sb.Append("</Border>");
                        column++;
                        j++;

                    }

                }
                sb.Append("</Grid>");
                sb.Append("</DataTemplate>");
                return (DataTemplate)XamlReader.Load(sb.ToString());
            }
            else
            {
                throw new Exception("Type no valid, valors Vàlids: Header o Item");
            }
        }


        private void lvGeneric_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            onSelected_changed?.Invoke(this, null);
        }

        private void lvGeneric_DoubleTapped(object sender, DoubleTappedRoutedEventArgs e)
        {
            onDouble_tap?.Invoke(this, null);
        }
    }


}
