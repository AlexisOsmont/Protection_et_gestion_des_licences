using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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

        private string licence;

        public Licence(string path) 
        {
            rawlicence = "";
            licence = "";
            signature = "";
            try
            {
                parseLicenceFile(path);
            }   
            catch (Exception ex)
            {
                throw;
            }
            byte[] licenceBytes = Convert.FromBase64String(rawlicence);
            licence = Encoding.UTF8.GetString(licenceBytes, 0, licenceBytes.Length);
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

        private void parseLicenceFile(string path)
        {
            // TODO: A affiner afin d'éviter de lire un fichier de 100Go par exemple.
            //          Dépassement de capacité.

            // Lecture du fichier de licence.
            StreamReader reader = null;
            try
            {
                reader = new StreamReader(path);
            }
            catch (Exception ex)
            {
                throw;
            }

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
    }
}
