using System;
using System.Collections;
using System.Management;
using System.Net.NetworkInformation;
using System.Text;

#pragma warning disable CA1416 // Code valide uniquement sous Windows

namespace MachineID
{
    internal class Program
    {
        static void Main(string[] args)
        {

            Console.WriteLine("--- Generating Device ID ---\n");

            Console.WriteLine("--- Retrieving Processor Id Hash\n");
            string processorIDHash = ProcessorIdHash();

            Console.WriteLine("--- Retrieving Bios Id Hash\n");
            string biosHash = BiosHash();

            Console.WriteLine("--- Retrieving Hdd Id Hash\n");
            string hddHash = HddHash();

            Console.WriteLine("\n--- Retrieving Mac Address Hash");
            string macAddressHash = MacAddressHash();

            Console.WriteLine("\n--- Retrieving Baseboard Serial Number Hash");
            string baseBoardHash = BaseBoardHash();


            Console.WriteLine("\n--- Summary ---");
            Console.WriteLine("Processor ID hash is : " + processorIDHash);
            Console.WriteLine("Bios ID hash is : " + macAddressHash);
            Console.WriteLine("Mac address(es) hash is : " + macAddressHash);
            Console.WriteLine("Baseboard Serial Number hash is : " + baseBoardHash);

            Console.WriteLine("\n\npress any key to exit the process...");
            Console.ReadKey();
        }

        static string MacAddressHash()
        {
            const int MAC_ADDR_LENGTH = 12;

            byte[]? mac_bytes = new byte[MAC_ADDR_LENGTH];
            Console.WriteLine("mac_bytesL = " + mac_bytes.Length);
                
            foreach (NetworkInterface nic in NetworkInterface.GetAllNetworkInterfaces())
            {
                if (nic.NetworkInterfaceType == NetworkInterfaceType.Ethernet || nic.NetworkInterfaceType == NetworkInterfaceType.Wireless80211)
                {
                    string tempMac = nic.GetPhysicalAddress().ToString();
                    if (tempMac.Length == MAC_ADDR_LENGTH)
                    {
                        Console.WriteLine("\nName: " + nic.Name 
                            + "\nType: " + nic.NetworkInterfaceType 
                            + "\nMAC Address: " + nic.GetPhysicalAddress() );

                        byte[] bytes = Encoding.ASCII.GetBytes(tempMac);
                        if (mac_bytes != null && bytes != null)
                        {
                            mac_bytes = XorBytesArray(mac_bytes, bytes);
                        }
                    }
                }
            }

            string hash = BitConverter.ToString(mac_bytes).Replace("-", "");

            Console.WriteLine("\nFinal Mac Hash = " + hash);
            return hash;
        }

        static string BaseBoardHash()
        {
            ManagementObjectSearcher myProcessorObject = new ManagementObjectSearcher("select * from Win32_BaseBoard");

            foreach (ManagementObject item in myProcessorObject.Get())
            {
                // https://docs.microsoft.com/fr-fr/windows/win32/cimwin32prov/win32-baseboard
                Console.WriteLine("\nName : " + item["Name"]);
                Console.WriteLine("Manufacturer : " + item["Manufacturer"]);
                Console.WriteLine("SerialNumber : " + item["SerialNumber"]);

                string? serialNo = item["SerialNumber"].ToString();
                if (serialNo != null)
                {
                    return serialNo;
                }
            }

            Console.WriteLine("BaseBoard Serial Number can't be reached.");
            return "";
        }

        static string ProcessorIdHash()
        {
            ManagementObjectSearcher myProcessorObject = new ManagementObjectSearcher("select * from Win32_Processor");

            foreach (ManagementObject item in myProcessorObject.Get())
            {
                // https://docs.microsoft.com/en-us/windows/win32/cimwin32prov/win32-processor
                Console.WriteLine("Name : " + item["Name"]);
                Console.WriteLine("Processor Id : " + item["processorID"]);

                string? processorId = item["processorID"].ToString();
                if (processorId != null)
                {
                    return processorId;
                }
                
            }
            Console.WriteLine("Processor Informations can't be reached.");
            return "";
        }
        static string BiosHash()
        {
            ManagementObjectSearcher bios = new ManagementObjectSearcher("SELECT * FROM Win32_BIOS");

             foreach (ManagementObject obj in bios.Get())
            {
                Console.WriteLine("bios version : " + obj["Version"]);

                string? bios = obj["Version"].ToString();
                if (bios != null)
                {
                    return bios;
                }
                
            }
            Console.WriteLine("Bios Informations can't be reached.");
            return "";

        }

        static string HddHash()
        {
            ManagementObjectSearcher hdd = new ManagementObjectSearcher("SELECT * FROM Win32_DiskDrive");

             foreach (ManagementObject obj in hdd.Get())
            {
                Console.WriteLine("hdd Signature : " + obj["Signature"]);

                string? hdd = obj["Signature"].ToString();
                if (hdd != null)
                {
                    return hdd;
                }
                
            }
            Console.WriteLine("Hdd Informations can't be reached.");
            return "";

        }


        static byte[]? XorBytesArray(byte[] first, byte[] second)
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