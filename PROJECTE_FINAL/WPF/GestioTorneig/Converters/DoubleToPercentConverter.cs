using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml.Data;

namespace GestioTorneig.Converters
{
    class DoubleToPercentConverter : IValueConverter
    {
        //retorna el percentatge del double, el percentatge es pasa per parametre
        public object Convert(object value, Type targetType, object parameter, string language)
        {
            if (parameter != null)
            {
                return ((double)value) * double.Parse((string)parameter, CultureInfo.InvariantCulture);
            }
            return null;
        }

        public object ConvertBack(object value, Type targetType, object parameter, string language)
        {
            throw new NotImplementedException();
        }
    }
}
