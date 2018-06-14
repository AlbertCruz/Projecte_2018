using GestioTorneig.Controls;
using System;
using System.Collections.Generic;
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
using GestioTorneig.Model;

// La plantilla de elemento Control de usuario está documentada en https://go.microsoft.com/fwlink/?LinkId=234236

namespace GestioTorneig.Controls
{
    public sealed partial class PartidaItem : UserControl
    {
        public PartidaItem()
        {
            this.InitializeComponent();
        }

       

        public Partida Partida
        {
            get { return (Partida)GetValue(PartidaProperty); }
            set { SetValue(PartidaProperty, value); }
        }

        // Using a DependencyProperty as the backing store for Partida.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty PartidaProperty =
            DependencyProperty.Register("Partida", typeof(Partida), typeof(PartidaItem), new PropertyMetadata(null));


    }
}
