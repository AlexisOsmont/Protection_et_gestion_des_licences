using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace ProjetAnnuel
{
    /// <summary>
    /// Logique d'interaction pour Login.xaml
    /// </summary>
    public partial class LoginWindow : Window
    {
        public LoginWindow()
        {
            InitializeComponent();
            Project.LoginOk = false;
        }

        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            String username = tbUsername.Text;
            String password = psdbPasswd.Password;

            try
            {
                if (Project.testCredentials(username, password))
                {
                    Project.connectionUsername = username;
                    Project.connectionPassword = password;
                    Project.remindMe = (bool)chkRemindMe.IsChecked;
                }
                Project.LoginOk = true;
                DialogResult = true;
            } catch (Exception ex)
            {
                DialogResult = false;
            }
        }

        private void lblRegister_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            System.Diagnostics.Process.Start(Project.serverProtocol + "://" + Project.serverAddress + ":" + Project.serverPort + "/" + Project.registerLocation);
        }

        private void tbUsername_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Enter)
            {
                psdbPasswd.Focus();
            }
        }

        private void psdbPasswd_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Enter)
            {
                btnLogin_Click(this, null);
            }
        }

        private void wndLogin_Loaded(object sender, RoutedEventArgs e)
        {
            tbUsername.Focus();
            chkRemindMe.IsChecked = Project.remindMe;
        }
    }
}
