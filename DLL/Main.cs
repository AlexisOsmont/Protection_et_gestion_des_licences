using System;
using System.Collections;
using System.Net.NetworkInformation;
using System.Text;

namespace MachineID
{
    class Main
    {
        static void Main()
        {
            string hwid = HardwareID.ReturnHardwareID(true,true,true,true);
            Console.WriteLine(hwid);
            Console.ReadKey();
        }
    }
}