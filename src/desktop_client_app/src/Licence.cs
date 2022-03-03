using System;
using System.Collections.Generic;
using System.IO;

namespace ProjetAnnuel
{
    internal class Licence
    {

        public static String licenceFolder { get; set; } //default appFolder
        public String content { get; set; }
        public String name { get; set; }
        public String period { get; set; }
        public Licence(String licenceName)
        {
            name = licenceName;
            period = "∞";
        }

        public Licence(String licenceName, String licenceContent)
        {
            name = licenceName;
            content = licenceContent;
            period = "∞";
        }

        public static List<String> getListFiles()
        {
            List<String> listFiles = new List<String>();

            if (Directory.Exists(licenceFolder))
            {
                foreach (String file in Directory.GetFiles(licenceFolder))
                {
                    listFiles.Add(file.Substring(licenceFolder.Length + 1));
                }
            }

            return listFiles;
        }

        public bool storeFile()
        {
            bool ok = false;

            try
            {
                File.WriteAllText(licenceFolder + "\\" + name, content);
                ok = true;
            } catch (Exception ex)
            {

            }

            return ok;
        }

        public bool readFile()
        {
            bool ok = false;

            String fileContent = File.ReadAllText(licenceFolder + "\\" + name);

            content = fileContent;

            if (content != null)
            {
                ok = true;
            }
            return ok;
        }

        public bool deleteFile()
        {
            bool ok = false;

            try
            {
                File.Delete(licenceFolder + "\\" + name);
                ok = true;
            } catch (Exception ex)
            {
               
            }

            return ok;
        }
    }
}
