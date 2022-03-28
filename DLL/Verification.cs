using System;
using System.Security.Cryptography;
using System.Text;

// Algorithme utiliseé : SHA256 avec ECDSA
// Courbe utilisé : secp521r1

namespace Verification
{
    public class Verification
    {
        private const String KEY_FILE  = @"C:\Users\Louka.PC-LOUKA\Desktop\Keys\public.pem";
        private const String SIGNATURE = @"C:\Users\Louka.PC-LOUKA\Desktop\Keys\file.sign";
        private const String TEST_FILE = @"C:\Users\Louka.PC-LOUKA\Desktop\Keys\test.txt";

        static void Main()
        {
            string data = System.IO.File.ReadAllText(TEST_FILE);
            byte[] dataBytes = Encoding.UTF8.GetBytes(data);
            byte[] signBytes = System.IO.File.ReadAllBytes(SIGNATURE);

            CngKey cng = CngKey.Open(KEY_FILE);
            ECDsaCng ecdsa = new ECDsaCng(cng);

            bool result = ecdsa.VerifyData(dataBytes, signBytes);
            if (result)
            {
                System.Console.WriteLine("Signature OK");
            }
            else
            {
                System.Console.WriteLine("Signature not OK");
            }
        }
    }
}