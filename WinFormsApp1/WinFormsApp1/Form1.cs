using ActivationSoftware;

namespace WinFormsApp1
{
    public partial class Form1 : Form
    {
        private readonly List<APISoftware> softwareList;
        private readonly string extension = ".license";

        private const int MIN_COMPONENTS_NUMBER = 2;

        public Form1()
        {
            InitializeComponent();
            // Verify connection to API
            if (!Controller.TestConnection())
            {
                ShowError("Connexion au serveur impossible");
            }
            else
            {
                // Fetch the softwares with the API
                softwareList = Controller.FetchSoftwareList();
                // Fill the combo box with the softwares
                FillComboBox();
            }
            FillHWIdBox();
        }

        private void FillComboBox()
        {
            if (softwareList == null)
            {
                ShowError("Impossible de charger la liste des logiciels.");
            } else
            {
                // set the content of the ComboBox with the software list
                softsComboBox.DataSource = softwareList;
            }
        }

        private void FillHWIdBox()
        {
            string hw = Controller.getHardwareHash();
            if (hw != null && hw.Length > 0)
            {
                hwTextBox.Text = hw;
            }
        }

        private void submit_Click(object sender, EventArgs e)
        {
            string userMail = idBox.Text;
            string password = passwdBox.Text;
            int softwareId = ((APISoftware)softsComboBox.SelectedItem).SoftwareId;

            if (userMail == "" || password == "")
            {
                ShowError("Certains champs sont incomplets");
            } else
            {
                string? licence = null;
                try
                {
                    // try to fetch the requested license via API
                    licence = Controller.RequestLicence(
                            userMail, password, softwareId
                        );
                }
                catch (HttpRequestException ex)
                {
                    string errMsg = null;
                    switch (ex.StatusCode)
                    {
                        case System.Net.HttpStatusCode.Unauthorized: // 401
                            errMsg = "Identifiant et/ou mot de passe invalide";
                            break;
                        case System.Net.HttpStatusCode.NotFound:    // 404
                            errMsg = "Aucune licence pour ce logiciel n'a été trouvé";
                            break;
                        case System.Net.HttpStatusCode.InternalServerError: // 500
                            errMsg = "Une erreur interne au serveur est survenue, veuillez réessayer.";
                            break;
                        default:
                            errMsg = "Erreur lors de la récupération de la licence";
                            break;
                    }
                    // An error occured, print error message and finish
                    ShowError(ex.StatusCode + ": " + errMsg);
                    return;
                }

                // Create a file dialog to ask user where to save license
                SaveFileDialog saveFileDialog = new SaveFileDialog();
                saveFileDialog.Title = "Sauvegarder le fichier de licence";
                saveFileDialog.Filter = "License file | *.license";
                saveFileDialog.FileName =
                        ((APISoftware)softsComboBox.SelectedItem).SoftwareName.Replace(' ', '-');
                saveFileDialog.DefaultExt = extension;

                DialogResult dr = saveFileDialog.ShowDialog();

                if (dr == DialogResult.OK)
                {
                    string path = saveFileDialog.FileName;
                    File.WriteAllText(path, licence);
                    ShowInfo("Fichier de licence sauvegardé");
                }
            }
        }

        private void copyButton_Click(object sender, EventArgs e)
        {
            string text = hwTextBox.Text;
            if (text != null && text.Length > 0)
            {
                Clipboard.SetText(text);
            } else
            {
                ShowError("Aucun identifiant a copier");
            }
        }

        // Tools methods

        private static void ShowError(string msg)
        {
            MessageBox.Show(msg, "Erreur", MessageBoxButtons.OK, MessageBoxIcon.Error);
        }

        private static void ShowInfo(string msg)
        {
            MessageBox.Show(msg, "Information", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }
    }
}