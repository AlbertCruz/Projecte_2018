using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Media;

namespace GestioTorneig.Controls
{
    public class BackgroundStyleSelecter : StyleSelector
    {

        protected override Style SelectStyleCore(object item, DependencyObject container)
        {
            ListViewModelItem listViewItem = item as ListViewModelItem;
            SolidColorBrush background = ((App.index % 2) == 0 ? new SolidColorBrush(Colors.Gold) : new SolidColorBrush(Colors.Silver));
            TextBox xx = new TextBox();
            double fontSize = (double)App.Current.Resources["ControlContentThemeFontSize"];
            double minWidth = (double)App.Current.Resources["ListViewItemMinWidth"];
            double minHeight = (double)App.Current.Resources["ListViewItemMinHeight"];

            Style style = new Style(typeof(ListViewItem));
            style.Setters.Add(new Setter(ListViewItem.BackgroundProperty, background));
            style.Setters.Add(new Setter(ListViewItem.FontSizeProperty, fontSize));
            style.Setters.Add(new Setter(ListViewItem.TabNavigationProperty, "Local"));
            style.Setters.Add(new Setter(ListViewItem.IsHoldingEnabledProperty, "True"));
            style.Setters.Add(new Setter(ListViewItem.PaddingProperty, "0,0,0,0"));
            style.Setters.Add(new Setter(ListViewItem.HorizontalContentAlignmentProperty, "Stretch"));
            style.Setters.Add(new Setter(ListViewItem.VerticalContentAlignmentProperty, "Stretch"));
            style.Setters.Add(new Setter(ListView.MinWidthProperty, minWidth));
            style.Setters.Add(new Setter(ListView.MinHeightProperty, minHeight));

            //style.Setters.Add(new Setter(ListViewItem.H));
            App.index++;
            return style;
        }
    }

    class ListViewModelItem
    {
        static int _LastPosition = 0;
        int _Position = -1;
        public int Position
        {
            get
            {
                return _Position;
            }
        }

        public ListViewModelItem()
        {
            if (_Position == -1)
            {
                _Position = _LastPosition;
                _LastPosition++;

            }
        }
    }



}
