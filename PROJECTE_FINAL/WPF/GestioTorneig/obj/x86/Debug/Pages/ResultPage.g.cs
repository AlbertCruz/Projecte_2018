﻿#pragma checksum "C:\Users\alber\Desktop\PROJECTE_FINAL\WPF\GestioTorneig\Pages\ResultPage.xaml" "{406ea660-64cf-4c82-b6f0-42d48172a799}" "4CA2CCAAE77449C34BA299F9D847A7EA"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace GestioTorneig.Pages
{
    partial class ResultPage : 
        global::Windows.UI.Xaml.Controls.Page, 
        global::Windows.UI.Xaml.Markup.IComponentConnector,
        global::Windows.UI.Xaml.Markup.IComponentConnector2
    {
        /// <summary>
        /// Connect()
        /// </summary>
        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 14.0.0.0")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public void Connect(int connectionId, object target)
        {
            switch(connectionId)
            {
            case 1:
                {
                    this.lvGrups = (global::GestioTorneig.Controls.Control_ListViewDBCreator)(target);
                }
                break;
            case 2:
                {
                    this.lvPartides = (global::GestioTorneig.Controls.Control_ListViewDBCreator)(target);
                }
                break;
            case 3:
                {
                    this.tbCarambolesA = (global::Windows.UI.Xaml.Controls.TextBox)(target);
                    #line 82 "..\..\..\Pages\ResultPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.TextBox)this.tbCarambolesA).KeyDown += this.TextBox_KeyDown;
                    #line default
                }
                break;
            case 4:
                {
                    this.tbCarambolesB = (global::Windows.UI.Xaml.Controls.TextBox)(target);
                    #line 83 "..\..\..\Pages\ResultPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.TextBox)this.tbCarambolesB).KeyDown += this.TextBox_KeyDown;
                    #line default
                }
                break;
            case 5:
                {
                    this.tbEntradesA = (global::Windows.UI.Xaml.Controls.TextBox)(target);
                    #line 84 "..\..\..\Pages\ResultPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.TextBox)this.tbEntradesA).KeyDown += this.TextBox_KeyDown;
                    #line default
                }
                break;
            case 6:
                {
                    this.tbEntradesB = (global::Windows.UI.Xaml.Controls.TextBox)(target);
                    #line 85 "..\..\..\Pages\ResultPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.TextBox)this.tbEntradesB).KeyDown += this.TextBox_KeyDown;
                    #line default
                }
                break;
            case 7:
                {
                    this.tbEntradesTotals = (global::Windows.UI.Xaml.Controls.TextBox)(target);
                    #line 86 "..\..\..\Pages\ResultPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.TextBox)this.tbEntradesTotals).KeyDown += this.TextBox_KeyDown;
                    #line default
                }
                break;
            case 8:
                {
                    this.cbEstatPartida = (global::Windows.UI.Xaml.Controls.ComboBox)(target);
                    #line 87 "..\..\..\Pages\ResultPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.ComboBox)this.cbEstatPartida).SelectionChanged += this.ComboBox_SelectionChanged;
                    #line default
                }
                break;
            case 9:
                {
                    this.cbModeVictoria = (global::Windows.UI.Xaml.Controls.ComboBox)(target);
                    #line 91 "..\..\..\Pages\ResultPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.ComboBox)this.cbModeVictoria).SelectionChanged += this.ComboBox_SelectionChanged;
                    #line default
                }
                break;
            case 10:
                {
                    this.cbGuanyador = (global::Windows.UI.Xaml.Controls.ComboBox)(target);
                    #line 96 "..\..\..\Pages\ResultPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.ComboBox)this.cbGuanyador).SelectionChanged += this.ComboBox_SelectionChanged;
                    #line default
                }
                break;
            case 11:
                {
                    this.btSavePartida = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 97 "..\..\..\Pages\ResultPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btSavePartida).Click += this.btSavePartida_Click;
                    #line default
                }
                break;
            default:
                break;
            }
            this._contentLoaded = true;
        }

        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 14.0.0.0")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public global::Windows.UI.Xaml.Markup.IComponentConnector GetBindingConnector(int connectionId, object target)
        {
            global::Windows.UI.Xaml.Markup.IComponentConnector returnValue = null;
            return returnValue;
        }
    }
}

