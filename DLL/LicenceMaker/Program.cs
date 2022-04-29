using HardwareHash;
using System;
using System.IO;
using EllipticCurve;

namespace LicenceMaker
{
    internal class Program
    {
        const string PRIVATE_KEY_FILE = "./private.pem";
        static void Main(string[] args)
        {
            Console.WriteLine("Génération interactive de licence.\n");
            Console.Write("Date de validitée (exemple 31/12/2999) : ");

            string date;
            date = Console.ReadLine();

            Console.Write("\nCalcul de l'ID hardware... ");
            string hardwareHash = MachineHardware.getHardwareId(true, true, true, true, true);
            Console.WriteLine(hardwareHash);

            string content = "{\n" +
                                "\t\"hardwareid\":\""     +   hardwareHash  + "\"," +
                                "\n\t\"validity\":\""   +   date          + "\""   +
                             "\n}";

            Console.WriteLine("\nLicence avant encodage :\n\n" + content);

            byte[] plainTextBytes = System.Text.Encoding.UTF8.GetBytes(content);
            string contentb64 = Convert.ToBase64String(plainTextBytes);

            // Lecture de la clé privée
            StreamReader reader = null;
            reader = new StreamReader(PRIVATE_KEY_FILE);
            string privateKeyString = reader.ReadToEnd();
            PrivateKey privateKey = PrivateKey.fromPem(privateKeyString);
            // Signature du contenu en base64
            Signature signature = Ecdsa.sign(contentb64, privateKey);
            string signb64 = signature.toBase64();


            string licence = "###licence#\n";
            licence += contentb64 + "\n";
            licence += "###signature#\n";
            licence += signb64 + "\n";
            licence += "###eof#";

            Console.WriteLine("\nFichier de licence final :\n\n" + licence);
            using (StreamWriter writer = new StreamWriter("licence.txt"))
            {
                writer.WriteLine(licence);
            }

            Console.Write("\n\n\nAppyez sur entrée pour terminer le programme...");
            Console.Read();
        }
    }
}
