using Microsoft.Win32;
using System.Management;
using System.Net.NetworkInformation;
using System.Text;

namespace HardwareHash
{
    internal class MachineHardware
    {

        private const int ANTICHEAT_CODE_LEN = 10;
        private const string ALPHABET = "aA1bB2cC3dD4eE5fF6gG7hH8iI9jJ0kKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";

        private static string MAC = "MAC";
        private static string BASE_BOARD = "BaseBoard";
        private static string HARD_DRIVE = "HardDrive";
        private static string BIOS = "BIOS";
        private static string PROC = "Processor";

        public static string getAllHardwareId()
        {
            return getHardwareId(true, true, true, true, true);
        }

        public static string getHardwareId(bool mac, bool baseBoard, bool hdd, bool bios, bool proc)
        {
            // Anti cheating process
            string hash = assureAntiCheating();

            // generating keys
            if (mac)
            {
                hash += MacAddressHash();
            }
            if (baseBoard)
            {
                hash += BaseBoardHash();
            }
            if (hdd)
            {
                hash += HddHash();
            }
            if (bios)
            {
                hash += BiosHash();
            }
            if (proc)
            {
                hash += ProcessorIdHash();
            }

            return hash.Replace(" ", "").Replace("-", "");
        }

        private static string assureAntiCheating()
        {
            RegistryKey key;
            key = Registry.CurrentUser.CreateSubKey("ProLicenceMachineHardware");

            string? anticheatcode = key.GetValue("anticheating_CODE")?.ToString();
            if (anticheatcode == null)
            {
                // Génération d'une clé aléatoire
                Random random = new Random();
                string newcode = "";
                for (var i = 0; i < ANTICHEAT_CODE_LEN; i++)
                {
                    int r = random.Next(ALPHABET.Length);
                    newcode += ALPHABET[r];
                }

                key.SetValue("anticheating_CODE", newcode);
                key.Close();
                return newcode;
            }

            key.Close();
            return anticheatcode;
        }

        private static string MacAddressHash()
        {
            const int MAC_ADDR_LENGTH = 12;

            byte[] mac_bytes = new byte[MAC_ADDR_LENGTH];
            // Console.WriteLine("mac_bytesL = " + mac_bytes.Length);

            foreach (NetworkInterface nic in NetworkInterface.GetAllNetworkInterfaces())
            {
                if (nic.NetworkInterfaceType == NetworkInterfaceType.Ethernet || nic.NetworkInterfaceType == NetworkInterfaceType.Wireless80211)
                {
                    string tempMac = nic.GetPhysicalAddress().ToString();
                    if (tempMac.Length == MAC_ADDR_LENGTH)
                    {
                        //Console.WriteLine("\nName: " + nic.Name 
                        //    + "\nType: " + nic.NetworkInterfaceType 
                        //    + "\nMAC Address: " + nic.GetPhysicalAddress() );

                        byte[] bytes = Encoding.ASCII.GetBytes(tempMac);
                        if (mac_bytes != null && bytes != null)
                        {
                            mac_bytes = XorBytesArray(mac_bytes, bytes);
                        }
                    }
                }
            }

            if (mac_bytes == null)
            {
                return "";
            }

            string hash = BitConverter.ToString(mac_bytes).Replace("-", "");
            // Console.WriteLine("\nFinal Mac Hash = " + hash);
            return hash;
        }

        private static string BaseBoardHash()
        {
            ManagementObjectSearcher myProcessorObject = new ManagementObjectSearcher("select * from Win32_BaseBoard");

            foreach (ManagementObject item in myProcessorObject.Get())
            {
                // https://docs.microsoft.com/fr-fr/windows/win32/cimwin32prov/win32-baseboard

                string? serialNo = (item["SerialNumber"] == null) ? null : item["SerialNumber"].ToString();
                if (serialNo != null)
                {
                    return serialNo;
                }
            }

            //Console.WriteLine("BaseBoard Serial Number can't be reached.");
            return "";
        }

        private static string ProcessorIdHash()
        {
            ManagementObjectSearcher myProcessorObject = new ManagementObjectSearcher("select * from Win32_Processor");

            foreach (ManagementObject item in myProcessorObject.Get())
            {
                // https://docs.microsoft.com/en-us/windows/win32/cimwin32prov/win32-processor
                //Console.WriteLine("Name : " + item["Name"]);
                //Console.WriteLine("Processor Id : " + item["processorID"]);

                string? processorId = item["processorID"] == null ? null : item["processorID"].ToString();
                if (processorId != null)
                {
                    return processorId;
                }

            }
            //Console.WriteLine("Processor Informations can't be reached.");
            return "";
        }

        private static string BiosHash()
        {
            ManagementObjectSearcher bios = new ManagementObjectSearcher("SELECT * FROM Win32_BIOS");

            foreach (ManagementObject obj in bios.Get())
            {
                //Console.WriteLine("bios version : " + obj["Version"]);

                string? sbios = obj["Version"] == null ? null : obj["Version"].ToString();
                if (sbios != null)
                {
                    return sbios;
                }

            }
            //Console.WriteLine("Bios Informations can't be reached.");
            return "";
        }

        private static string HddHash()
        {
            ManagementObjectSearcher hdd = new ManagementObjectSearcher("SELECT * FROM Win32_DiskDrive");

            foreach (ManagementObject obj in hdd.Get())
            {
                //Console.WriteLine("hdd Signature : " + obj["Signature"]);

                string? shdd = obj["Signature"] == null ? null : obj["Signature"].ToString();
                if (shdd != null)
                {
                    return shdd;
                }

            }
            //Console.WriteLine("Hdd Informations can't be reached.");
            return "";
        }

        private static byte[]? XorBytesArray(byte[] first, byte[] second)
        {
            if (first == null || second == null
                || first.Length != second.Length)
            {
                return null;
            }

            byte[] xored = new byte[first.Length];

            for (int i = 0; i < first.Length; ++i)
            {
                xored[i] = (byte)(first[i] ^ second[i]);
            }
            return xored;
        }
    }
}