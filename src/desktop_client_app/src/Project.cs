using System;
using System.Collections.Generic;
using System.IO;
using System.Security.Principal;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace ProjetAnnuel
{
    internal static class Project
    {
        public static String rootAppFolder = Environment.ExpandEnvironmentVariables("%ProgramW6432%") + "\\LicencingApp";
        public static String licenceSubFolder = "\\config\\licence";
        public static String configSubFolder = "\\config";
        public static String configFile = "\\config";
        public static String serverProtocol = "https";
        public static String registerLocation = "register";
        public static bool licenceLoadSuccessfull = false;

        public static String serverAddress = "srv-dpi-proj-gestlic-auth.univ-rouen.fr";
        public static String serverPort = "443";
        public static String webServerPort = "8443";
        public static int maximumResponseTimeMs = 200;
        public static String connectionUsername { get; set; }
        public static String connectionPassword { get; set; }
        public static bool remindMe { get; set; }
        public static List<Licence> licences { get; private set; }

        public static String addedLicencePeriod = null;
        public static String addedLicenceName = null;
        public static bool LoginOk { get; set; }

        private static String getHardwareHash()
        {
            return "0000-0000-0000-0000";//TODO
        }
        public static void addLicence(Licence licence)
        {
            licences.Add(licence);
        }
        public static void removeLicence(Licence licence)
        {
            licences.Remove(licence);
        }
        public static bool connectToServer()
        {
            RestApiConnector.init(serverAddress, serverPort);
            return testConnection();
        }
        public static bool isAdministrator()
        {
            using (WindowsIdentity identity = WindowsIdentity.GetCurrent())
            {
                WindowsPrincipal principal = new WindowsPrincipal(identity);
                return principal.IsInRole(WindowsBuiltInRole.Administrator);
            }
        }
        public static void init()
        {
            bool ok = true;

            if (!isAdministrator())
            {
                MessageBox.Show("Please run this application as administrator !", "Activation software", MessageBoxButton.OK, MessageBoxImage.Exclamation);
                ok = false;
                System.Windows.Application.Current.Shutdown();
            }

            Licence.licenceFolder = rootAppFolder + licenceSubFolder;

            remindMe = true;

            if (Directory.Exists(rootAppFolder + configSubFolder))
            {
                if (File.Exists(rootAppFolder + configSubFolder + configFile))
                {
                    String fileContent = File.ReadAllText(rootAppFolder + configSubFolder + configFile);
                    if (fileContent != null)
                    {
                        if (fileContent == "NAC")
                        {
                            remindMe = false;
                        }
                    }
                }
            }

            //


            if (ok && !Project.connectToServer())
            {
                MessageBox.Show("Unable to connect to the licensing server !", "Activation software", MessageBoxButton.OK, MessageBoxImage.Error);
                ok = false;
            }

            if (ok && Project.remindMe && !Project.tryAutoconnectUser())
            {
                MessageBox.Show("Autologin failed, please login using credentials !", "Login", MessageBoxButton.OK, MessageBoxImage.Exclamation);
                Project.connectionUsername = null;
            }

            while (ok && Project.connectionUsername == null)
            {
                LoginWindow login = new LoginWindow();
                ok = (bool)login.ShowDialog();

                if (ok && Project.connectionUsername == null)
                {
                    MessageBox.Show("Bad credentials, please verify username or password !", "Activation software", MessageBoxButton.OK, MessageBoxImage.Exclamation);
                }
            }

            if (ok)
            {
                if (Project.remindMe)
                {
                    Project.storeCredentials();
                }
                else
                {
                    Project.storeNoAutoConnect();
                }
            }

            try
            {
                if (ok)
                {
                    if (Project.tryLoadLocalLicences())
                    {
                        licenceLoadSuccessfull = true;
                    }
                }
            }
            catch
            {
            }
        }

        public static bool testConnection()
        {
            bool ok = false;

            Task<bool> t = RestApiConnector.pingServer();

            TimeSpan ts = TimeSpan.FromMilliseconds(maximumResponseTimeMs);
            if (t.Wait(ts))
            {
                ok = t.Result;
            }

            return ok;
        }

        public static void storeCredentials()
        {
            FileStream fs = File.Create(rootAppFolder + configSubFolder + configFile);
            byte[] content = Encoding.UTF8.GetBytes(connectionPassword + ";" + connectionUsername);
            fs.Write(content, 0, content.Length);

            fs.Close();
        }

        public static void storeNoAutoConnect()
        {
            FileStream fs = File.Create(rootAppFolder + configSubFolder + configFile);
            byte[] content = Encoding.UTF8.GetBytes("NAC");
            fs.Write(content, 0, content.Length);

            fs.Close();
        }

        public static bool testCredentials(String username, String password) => true;

        public static bool tryAutoconnectUser()
        {
            bool ok = false;

            connectionUsername = null;
            connectionPassword = null;

            if (Directory.Exists(rootAppFolder + configSubFolder))
            {
                if (File.Exists(rootAppFolder + configSubFolder + configFile))
                {
                    String fileContent = File.ReadAllText(rootAppFolder + configSubFolder + configFile);
                    if (fileContent != null)
                    {
                        String hashPasswd = "";
                        String username = "";
                        bool before = true;
                        for (int i = 0; i < fileContent.Length; i++)
                        {
                            if (before && fileContent[i] == ';')
                            {
                                before = false;
                                continue;
                            }
                            if (before)
                            {
                                hashPasswd += fileContent[i];
                            }
                            else
                            {
                                username += fileContent[i];
                            }
                        }
                        if (!before)
                        {
                            connectionPassword = hashPasswd;
                            connectionUsername = username;
                            ok = true;
                        }
                    }
                }
            }

            return ok;
        }

        private static String hashPassword(String password)
        {
            return password; //TODO
        }

        public static bool tryAddLicence()
        {
            bool ok = false;
            addedLicencePeriod = "∞";

            RestAPIRequestLicence req = new RestAPIRequestLicence();
            Task<List<RestAPIGetSoftwareList>> t = RestApiConnector.getSoftwareList();

            //handle a timeout for the response
            TimeSpan ts = TimeSpan.FromMilliseconds(maximumResponseTimeMs);
            if (t.Wait(ts))
            {
                ok = true;
            }


            if (ok)
            {
                ok = false;

                List<RestAPIGetSoftwareList> list = t.Result;

                foreach (RestAPIGetSoftwareList item in list)
                {
                    if (item.SoftwareName == addedLicenceName)
                    {
                        req.SoftwareId = item.SoftwareId;
                        req.HardwareHash = getHardwareHash();
                        req.UserMail = connectionUsername;
                        req.UserPassword = hashPassword(connectionPassword);
                        ok = true;
                    }
                }
            }

            if (ok)
            {
                ok = false;

                String licence = RestApiConnector.requestLicence(req).Result;

                if (licence != null)
                {
                    if (new Licence(addedLicenceName, licence).storeFile()) //TODO opt : message if unable to store file
                    {
                        ok = true;
                    }
                }
            }

            return ok;
        }
        public static bool tryLoadLocalLicences()
        {
            bool ok = true;

            licences = new List<Licence>();
            foreach (String licenceName in Licence.getListFiles())
            {
                Licence insertLicence = new Licence(licenceName);

                if (!insertLicence.readFile()) //TODO opt : message if unable to read file
                {
                    ok = false;
                }
                else
                {
                    licences.Add(insertLicence);
                }
            }

            return ok;
        }

        public static void deleteLicence(String programName)
        {
            new Licence(programName).deleteFile(); //TODO opt : message if unable to delete file
        }
    }
}
