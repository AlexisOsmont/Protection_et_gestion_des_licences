using System;
using System.Collections;
using System.Management;
using System.Net.NetworkInformation;
using System.Text;

#pragma warning disable CA1416 // Code valide uniquement sous Windows

namespace MachineID
{
    class HardwareID
    {
        public static async Task<string> ReturnHardwareID(Boolean bmac, Boolean bbd,Boolean bhdd,Boolean bbios,Boolean bproc)
        {

            byte[] bytes;
            byte[] hashedBytes;
            StringBuilder sb = new StringBuilder();

            Task task = task.Run(() =>
            {

                Console.WriteLine("--- Generating Device ID ---\n");
                Console.WriteLine("--- Retrieving Processor Id Hash\n");
                Console.WriteLine("--- Retrieving Bios Id Hash\n");
                Console.WriteLine("--- Retrieving Hdd Id Hash\n");
                Console.WriteLine("\n--- Retrieving Mac Address Hash");
                Console.WriteLine("\n--- Retrieving Baseboard Serial Number Hash");
                Console.WriteLine("\n--- Summary ---");
                Console.WriteLine("Processor ID hash is : " + processorIDHash);
                Console.WriteLine("Bios ID hash is : " + macAddressHash);
                Console.WriteLine("Mac address(es) hash is : " + macAddressHash);
                Console.WriteLine("Baseboard Serial Number hash is : " + baseBoardHash);
                Console.WriteLine("\n\npress any key to exit the process...");
                Console.ReadKey();

                // ----------- MAC ADDRESS ---------------
                if (bmac) {
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
                                    + "\nMAC Address: " + nic.GetPhysicalAddress());

                                byte[] bytes = Encoding.ASCII.GetBytes(tempMac);
                                if (mac_bytes != null && bytes != null)
                                {
                                    mac_bytes = XorBytesArray(mac_bytes, bytes);
                                }
                            }
                        }
                        sb.Append(mac_bytes.ToString().Substring(0,4));
                        break;
                    }
                }
            
                // ----------- BASE BOARD ---------------
                if (bbd) {
                    ManagementObjectSearcher baseBoardObject = new ManagementObjectSearcher("select * from Win32_BaseBoard");
                    foreach (ManagementObject item in baseBoardObject.Get())
                    {
                        // https://docs.microsoft.com/fr-fr/windows/win32/cimwin32prov/win32-baseboard
                        Console.WriteLine("\nName : " + item["Name"]);
                        Console.WriteLine("Manufacturer : " + item["Manufacturer"]);
                        Console.WriteLine("SerialNumber : " + item["SerialNumber"]);
                        if (serialNo != null)
                        {
                            sb.Append(item["SerialNumber"].ToString().Substring(0,4));
                        } else {
                            Console.WriteLine("BaseBoard Serial Number can't be reached.");
                        }
                        break;
                    }
                }
            
                // ----------- PROCESSOR ---------------
            
                if (bproc) {
                    ManagementObjectSearcher myProcessorObject = new ManagementObjectSearcher("select * from Win32_Processor");
                    foreach (ManagementObject item in myProcessorObject.Get())
                    {
                        // https://docs.microsoft.com/en-us/windows/win32/cimwin32prov/win32-processor
                        Console.WriteLine("Name : " + item["Name"]);
                        Console.WriteLine("Processor Id : " + item["processorID"]);
                        if (processorId != null)
                        {
                            sb.Append(item["processorID"].ToString().Substring(0,4));
                        } else {
                            Console.WriteLine("Processor Informations can't be reached.");
                        }
                        break;
                    }
                }
            
                // ----------- BIOS ---------------

                if (bbios) {
                    ManagementObjectSearcher bios = new ManagementObjectSearcher("SELECT * FROM Win32_BIOS");
                    foreach (ManagementObject obj in bios.Get())
                    {
                        Console.WriteLine("bios version : " + obj["Version"]);
                        if (bios != null)
                        {
                            sb.Append(obj["Version"].ToString().Substring(0,4));
                        } else {
                            Console.WriteLine("Bios Informations can't be reached.");
                        }
                        break;
                    }
                }

                // ----------- HDD ---------------

                if (bhdd) {
                    ManagementObjectSearcher hdd = new ManagementObjectSearcher("SELECT * FROM Win32_DiskDrive");
                    foreach (ManagementObject obj in hdd.Get())
                    {
                        Console.WriteLine("hdd Signature : " + obj["Signature"]);
                        if (hdd != null)
                        {
                            sb.Append(obj["Signature"].ToString().Substring(0,4));
                        } else {
                            Console.WriteLine("Hdd Informations can't be reached.");
                        }
                        break;
                    }
                }
                // ---------- END ---------------
            });  

            task.WaitAll(task);
            bytes = System.Text.Encoding.UTF8.GetBytes(sbyte.ToString());
            hashedBytes = System.Security.Cryptography.SHA256.Create().ComputeHash(bytes);
            return await task.FromResult(Convert.ToBase64String(hashedBytes).Substring(25));
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