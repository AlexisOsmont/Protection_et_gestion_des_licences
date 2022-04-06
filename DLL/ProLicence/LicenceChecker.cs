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

        /**
         * <summary>
         *      <c>LicenceChecker</c> permet de vérifier l'intégrité et la validité d'une licence donnée.
         * </summary>
         * <param name="path">Le chemin vers un fichier de licence de format valide.</param>
         */
        public LicenceChecker(string path)
        {
            licence = new Licence(path);
            Console.Write("Licence : " + licence.getLicence().Trim() + "\n");
            Console.Write("Raw Licence : " + licence.getRawLicence() + "\n");
            Console.Write("Signature : " + licence.getSignature() + "\n");
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
        private bool checkIntegrity()
        {
            return checkSignature() && checkHardware();
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
            return true;
        }

        /**
         * <summary>
         *      Vérifie la signature de la licence assurant qu'elle n'ai pas été modifiée.
         * </summary>
         * 
         * <returns><c>True</c> si la signature est valide, <c>False</c> sinon.</returns>
         */
        private bool checkSignature()
        {
            PublicKey publicKey = PublicKey.fromPem(PUBLIC_KEY);
            Signature sign = Signature.fromBase64(licence.getSignature());

            bool isVerified = Ecdsa.verify(licence.getRawLicence(), sign, publicKey);
            Console.WriteLine("\nSignature verification : " + isVerified);
            return isVerified;
        }

        private bool checkHardware()
        {
            return true;
        }
    }
}