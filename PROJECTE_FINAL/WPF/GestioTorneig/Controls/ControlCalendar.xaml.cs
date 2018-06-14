using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI;
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
    public sealed partial class ControlCalendar : UserControl
    {
        public ControlCalendar()
        {
            this.InitializeComponent();
        }


        public event EventHandler DateChanged;

        private DateTime mCurrentDate;

        public DateTime MCurrentDate
        {
            get { return mCurrentDate; }
            set
            {
                mCurrentDate = value;
                DateChanged?.Invoke(this, null);
                if (!mDibuixat)
                {
                    dibuixaMes(value);
                    MDibuixat = true;
                }
            }
        }

        private bool mDibuixat = false;

        public bool MDibuixat
        {
            get { return mDibuixat; }
            set { mDibuixat = value; }
        }


        private void dibuixaMes(DateTime dataActual)
        {

            buidarGrid();

            txblkMes.Text = dataActual.ToString("MMMM");

            DateTime dataAux = new DateTime(dataActual.Year, dataActual.Month, 1);
            // int i = 1;
            int diesMes = DateTime.DaysInMonth(dataActual.Year, dataActual.Month);


            int dia = ((int)dataAux.DayOfWeek == 0) ? 7 : (int)dataAux.DayOfWeek;
            int row = 0;
            for (int i = 0; i < diesMes; i++)
            {

                int diaD = ((int)dataAux.DayOfWeek == 0) ? 7 : (int)dataAux.DayOfWeek;

                Button btnDia = new Button();

                btnDia.Content = dataAux.Day;


                if (diaD == 6 || diaD == 7)
                {
                    btnDia.Background = new SolidColorBrush(Colors.Red);
                }

                btnDia.Click += BtnDia_Click;
                gridCalendari.Children.Add(btnDia);
                Grid.SetColumn(btnDia, diaD - 1);
                Grid.SetRow(btnDia, row);
                dataAux = dataAux.AddDays(1);
                if (diaD == 7)
                {
                    row++;
                }
            }

        }

        private void BtnDia_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                desclicarAnterior();
            }
            catch (Exception)
            {

            }
            Button clicat = (Button)sender;
            int x;
            int.TryParse(clicat.Content.ToString(), out x);
            //clicat.Background = new SolidColorBrush(Colors.Orange);
            clicat.Tag = "clicat";
            clicat.BorderBrush = new SolidColorBrush(Colors.Red);
            clicat.BorderThickness = new Thickness(1);
            MCurrentDate = new DateTime(mCurrentDate.Year, mCurrentDate.Month, x);
        }

        private void desclicarAnterior()
        {    // Depen de com falla i nose pk.. ignora el tag
            foreach (Button b in gridCalendari.Children)
            {
                if (b.Tag.Equals("clicat"))
                {
                    b.BorderBrush = new SolidColorBrush(Colors.Transparent);
                }
            }
        }

        private void buidarGrid()
        {
            gridCalendari.Children.Clear();

        }

        private void btnEndavant_Click(object sender, RoutedEventArgs e)
        {
            MDibuixat = false;
            MCurrentDate = MCurrentDate.AddMonths(1);
        }

        private void btnEnrere_Click(object sender, RoutedEventArgs e)
        {
            mDibuixat = false;
            MCurrentDate = MCurrentDate.AddMonths(-1);
        }
    }
}
