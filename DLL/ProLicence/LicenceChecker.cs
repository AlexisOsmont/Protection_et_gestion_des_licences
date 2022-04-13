using System;
using System.Security.Cryptography;
using System.Text;
using EllipticCurve;

namespace ProLicence

{
    public class LicenceChecker
    {
        private const string PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\nMFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEzhQXdPP50Dt+IJkAl58diahPnxrAUZta4+SUItloZeeF2efMetsQu3TvnX1KExv5CR8NxYfvL9TYrSSmLdxm+g==\n-----END PUBLIC KEY-----";

        private Licence licence;

        private bool[] hardwareHashComposent;

        /**
         * <summary>
         *      <c>LicenceChecker</c> permet de vérifier l'intégrité et la validité d'une licence donnée.
         * </summary>
         * <param name="path">Le chemin vers un fichier de licence de format valide.</param>
         */
        public LicenceChecker(string path)
        { 
            hardwareHashComposent = new bool[5];
            for (int i = 0; i < hardwareHashComposent.Length; i++)
            {
                hardwareHashComposent[i] = true;
            }

            licence = new Licence(path);
        }

        public void setHardwareHashComposent(bool mac, bool board, bool hdd, bool bios, bool proc)
        {
            hardwareHashComposent[0] = mac;
            hardwareHashComposent[1] = board;
            hardwareHashComposent[2] = hdd;
            hardwareHashComposent[3] = bios;
            hardwareHashComposent[4] = proc;
        }

        public bool isValid()
        {
            return checkIntegrity() && checkValidity();
        }

        /**
         *  <summary>
         *      Vérifie l'intégrité de la licence.
         *      Signature afin d'assurer qu'elle n'ai pas été modifiée.
         *      Hardware Id afin d'assurer qu'elle soit bien utilisée sur la bonne machine.
         *  </summary>
         */
        public bool checkIntegrity()
        {
            return checkSignature();
        }

        /**
         * <summary>
         *  Vérifie la validité de la licence en vérifiant 
         *  les différentes contraintes définies dans celle ci. 
         *  Date de validité, nombre de lancement...
         * </summary>
         * <returns><c>True</c> si les contraintes de validité sont respectées, <c>False</c> sinon.</returns>
         * 
         */
        public bool checkValidity()
        {
            return checkHardwareHash() && checkExpirationDate();
        }

        /**
         * <summary>
         *      Vérifie la signature de la licence, assurant qu'elle n'ai pas été modifiée.
         * </summary>
         * 
         * <returns><c>True</c> si la signature est valide, <c>False</c> sinon.</returns>
         */
        public bool checkSignature()
        {
            PublicKey publicKey = PublicKey.fromPem(PUBLIC_KEY);
            Signature sign = Signature.fromBase64(licence.getSignature());

            Console.WriteLine("Licence b64 : " + licence.getRawLicence());
            Console.WriteLine("Signature b64 : " + licence.getSignature());

            bool isVerified = Ecdsa.verify(licence.getRawLicence(), sign, publicKey);
            Console.WriteLine("Signature vérification : " + isVerified + "\n");
            return isVerified;
        }

        public bool checkHardwareHash()
        {
            string machineHardwareHash = MachineHardware.getHardwareId(hardwareHashComposent[0], 
                hardwareHashComposent[1], hardwareHashComposent[2], hardwareHashComposent[3], hardwareHashComposent[4]);

            Console.WriteLine("Licence Hardware Hash : " + licence.getHardwareHash());
            Console.WriteLine("Machine Hardware Hash : " + machineHardwareHash);
            
            bool isChecked = licence.getHardwareHash().Equals(machineHardwareHash);
            Console.WriteLine("Vérification HardwareHash : " + isChecked + "\n");
            return isChecked;
        }

        public bool checkExpirationDate()
        {
            DateTime? validityDate = licence.getValidityDate();
            if (validityDate == null) 
            {
                Console.WriteLine("Validity Date is null");
                return false;
            }

            DateTime nowDate = DateTime.Now;

            Console.WriteLine("Validity Date : " + validityDate);
            Console.WriteLine("Current Date : " + nowDate);

            bool isChecked = validityDate > nowDate;
            Console.WriteLine("Date Validity : " + isChecked + "\n");
            return isChecked;
        }
    }
}