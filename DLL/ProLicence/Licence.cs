using System.Text;
using System.Text.Json;


namespace ProLicence
{
    /**
     * <summary>
     *      Classe représentant les objets licence construits à partir de fichier de licence.
     * </summary>
     */
    internal class Licence
    {
        private const string LICENCE_HEADER = "###licence#";
        private const string SIGNATURE_HEADER = "###signature#";
        private const string EOF_HEADER = "###eof#";

        // Licence et signature en base64 depuis le fichier de licence.
        private string rawlicence;
        private string signature;

        // Contenu de la licence décodé depuis base64
        private string licence;

        private string hardwareHash;

        private DateTime? validityDate;
        private const string DATE_SEPARATOR = "/";

        public Licence(string path) 
        {
            rawlicence = "";
            licence = "";
            signature = "";

            hardwareHash = "";
            validityDate = null;

            parseLicenceFile(path);
            parseLicenceContent();   
        }

        public string getRawLicence()
        {
            return rawlicence;
        }

        public string getLicence()
        {
            return licence;
        }

        public string getSignature()
        {
            return signature;
        }

        public string getHardwareHash()
        {
            return hardwareHash;
        }

        public DateTime? getValidityDate()
        {
            return validityDate == null ? null : validityDate.Value;
        }

        private void parseLicenceFile(string path)
        {
            // TODO: A affiner afin d'éviter de lire un fichier de 100Go par exemple.
            //          Dépassement de capacité.

            // Lecture du fichier de licence.
            StreamReader? reader = null;
            reader = new StreamReader(path);


            // Lecture de la première ligne.
            string? line = reader.ReadLine();
            // Vérification du header de licence.
            if (line == null || !line.Equals(LICENCE_HEADER))
            {
                throw new Exception("Format de licence invalide. Licence header non présent.");
            }

            // La lecture de licence header a été faites, lecture de la licence.
            line = reader.ReadLine();
            // Lecture du contenu de la licence. Jusqu'à rencontrer le header de signature.
            while (line != null && !line.Equals(SIGNATURE_HEADER))
            {
                rawlicence += line;
                line = reader.ReadLine();
            }
            if (line == null)
            {
                // Le header de signature n'est jamais rencontré.
                throw new Exception("Format de licence invalide. Signature header non présent.");
            }

            // La lecture du signature header a été faite. Lecture de la prochaine ligne.
            line = reader.ReadLine();
            // Lecture de la signature. Jusqu'à renctontrer le header de fin de fichier.
            while (line != null && !line.Equals(EOF_HEADER))
            {
                signature += line;
                line = reader.ReadLine();
            }
            if (line == null)
            {
                throw new Exception("Format de licence invalide. EOF header non présent.");
            }
        }

        private void parseLicenceContent()
        {
            // Décodage de la licence en base64
            byte[] licenceBytes = Convert.FromBase64String(rawlicence);
            licence = Encoding.UTF8.GetString(licenceBytes, 0, licenceBytes.Length);

            JsonDocument json = JsonDocument.Parse(licence);
            var content = json.RootElement;

            // Récupération du l'identifiant machine.
            hardwareHash = json.RootElement.GetProperty("hardwareid").ToString();

            // Récupération de la date 
            string rawValidityDate = json.RootElement.GetProperty("validity").ToString();
            string[] rawValidityDateSplitted = rawValidityDate.Split(DATE_SEPARATOR);
            if (rawValidityDateSplitted.Length != 3)
            {
                throw new Exception("Date de validité de format incorrect.");
            }
            validityDate = new DateTime(Int32.Parse(rawValidityDateSplitted[2]), Int32.Parse(rawValidityDateSplitted[1]), Int32.Parse(rawValidityDateSplitted[0])); 
        }
    }
}
