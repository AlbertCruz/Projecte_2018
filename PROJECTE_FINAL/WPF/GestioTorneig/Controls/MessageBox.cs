using System;
using System.Threading.Tasks;
using Windows.UI.Popups;
using Windows.UI.Xaml.Controls;

namespace GestioTorneig.Controls
{
    public static class MessageBox
    {
        public static async void Show(String title, String message)
        {
            var dialog = new MessageDialog(message, title);
            await dialog.ShowAsync();
        }

        public static async Task<bool> ShowConfirm(String tit, String msg, bool bOnlyCancel)
        {
            bool res = false;

            if (tit == null || msg == null) return res;

            ContentDialog locationPromptDialog;
            if (bOnlyCancel)
            {
                locationPromptDialog = new ContentDialog
                {
                    Title = tit,
                    Content = msg,
                   PrimaryButtonText = "Tancar"
                };
            } else { 
                locationPromptDialog = new ContentDialog
                {
                    Title = tit,
                    Content = msg,
                   SecondaryButtonText = "Cancelar",
                    PrimaryButtonText = "Acceptar"
                };
            }

           
            ContentDialogResult result = await locationPromptDialog.ShowAsync();

            res = (result == ContentDialogResult.Primary) ? true : false;

            return res;
        }

    }
}
